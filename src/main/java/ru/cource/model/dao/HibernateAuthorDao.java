package ru.cource.model.dao;

import java.util.HashSet;
import java.util.List;
import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;

import ru.cource.model.domain.Author;
import ru.cource.model.domain.Book;
import ru.cource.model.dao.HibernateGenericAbstractDao;

/**
 * DAO class for {@link Author}
 * 
 * @author AlexanderM-O
 *
 */

@Repository
public class HibernateAuthorDao extends HibernateGenericAbstractDao<Author> {

	HibernateAuthorDao() {
		super(Author.class);
	}

	@Override
	public void update(Author entity) {
		session = factory.getCurrentSession();

		session.update(entity);
	}

	@Override
	public void delete(int id) {
		session = factory.getCurrentSession();
		Author deletingAuthor = session.get(Author.class, id);
		for(Book b:deletingAuthor.getBooks()) {
			b.removeAuthor(deletingAuthor);
		}
		session.flush();
		session.delete(deletingAuthor);
	}

	@Override
	public void create(Author entity) {
		session = factory.getCurrentSession();
		session.save(entity);
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public List<Author> getAll() {
		List<Author> Data;
		session = factory.getCurrentSession();
		Query query = session.createQuery("FROM Author");
		Data = query.list();
		return Data;
	}

	@SuppressWarnings("rawtypes")
	public Author getByName(String name) {
		Author Data;
		session = factory.getCurrentSession();
		Query query = session.createQuery("FROM Author A WHERE name = :paramName");
		query.setParameter("paramName", name);
		Data = (Author) query.uniqueResult();
		return Data;
	}
}
