package Config;

import Helper.AuthorRepresentation;
import Model.Domain.Author;
import Model.Domain.Book;
import Model.Service.BookShopService;
import org.flywaydb.core.Flyway;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.*;
import Helper.GenreRepository;


/**
 * Created by user on 12.11.2019.
 */
@Configuration
@ComponentScan(basePackages = {"Model.DAO","Controller"})
public class SpringConfig {
    private static Logger logger= LoggerFactory.getLogger(SpringConfig.class);

    private static org.hibernate.cfg.Configuration configuration;

    @Bean
    public Flyway flyway() {
        try {
            logger.info("checking database and making migration");
            Flyway flyway = new Flyway();
            flyway.setDataSource("jdbc:mysql://localhost:3306/BASE?useLegacyDatetimeCode=false&serverTimezone=UTC","root","1234");
            flyway.migrate();
            logger.info("migration complete");
            return flyway;
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }

    }


    @Bean
    @DependsOn("flyway")
    public SessionFactory getFactory() {
        logger.info("get SessionFactory from Configuration");
        configuration = new org.hibernate.cfg.Configuration().configure();
        configuration.addAnnotatedClass(Book.class);
        configuration.addAnnotatedClass(Author.class);
        return configuration.buildSessionFactory();
    }


    @Bean
    @DependsOn("getFactory")
    public BookShopService Service(){
        logger.info("getting "+BookShopService.class.getName()+" from SessionFactory");
        BookShopService bookShopService=new BookShopService();
        return bookShopService;
    }

    @Bean
    public GenreRepository genres(){
        return new GenreRepository();
    }

    @Bean
    public AuthorRepresentation authorRepresentation(){
        return new AuthorRepresentation();
    }
}
