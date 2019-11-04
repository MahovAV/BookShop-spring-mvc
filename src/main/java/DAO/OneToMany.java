package DAO;

import Domain.FlatWhichCouldWorkWithTenant;
import Domain.tenant;
import org.hibernate.SessionFactory;

import java.util.Set;

/**
 * Created by user on 03.11.2019.
 */

public class OneToMany extends abstractDao {

    public OneToMany(SessionFactory factory){
        super(factory);
    }

    public void addFlat(FlatWhichCouldWorkWithTenant flat,
                        Set<tenant> tenants){
        try{

            logger.info("session is open");
            session=factory.openSession();
            session.getTransaction().begin();
            logger.info("transaction is begin");
            //ВСЕЛЯЕМ ЖИЛЬЦОВ И ЗАПИСЫВАЕМ КТО ГДЕ ЖИВЕТ
            for(tenant t:tenants){
                t.setFlat(flat);

                flat.getTenants().add(t);
            }
            logger.info("having 2 configurated POJO");
            session.save(flat);
            //ADDING CASCADE


            session.getTransaction().commit();

        } finally {
            if (session!=null) {
                logger.info("session is closed");
                session.close();
            }
        }
    }
    public void relocate(tenant t,FlatWhichCouldWorkWithTenant flat){
        try{

            logger.info("session is open");
            session=factory.openSession();

            session.getTransaction().begin();

            tenant representation=session.get(tenant.class,t.getId());

            FlatWhichCouldWorkWithTenant flatrepr=session.get(FlatWhichCouldWorkWithTenant.class,flat.getId());

            representation.setFlat(flatrepr);

            session.save(representation);

            session.getTransaction().commit();

        } finally {
            if (session!=null) {
                logger.info("session is closed");
                session.close();
            }
        }
    }
    public void RenameFlat(tenant t,String name){
        try{

            logger.info("session is open");
            session=factory.openSession();

            session.getTransaction().begin();
            //getting and chaging pojo

            tenant repr=session.get(tenant.class,t.getId());

            repr.getFlat().setAdress(name);

            session.getTransaction().commit();

        } finally {
            if (session!=null) {
                logger.info("session is closed");
                session.close();
            }
        }
    }
    public void renameAllTenant(FlatWhichCouldWorkWithTenant flat,String name){
        try{

            logger.info("session is open");
            session=factory.openSession();

            session.getTransaction().begin();
            //getting and chaging pojo

            FlatWhichCouldWorkWithTenant repr=session.get(FlatWhichCouldWorkWithTenant.class,flat.getId());

            Set<tenant> tenants=repr.getTenants();

            for(tenant t:tenants){
                t.setName(name);
            }

            session.getTransaction().commit();

        } finally {
            if (session!=null) {
                logger.info("session is closed");
                session.close();
            }
        }
    }
    public void relocateFromFlatToFlat(FlatWhichCouldWorkWithTenant flat1,FlatWhichCouldWorkWithTenant flat2){

        try{

            logger.info("session is open");
            session=factory.openSession();

            session.getTransaction().begin();
            //getting and chaging pojo
            //should write validation
            FlatWhichCouldWorkWithTenant flat1rep=session.get(FlatWhichCouldWorkWithTenant.class,flat1.getId());

            FlatWhichCouldWorkWithTenant flat2rep=session.get(FlatWhichCouldWorkWithTenant.class,flat2.getId());


            Set<tenant> tenants=flat1rep.getTenants();
            for(tenant t:tenants){
                t.setFlat(flat2rep);
            }
            flat2rep.addTenn(tenants);
            flat1rep.setTenants(null);

            session.getTransaction().commit();

        } finally {
            if (session!=null) {
                logger.info("session is closed");
                session.close();
            }
        }


    }
}
