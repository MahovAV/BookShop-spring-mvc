package DAO;

import Domain.Flat;
import Domain.tenant;
import org.hibernate.SessionFactory;

import java.util.Set;


public class ManyToOne extends abstractDao implements Many {

    public ManyToOne(SessionFactory factory){
        super(factory);
    }


    public void addFlat(final Flat flat, Set<tenant> tenants) {
        try{
            logger.info("session is open");
            session=factory.openSession();
            session.getTransaction().begin();
            logger.info("transaction is begin");
            //ВСЕЛЯЕМ ЖИЛЬЦОВ
            for(tenant t:tenants){
                t.setFlat(flat);
            }
            //ДОБАВЛЯЕМ КВАРТИРУ И ЖИЛЬЦОВ В БАЗУ ДАННЫХ
            session.save(flat);

            for(tenant t:tenants){
                session.save(t);
            }
            session.getTransaction().commit();

        } finally {
            if (session!=null) {
                logger.info("session is closed");
                session.close();
            }
        }


    }

    public void populate(Flat flat, tenant tenant) {

    }
}
