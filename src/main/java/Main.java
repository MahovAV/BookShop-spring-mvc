
import DAO.ManyToOne;
import DAO.PersonDao;
import Domain.PersonData;
import Domain.PersonEntity;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import Domain.Flat;
import Domain.tenant;

import java.util.HashSet;
import java.util.Set;

/*
    TODO ADD VALIDATION MECHANIZM
    TODO ADD COMPOSITE
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
        configuration.addAnnotatedClass(tenant.class);
        configuration.addAnnotatedClass(Flat.class);
        factorySession = configuration.buildSessionFactory();
        logger.info("Connection SUCCESS");
    }

    public static void main(String ...argc) throws Exception {
        Flat flat=new Flat("pole");

        tenant person1=new tenant("sasha");
        tenant person2=new tenant("dan");
        tenant person3=new tenant("jack");

        Set<tenant> tenants=new HashSet<tenant>();
        tenants.add(person1);
        tenants.add(person2);
        tenants.add(person3);

        ManyToOne dao=new ManyToOne(factorySession);
        dao.addFlat(flat,tenants);
    }

}
