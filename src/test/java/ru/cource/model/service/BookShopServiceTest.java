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
 * Test manyToMany implementation
 */
@ExtendWith(SpringExtension.class) // add spring support
@ContextConfiguration(classes = {DataBaseConfig.class,BookShopServiceImpl.class,LoggerAspect.class}, 
	loader = AnnotationConfigContextLoader.class)
public class BookShopServiceTest {
	@Autowired
	private SessionFactory sessionFactory;
	
	@Autowired
	private BookShopServiceImpl bookShopService;
	
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
	}

	@Test
	public void createShouldAddBookToDataBase() {
		Book book = new Book("1984");
		bookShopService.createBook(book);
		Book bookFromShop = bookShopService.getBookById(book.getId());
		assertTrue(bookFromShop.equals(book));
	}

	@Test
	public void createShouldAddBookWithAuthorsToDataBaseWithCorrectLinks() {
		Book book = new Book("1984");
		
		book.getAuthors().add(author1);
		book.getAuthors().add(author2);
		
		bookShopService.createBook(book);

		Book persistedBook = bookShopService.getBookById(book.getId());

		Set<Author> persistedAuthors = persistedBook.getAuthors();

		assertThatEachAuthorHasLinkToBook(persistedAuthors,persistedBook);
	}

	@Test
	public void createShouldnotAddAuthorsWithSameName() {
		Book book1 = new Book("1984");

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
		Book book = new Book("1984");
		book.setAuthors(new HashSet<Author>(Arrays.asList(author1)));
		
		bookShopService.createBook(book);
	
		book.setAuthors(new HashSet<Author>(Arrays.asList(author2)));

		bookShopService.updateBook(book);
		
		Book persistedUpdatedBook=bookShopService.getBookById(book.getId());
		Author author1FromDataBase=bookShopService.getAuthorByName(author1.getName());
		Author author2FromDataBase=bookShopService.getAuthorByName(author2.getName());

		// ACTUALLY HAVE 2 DISTINCT AUTHORS AND 1 BOOK
		//author2 has link to book and  author 1 has't
		assertEquals(2, bookShopService.GetAllAuthors().size());
		assertFalse(author1FromDataBase.getBooks().contains(persistedUpdatedBook));
		assertTrue(author2FromDataBase.getBooks().contains(persistedUpdatedBook));
	}
	
	@Test
	public void updateShouldNotCreateDublicates() {
		Book book = new Book("1984");
		book.setAuthors(new HashSet<Author>(Arrays.asList(author1)));
		
		bookShopService.createBook(book);
	
		book.setAuthors(new HashSet<Author>(Arrays.asList(author2)));

		bookShopService.updateBook(book);
		
		book.setAuthors(new HashSet<Author>(Arrays.asList(author1)));

		bookShopService.updateBook(book);
		//have only 2 not 3 authors
		assertEquals(bookShopService.GetAllAuthors().size(),2);
	}

	@Test
	public void deleteShouldnotDeleteAuthorWithBook() {
		Book book = new Book("1984");		
		book.setAuthors(new HashSet<Author>(Arrays.asList(author1)));
		
		bookShopService.createBook(book);

		bookShopService.deleteBookById(book.getId());

		assertEquals(bookShopService.GetAllBook().size(), 0);
		assertEquals(bookShopService.GetAllAuthors().size(), 1);
	}
	
	static void assertThatEachAuthorHasLinkToBook(Collection<Author> AuthorsFromPersistedBook,Book persistedBook) {
		for (Author a : AuthorsFromPersistedBook) {
			assertTrue(a.getBooks().contains(persistedBook));
		}
	}
}

