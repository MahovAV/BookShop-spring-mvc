import org.apache.log4j.Logger;
import org.apache.log4j.xml.DOMConfigurator;

/**
 * Created by user on 02.11.2019.
 */
public class Main {
    private static Logger logger=Logger.getLogger(Main.class);

    public static void main(String ...argc) throws Exception {
        //CONFIGURATION OUT PROPERTIES
        DOMConfigurator.configure("target/classes/log4j.xml");

        logger.info("through not prop");
    }

}
