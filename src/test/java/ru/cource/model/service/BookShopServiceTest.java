package ru.cource.model.service;

import ru.cource.model.domain.Author;
import ru.cource.model.domain.Book;
import ru.cource.aspects.LoggerAspect;
import ru.cource.config.DataBaseConfig;
import ru.cource.model.domain.Addres;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.NativeQuery;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.support.AnnotationConfigContextLoader;
import java.util.*;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Test for {@link BookShopServiceInterface}
 * 
 * @author AlexanderM-O
 *
 */

@ExtendWith(SpringExtension.class) // add spring support
@ContextConfiguration(classes = { DataBaseConfig.class, BookShopServiceHibernateImpl.class,
		LoggerAspect.class }, loader = AnnotationConfigContextLoader.class)
public class BookShopServiceTest {
	@Autowired
	private SessionFactory sessionFactory;

	@Autowired
	private BookShopServiceInterface bookShopService;

	private Book book1;

	private Author author1;

	private Author author2;

	public void cleanBase() {
		Session session = sessionFactory.openSession();
		session.beginTransaction();
		NativeQuery query = session.createSQLQuery("call truncate_all_tables();");
		query.executeUpdate();
		session.getTransaction().commit();
		session.close();
	}

	@BeforeEach
	public void prepareFixture() {
		cleanBase();
		author1 = new Author("Marks");
		author1.setAddres(new Addres("USA"));

		author2 = new Author("Oryell");
		author2.setAddres(new Addres("Russia"));

		book1 = new Book("1984");
	}

	@Test
	public void createShouldAddBookToDataBase() {
		bookShopService.createBook(book1);
		Book bookFromShop = bookShopService.findBookById(book1.getId());
		assertTrue(bookFromShop.equals(book1));
	}

	@Test
	public void createShouldAddBookWithAuthorsToDataBaseWithCorrectLinks() {
		book1.getAuthors().add(author1);
		book1.getAuthors().add(author2);

		bookShopService.createBook(book1);

		Book persistedBook = bookShopService.findBookById(book1.getId());

		Set<Author> persistedAuthors = persistedBook.getAuthors();

		assertThatEachAuthorHasLinkToBook(persistedAuthors, persistedBook);
	}

	@Test
	public void createShouldnotAddAuthorsWithSameNameToDataBase() {
		Book book2 = new Book("Narny");

		book1.getAuthors().add(author1);
		book1.getAuthors().add(author2);

		// book1 already have 2 authors so we don't need to have copy of author 1
		book2.getAuthors().add(author1);

		bookShopService.createBook(book1);
		bookShopService.createBook(book2);

		// ACTUALLY HAVE 2 DISTINCT AUTHORS AND BOOKS

		assertEquals(2, bookShopService.GetAllBook().size());

		assertEquals(2, bookShopService.GetAllAuthors().size());
	}

	@Test
	public void updateShouldChangeManyToManyTable() {
		book1.setAuthors(new HashSet<Author>(Arrays.asList(author1)));

		bookShopService.createBook(book1);

		book1.setAuthors(new HashSet<Author>(Arrays.asList(author2)));

		bookShopService.updateBook(book1);

		Book persistedUpdatedBook = bookShopService.findBookById(book1.getId());
		Author author1FromDataBase = bookShopService.findAuthorByName(author1.getName());
		Author author2FromDataBase = bookShopService.findAuthorByName(author2.getName());

		// ACTUALLY HAVE 2 DISTINCT AUTHORS AND 1 BOOK
		// author2 has link to book and author 1 has't
		assertEquals(2, bookShopService.GetAllAuthors().size());
		assertFalse(author1FromDataBase.getBooks().contains(persistedUpdatedBook));
		assertTrue(author2FromDataBase.getBooks().contains(persistedUpdatedBook));
	}

	@Test
	public void updateShouldNotCreateDublicates() {
		book1.setAuthors(new HashSet<Author>(Arrays.asList(author1)));

		bookShopService.createBook(book1);

		book1.setAuthors(new HashSet<Author>(Arrays.asList(author2)));

		bookShopService.updateBook(book1);

		book1.setAuthors(new HashSet<Author>(Arrays.asList(author1)));

		bookShopService.updateBook(book1);
		// have only 2 not 3 authors
		assertEquals(bookShopService.GetAllAuthors().size(), 2);
	}

	@Test
	public void deleteShouldnotDeleteAuthorWithBook() {
		book1.setAuthors(new HashSet<Author>(Arrays.asList(author1)));

		bookShopService.createBook(book1);

		bookShopService.deleteBookById(book1.getId());

		assertEquals(bookShopService.GetAllBook().size(), 0);
		assertEquals(bookShopService.GetAllAuthors().size(), 1);
	}

	static void assertThatEachAuthorHasLinkToBook(Collection<Author> AuthorsFromPersistedBook, Book persistedBook) {
		for (Author a : AuthorsFromPersistedBook) {
			assertTrue(a.getBooks().contains(persistedBook));
		}
	}
}
