package ru.cource.model.dao;

import java.util.List;

import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Repository;

import ru.cource.model.domain.Author;
import ru.cource.model.domain.Book;
import ru.cource.model.domain.User;

/**
 * DAO class for {@link User}
 * 
 * @author AlexanderM-O
 *
 */

@Repository
public class HibernateUserDao extends HibernateGenericAbstractDao<User> {

	HibernateUserDao() {
		super(User.class);
	}

	@Override
	public List getAll() {
		List<User> Data;
		session = factory.getCurrentSession();
		Query query = session.createQuery("FROM User");
		Data = query.list();
		return Data;
	}

	@Override
	public void update(User entity) {
		session = factory.getCurrentSession();
		session.merge(entity);
	}

	@Override
	public void delete(int id) {
		session = factory.getCurrentSession();
		session.delete(session.get(User.class, id));
	}

	@Override
	public void create(User entity) {
		session = factory.getCurrentSession();
		session.save(entity);
	}

	public User getByName(String name) {
		User Data;
		session = factory.getCurrentSession();
		Query query = session.createQuery("FROM User A WHERE name = :paramName");
		query.setParameter("paramName", name);
		Data = (User) query.uniqueResult();
		return Data;
	}

	public User getByEmail(String email) {
		User Data;
		session = factory.getCurrentSession();
		Query query = session.createQuery("FROM User A WHERE email = :paramName");
		query.setParameter("paramName", email);
		Data = (User) query.uniqueResult();
		return Data;
	}

}
