package ru.cource.model.dao;

import ru.cource.model.domain.Author;
import ru.cource.model.domain.Book;

import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;

import java.util.HashSet;
import java.util.List;

/**
 * DAO class for {@link Book}
 * 
 * @author AlexanderM-O
 *
 */
@Repository
public class HibernateBookDao extends HibernateGenericAbstractDao<Book> {

	HibernateBookDao() {
		super(Book.class);
	}

	@SuppressWarnings("unchecked")
	@Override
	public Book getEntityById(int id) {
		return (Book) super.getEntityById(id);
	}

	@Override
	public void update(Book entity) {
		session = factory.getCurrentSession();
		session.update(entity);
	}

	@Override
	public void delete(int id) {
		session = factory.getCurrentSession();
		Book deletingBook = session.get(Book.class, id);
		deletingBook.setAuthors(new HashSet<Author>());
		session.flush();
		session.delete(deletingBook);
	}

	@Override
	public void create(Book entity) {
		session = factory.getCurrentSession();
		session.save(entity);
	}

	@SuppressWarnings("rawtypes")
	public Book getByName(String name) {
		Book Data;
		session = factory.getCurrentSession();
		Query query = session.createQuery("FROM Book A WHERE name = :paramName");
		query.setParameter("paramName", name);
		Data = (Book) query.uniqueResult();
		return Data;
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public List<Book> getAll() {
		List<Book> Data;
		session = factory.getCurrentSession();
		Query query = session.createQuery("FROM Book");
		Data = query.list();
		return Data;
	}
}
