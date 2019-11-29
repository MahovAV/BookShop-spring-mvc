package ru.cource.model.dao;

import java.util.List;

import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import ru.cource.model.domain.Author;



/**
 * Created by user on 05.11.2019.
 */

@Component
public class AuthorDAO extends AbstractDao{

    @Autowired
    public AuthorDAO(SessionFactory factory){
        super(factory);
        logger= LoggerFactory.getLogger(AuthorDAO.class);
        logger.info("AuthorDAO is created");
    }

    public void addauthor(Author author){
        session=factory.openSession();

        session.getTransaction().begin();
        session.save(author);

        session.getTransaction().commit();
    }

    public List<Author> getAll(){
        List<Author> Data;
        try {
            session = factory.openSession();
            Query query =session.createQuery("FROM Author");
            Data=query.list();

        } finally {
            session.close();
        }
        return Data;
    }
}
