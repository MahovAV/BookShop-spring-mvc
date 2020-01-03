package ru.cource.model.dao;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Entity;
import javax.transaction.Transactional;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;

import ru.cource.model.domain.Author;
import ru.cource.model.domain.Book;

/**
 * Encapsulate hibernate basic stuff
 * 
 * @author AlexanderM-O
 *
 */
public abstract class HibernateGenericAbstractDao<E> implements Dao<E> {
	@Autowired
	protected SessionFactory factory;

	protected Session session = null;

	final Class<E> entityClass;

	HibernateGenericAbstractDao(Class<E> entityClass) {
		this.entityClass = entityClass;
	}

	@Override
	public <E> E getEntityById(int id) {
		return (E) factory.getCurrentSession().get(entityClass, id);
	}
}
