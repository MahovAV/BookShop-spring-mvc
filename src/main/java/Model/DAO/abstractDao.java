package Model.DAO;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by user on 03.11.2019.
 */
public abstract class abstractDao {
    //keeping all data for working with database
    protected static Logger logger=null;
    protected SessionFactory factory=null;
    protected Session session=null;
    public abstractDao(SessionFactory factory) {
        this.factory=factory;
    }
}
