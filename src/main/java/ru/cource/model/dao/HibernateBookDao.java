package ru.cource.model.dao;

import ru.cource.model.domain.Author;
import ru.cource.model.domain.Book;

import org.hibernate.Session;
import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;

/**
 * Created by user on 05.11.2019.
 */
@Repository
public class HibernateBookDao extends HibernateGenericAbstractDao<Book> {

	HibernateBookDao() {
		super(Book.class);
		System.out.println("HibernateBookDao");
	}
	
	@Override
	public Book getEntityById(int id) {
		return (Book) super.getEntityById(id);
	}

	@Override
	public void update(Book entity) {
		//should throw exception if there is no book with that id
		session = factory.getCurrentSession();
		Book oldBook = session.get(Book.class, entity.getId());
		Set<Author> oldAuthors = oldBook.getAuthors();
		Set<Author> NewAuthors = entity.getAuthors();
		//should delete for each author link to new book 
		for (Author author : oldAuthors) {
			if (NewAuthors == null || !NewAuthors.contains(author)) {
				author.deleteBook(entity);
			}
		}
		session.merge(entity);
	}

	@Override
	public void delete(int id) {
		session = factory.getCurrentSession();
		// cannot execute it in open session!!!
		Book deletingBook = session.get(Book.class, id);
		Set<Author> authors = deletingBook.getAuthors();
		for (Author a : authors) {
			a.deleteBook(deletingBook);
			// NO NEED MERGE HERE AS WE WILL COMMIT
			// AUTOMATICLY WILL MANAGE IT VIA DIRTY CHECKING
		}

		// dont have relation ship with not existed book
		// could delete book
		session.delete(deletingBook);
	}

	@Override
	public void create(Book entity) {
		session = factory.getCurrentSession();

		Set<Author> allAuthors = entity.getAuthors();

		List<Author> whoInDataBase = new ArrayList<Author>();

		List<Author> whoIsNotInDataBase = new ArrayList<Author>();

		Predicate<Author> isInDataBase = (author) -> getAuthorByName(session, author.getName()).isPresent();

		Function<Author, Author> getFromDataBase = (pojoAuthor) -> {
			return getAuthorByName(session, pojoAuthor.getName()).get();
		};

		whoInDataBase = allAuthors.stream().filter(isInDataBase).map(getFromDataBase).collect(Collectors.toList());

		whoIsNotInDataBase = allAuthors.stream().filter(isInDataBase.negate())
				.peek((newAuthor) -> session.save(newAuthor)).collect(Collectors.toList());

		Set<Author> ResultSet = new HashSet<Author>();

		ResultSet.addAll(whoInDataBase);

		ResultSet.addAll(whoIsNotInDataBase);

		entity.setAuthors(ResultSet);
		session.save(entity);
	}

	public Book getByName(String name) {
		Book Data;
		session = factory.getCurrentSession();
		Query query = session.createQuery("FROM Book A WHERE name = :paramName");
		query.setParameter("paramName", name);
		Data = (Book) query.uniqueResult();
		return Data;
	}

	private static Optional<Author> getAuthorByName(Session session, String name) {
		// if there is no object we return null

		Query query = session.createQuery("FROM Author A WHERE name = :paramName");
		query.setParameter("paramName", name);
		return Optional.ofNullable((Author) query.uniqueResult());
	}

	@Override
	public List<Book> getAll() {
		List<Book> Data;
		session = factory.getCurrentSession();
		Query query = session.createQuery("FROM Book");
		Data = query.list();
		return Data;
	}
}
