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
	}

	@Override
	public Book getEntityById(int id) {
		return (Book) super.getEntityById(id);
	}

	@Override
	public void update(Book entity) {
		// should throw exception if there is no book with that id
		session = factory.getCurrentSession();

		session.update(entity);
	}

	@Override
	public void delete(int id) {
		session = factory.getCurrentSession();
		Book deletingBook = session.get(Book.class, id);
		Set<Author> authors = deletingBook.getAuthors();
		for (Author a : authors) {
			a.deleteBook(deletingBook);
		}
		session.delete(deletingBook);
	}

	@Override
	public void create(Book entity) {
		session = factory.getCurrentSession();

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

	@Override
	public List<Book> getAll() {
		List<Book> Data;
		session = factory.getCurrentSession();
		Query query = session.createQuery("FROM Book");
		Data = query.list();
		return Data;
	}
}
