package ru.cource.model.dao;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by user on 03.11.2019.
 */
public abstract class AbstractDao {
    //keeping all data for working with database
    protected Logger logger=null;
    protected SessionFactory factory=null;
    protected Session session=null;
    public AbstractDao(SessionFactory factory) {
        this.factory=factory;
    }
}
