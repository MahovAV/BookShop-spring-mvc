package ru.cource.model.service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ru.cource.model.dao.HibernateAuthorDao;
import ru.cource.model.dao.HibernateBookDao;
import ru.cource.model.domain.Author;
import ru.cource.model.domain.Book;

/**
 * Implementation of {@link BookShopServiceInterface}
 * 
 * 
 * @author AlexanderM-O
 *
 */
@Transactional
@Service
public class BookShopServiceHibernateImpl implements BookShopServiceInterface {
	@Autowired
	private HibernateBookDao bookDAO;
	@Autowired
	private HibernateAuthorDao authorDAO;

	public BookShopServiceHibernateImpl() {
	}

	public void createBook(Book book) {
		replaceDuplicatedByNameAuthors(book);
		bookDAO.create(book);
	}

	public Book getBookById(int id) {
		return bookDAO.getEntityById(id);
	}

	public Book getBookByName(String Name) {
		return bookDAO.getByName(Name);
	}

	public Author getAuthorByName(String Name) {
		return authorDAO.getByName(Name);
	}

	public List<Book> GetAllBook() {
		return bookDAO.getAll();
	}

	public List<Author> GetAllAuthors() {
		return authorDAO.getAll();
	}

	public void updateBook(Book newbook) {
		Book oldBook = bookDAO.getEntityById(newbook.getId());
		oldBook.setAuthors(newbook.getAuthors());
		oldBook.setGenre(newbook.getGenre());
		oldBook.setName(newbook.getName());
		oldBook.setInformation(newbook.getInformation());
		// should replace authors by their already existed in database representations
		replaceDuplicatedByNameAuthors(oldBook);
		bookDAO.update(oldBook);
	}

	public void deleteBookById(int id) {
		bookDAO.delete(id);
	}

	/**
	 * Needed for prevent inserting to database authors with same names
	 * 
	 * @param entity
	 */
	private void replaceDuplicatedByNameAuthors(Book entity) {
		Set<Author> allAuthors = entity.getAuthors();

		Predicate<Author> isInDataBase = (author) -> authorDAO.getByName(author.getName()) == null ? false : true;

		Function<Author, Author> getFromDataBase = (author) -> authorDAO.getByName(author.getName());

		List<Author> whoInDataBase = allAuthors.stream().filter(isInDataBase).map(getFromDataBase)
				.collect(Collectors.toList());

		List<Author> whoIsNotInDataBase = allAuthors.stream().filter(isInDataBase.negate())
				.peek((newAuthor) -> authorDAO.create(newAuthor)).collect(Collectors.toList());

		Set<Author> ResultSet = new HashSet<Author>();

		ResultSet.addAll(whoInDataBase);

		ResultSet.addAll(whoIsNotInDataBase);

		entity.setAuthors(ResultSet);
	}
}
