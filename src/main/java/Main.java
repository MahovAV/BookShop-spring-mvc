
import DAO.OneToMany;
import Domain.FlatWhichCouldWorkWithTenant;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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
        configuration.addAnnotatedClass(FlatWhichCouldWorkWithTenant.class);
        factorySession = configuration.buildSessionFactory();
        logger.info("Connection SUCCESS");
    }

    public static void main(String ...argc) throws Exception {
        FlatWhichCouldWorkWithTenant flat1=new FlatWhichCouldWorkWithTenant("pole");

        FlatWhichCouldWorkWithTenant flat2=new FlatWhichCouldWorkWithTenant("SOVIET");

        tenant person1=new tenant("sasha");
        tenant person2=new tenant("dan");
        tenant person3=new tenant("jack");

        tenant person4=new tenant("O");
        tenant person5=new tenant("K");

        Set<tenant> tenants1=new HashSet<tenant>();
        tenants1.add(person1);
        tenants1.add(person2);
        tenants1.add(person3);

        Set<tenant> tenants2=new HashSet<tenant>();
        tenants2.add(person4);
        tenants2.add(person5);

        OneToMany dao=new OneToMany(factorySession);
        dao.addFlat(flat1,tenants1);

        dao.addFlat(flat2,tenants2);

        dao.relocate(person3,flat2);

        dao.relocate(person1,flat2);

        dao.RenameFlat(person1,"JOKE");

        dao.renameAllTenant(flat2,"ASSHOLE");

        dao.relocateFromFlatToFlat(flat2,flat1);

        dao.DeketeFlat(flat2);

        dao.DeketeFlat(flat1);

    }

}
