package ru.cource.model.service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
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

	@Override
	public void createBook(Book book) {
		loadPersistedEntityAndPersistNew(book);
		bookDAO.create(book);
	}

	public Book findBookById(int id) {
		return bookDAO.getEntityById(id);
	}

	public Book findBookByName(String Name) {
		return bookDAO.getByName(Name);
	}

	@Override
	public Author findAuthorById(int id) {
		return authorDAO.getEntityById(id);
	}

	@Override
	public Author findAuthorByName(String Name) {
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
		oldBook.setBookCoverFileName(newbook.getBookCoverFileName());
		// should replace authors by their already existed in database representations
		loadPersistedEntityAndPersistNew(oldBook);
		bookDAO.update(oldBook);
	}

	/**
	 * Used to load stored in database entities and persist new entities them.
	 * 
	 * @param entity
	 */
	private void loadPersistedEntityAndPersistNew(Book entity) {
		Set<Author> allAuthors = entity.getAuthors();

		Predicate<Author> isInDataBase = (author) -> authorDAO.getByName(author.getName()) == null ? false : true;

		List<Author> whoInDataBase = allAuthors.stream().filter(isInDataBase)
				.map((author) -> authorDAO.getByName(author.getName())).collect(Collectors.toList());

		List<Author> whoIsNotInDataBase = allAuthors.stream().filter(isInDataBase.negate())
				.peek((newAuthor) -> authorDAO.create(newAuthor)).collect(Collectors.toList());

		Set<Author> ResultSet = new HashSet<Author>();

		ResultSet.addAll(whoInDataBase);

		ResultSet.addAll(whoIsNotInDataBase);

		entity.setAuthors(ResultSet);
	}

	/**
	 * By domain rules each book exists in database
	 */
	@Override
	public void updateAuthor(Author newAuthor) {
		Author oldAuthor = authorDAO.getEntityById(newAuthor.getId());
		for (Book b : oldAuthor.getBooks()) {
			b.removeAuthor(oldAuthor);
		}
		oldAuthor.setAddres(newAuthor.getAddres());
		oldAuthor.setName(newAuthor.getName());
		oldAuthor.setInformation(newAuthor.getInformation());
		oldAuthor.setAvatarFileName(newAuthor.getAvatarFileName());
		oldAuthor.setBooks(newAuthor.getBooks());
		// should replace books by their already existed in database representations
		loadPersistedEntity(oldAuthor);
		for (Book b : oldAuthor.getBooks()) {
			// due to inversive end.
			b.addAuthor(oldAuthor);
		}
		authorDAO.update(oldAuthor);
	}

	/**
	 * Used to get persisted entity
	 * 
	 * @param entity
	 */
	private void loadPersistedEntity(Author author) {
		Set<Book> whoInDataBase = author.getBooks().stream().map((book) -> bookDAO.getByName(book.getName()))
				.collect(Collectors.toSet());
		author.setBooks(whoInDataBase);
	}

	public void deleteBookById(int id) {
		bookDAO.delete(id);
	}

	@Override
	public void deleteAuthorById(int id) {
		authorDAO.delete(id);
	}
}
