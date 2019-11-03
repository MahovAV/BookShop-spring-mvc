package DAO;
import Domain.PersonEntity;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by user on 02.11.2019.
 */
public class PersonDao {
    private static Logger logger= LoggerFactory.getLogger(PersonDao.class);
    SessionFactory factory=null;
    Session session=null;

    public PersonDao(SessionFactory factory) {
        this.factory=factory;
    }


    public void add(PersonEntity pers){
        try{
            logger.info("session is open");
        session=factory.openSession();
        session.getTransaction().begin();
            logger.info("transaction is begin");
        session.save(pers);
        session.getTransaction().commit();

        } finally {
            if (session!=null) {
                logger.info("session is closed");
                session.close();
            }
        }
    }

    public void replace(PersonEntity replacer,Long id){
        session.getTransaction().begin();
        PersonEntity personEntity=(PersonEntity)session.get(PersonEntity.class,id);
        //get by class and id
        personEntity=replacer;
        session.update(personEntity);
        session.getTransaction().commit();
    }
}
