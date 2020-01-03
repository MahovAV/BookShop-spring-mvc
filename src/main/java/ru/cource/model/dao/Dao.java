package ru.cource.model.dao;

import java.io.Serializable;
import java.util.List;

/**
 * DAO API
 * 
 * @author AlexanderM-O
 *
 * @param <E> Entity which we will store
 */
public interface Dao<E> {
	public List<E> getAll();

	public <E> E getEntityById(int id);

	public void update(E entity);

	public void delete(int id);

	public void create(E entity);
}
