package ru.cource.model.dao;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;

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

	@SuppressWarnings({ "unchecked", "hiding" })
	@Override
	public <E> E getEntityById(int id) {
		return (E) factory.getCurrentSession().get(entityClass, id);
	}
}
