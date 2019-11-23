package Model.DAO;

import Model.Domain.Author;
import org.hibernate.SessionFactory;

import org.hibernate.query.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Import;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Created by user on 05.11.2019.
 */

@Import(Config.SpringConfig.class)
@Component
public class AuthorDAO extends abstractDao{
    @Autowired
    public AuthorDAO(SessionFactory factory){
        super(factory);
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
