package ru.cource.config;

import org.flywaydb.core.Flyway;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;

import ru.cource.model.domain.Author;
import ru.cource.model.domain.Book;
import ru.cource.model.service.BookShopService;


/**
 * Created by user on 12.11.2019.
 */
@Configuration
@ComponentScan(basePackages = {"ru.cource.model.dao","ru.cource.controller"})
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
            logger.info("migration complete, bean flyway is created");
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
        logger.info("SessionFactory factory is created");
        return configuration.buildSessionFactory();
    }

    @Bean
    public BookShopService Service(){
        logger.info("getting "+BookShopService.class.getName()+" from SessionFactory");
        BookShopService bookShopService=new BookShopService();
        logger.info("Service is created");
        return bookShopService;
    }

}
