
import DAO.AuthorDAO;
import DAO.BookDAO;
import Domain.Book;
import Domain.TypeEnum;
import Domain.addres;
import Domain.Author;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

/*
    TODO ADD GENERIC METHOD IN ABSTRACT DAO
    TODO ADD VALIDATION MECHANIZM
    TODO ADD OTHER OWNER OF DATA(WORKER)
    TODO ADD  MANY TO MANY RELATIONSHIP(WORKER HAS A LOT OF WORK AND WORK HAS A LOT OF WORKERS)
    todo add registration and logging
 */


public class Main {
    private static Logger logger=LoggerFactory.getLogger(Main.class);
    private static SessionFactory factorySession;
    static {
        Configuration configuration = new Configuration().configure();
        configuration.addAnnotatedClass(Book.class);
        configuration.addAnnotatedClass(Author.class);
        factorySession = configuration.buildSessionFactory();
        logger.info("Connection SUCCESS");
    }

    public static void main(String ...argc) throws Exception {
        AuthorDAO authorDAO=new AuthorDAO(factorySession);

        BookDAO bookDAO=new BookDAO(factorySession);

        Author author=new Author();
        author.setName("oryell");
        author.setAddres(new addres("AVENU"));

        Book book=new Book("1984",new HashSet<String>());
        book.getNameOfCustommers().add("JESSY");
        book.getNameOfCustommers().add("WOLTER");

        authorDAO.addauthor(author);
        bookDAO.addBook(book);
    }

}
