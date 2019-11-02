
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/*
    todo add connection to database
    todo add Hibernate
    todo add Person Entity
    todo add registration and logging

 */
public class Main {
    private static Logger logger=LoggerFactory.getLogger(Main.class);

    static {
        Configuration configuration = new Configuration().configure();
        StandardServiceRegistryBuilder builder = new StandardServiceRegistryBuilder().applySettings(configuration.getProperties());
        SessionFactory factorySession = configuration.buildSessionFactory(builder.build());
        logger.info("Connection SUCCESS");
    }

    public static void main(String ...argc) throws Exception {

        logger.info("Magic is work");
    }

}
