package ru.cource.model.dao;

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

	@SuppressWarnings("hiding")
	public <E> E getEntityById(int id);

	public void update(E entity);

	public void delete(int id);

	public void create(E entity);
}
