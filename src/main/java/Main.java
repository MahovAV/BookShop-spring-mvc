
import DAO.PersonDao;
import Domain.PersonData;
import Domain.PersonEntity;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/*
    TODO ADD OTHER OWNER OF DATA(WORKER)
    TODO ADD CHANGE CASCADE METHODE
    TODO ADD  ONE TO MANY PERSON THINGS
    TODO ADD  MANY TO MANY RELATIONSHIP(WORKER HAS A LOT OF WORK AND WORK HAS A LOT OF WORKERS)
    todo add registration and logging

 */
public class Main {
    private static Logger logger=LoggerFactory.getLogger(Main.class);
    private static SessionFactory factorySession;

    static {


        Configuration configuration = new Configuration().configure();
        configuration.addAnnotatedClass(PersonEntity.class);
        configuration.addAnnotatedClass(PersonData.class);
        factorySession = configuration.buildSessionFactory();
        logger.info("Connection SUCCESS");
    }

    public static void main(String ...argc) throws Exception {
        PersonEntity person=new PersonEntity();

        PersonData data=new PersonData();
        data.setAdress("somePlace");
        data.setHabits("code");

        person.setName("sana");
        person.setPassword("1234");
        person.setData(data);

        PersonDao dao=new PersonDao(factorySession);
        dao.add(person);
    }

}
