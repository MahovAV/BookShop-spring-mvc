package ru.cource.model.dao;

import java.io.Serializable;
import java.util.List;

public interface Dao<E> {
	public List<E> getAll();

	public abstract<E> E getEntityById(int id);

	public abstract void update(E entity);

	public abstract void delete(int id);

	public abstract void create(E entity);
}
