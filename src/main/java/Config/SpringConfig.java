package Config;

import Helper.AuthorRepresentation;
import Model.Domain.Author;
import Model.Domain.Book;
import Model.Service.BookShopService;
import org.hibernate.SessionFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import Helper.AllGenres;

import java.util.Set;

/**
 * Created by user on 12.11.2019.
 */
@Configuration
public class SpringConfig {
    @Bean
    public BookShopService getService(){
        org.hibernate.cfg.Configuration configuration = new org.hibernate.cfg.Configuration().configure();
        configuration.addAnnotatedClass(Book.class);
        configuration.addAnnotatedClass(Author.class);
        SessionFactory factorySession = configuration.buildSessionFactory();
        BookShopService bookShopService=new BookShopService(factorySession);
        return bookShopService;
    }

    @Bean
    public AllGenres genres(){
        return new AllGenres();
    }

    @Bean
    public AuthorRepresentation authorRepresentation(){
        return new AuthorRepresentation();
    }
}
