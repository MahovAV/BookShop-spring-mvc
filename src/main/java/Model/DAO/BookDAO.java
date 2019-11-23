package Model.DAO;

import Config.SpringConfig;
import Helper.AuthorRepresentation;
import Model.Domain.Book;
import Model.Service.BookShopService;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import Model.Domain.Author;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Import;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by user on 05.11.2019.
 */

// TODO: 08.11.2019 ADD CLOSE TRANSACTION AND STRATEGY PATTERN AND INTERFACE
@Import(SpringConfig.class)
@Component
public class BookDAO extends abstractDao {

    @Autowired
    private AuthorRepresentation representation;

    @Autowired
    public BookDAO(SessionFactory factory){
        super(factory);
    }

    public void addBook(Book book){
        try {
            session = factory.openSession();
            session.getTransaction().begin();

            List<String> namesOfAutors=representation.getListOfStrings(book.getAuthors());

            Set<Author> authors =book.getAuthors();

            List<Author> WhoInDataBase=new ArrayList<Author>();

            List<Author> WhoIsNotInDataBase=new ArrayList<Author>();

            representation.SplitAuthors(namesOfAutors,WhoInDataBase, WhoIsNotInDataBase,authors,session);

            Set<Author> ResultSet = new HashSet<Author>();

            for(Author author:WhoInDataBase){
                ResultSet.add(author);
            }
            for(Author author:WhoIsNotInDataBase){
                session.save(author);
                ResultSet.add(author);
            }

            book.setAuthors(ResultSet);
            session.save(book);
            session.getTransaction().commit();
        } finally {
            session.disconnect();
        }
    }





    public void addAuthorToBook(Book book,Author author){
        try {
            session = factory.openSession();

            session.getTransaction().begin();
            book.getAuthors().add(author);

            session.getTransaction().commit();
        } finally {
            session.close();
        }
    }

    public Book getById(int id){
        //return POJO
        Book Data;
        try {
            session = factory.openSession();
            Data=session.get(Book.class,id);
        } finally {
            session.close();
        }
        return Data;
    }

    public List<Book> getAll(){
        List<Book> Data;
        try {
            session = factory.openSession();
            Query query =session.createQuery("FROM Book");
            Data=query.list();

        } finally {
            session.close();
        }
        return Data;
    }

    public void update(Book book){
        try {
        session = factory.openSession();
        session.getTransaction().begin();
            Book oldBook=session.get(Book.class,book.getId());
            Set<Author> oldAuthors=oldBook.getAuthors();
            Set<Author> NewAuthors=book.getAuthors();
            for(Author a:oldAuthors){
                if(NewAuthors==null||!NewAuthors.contains(a)){
                    //should change author and merge him
                    a.deleteBook(book);
                }
            }
            session.merge(book);
            //WILL CASCADE TO AUTHORS TOO
        session.getTransaction().commit();
        } finally {
            session.close();
        }
    }
    public void delete(int id){
        try {
        session = factory.openSession();
        session.getTransaction().begin();
            //cannot execute it in open session!!!
        Book deletingBook=session.get(Book.class,id);
        Set<Author> authors=deletingBook.getAuthors();
        for (Author a: authors) {
            a.deleteBook(deletingBook);
            //NO NEED MERGE HERE AS WE WILL COMMIT
            //AUTOMATICLY WILL MANAGE IT VIA DIRTY CHECKING
        }

            //dont have relation ship with not existed book
            //could delete book
            session.delete(deletingBook);
        session.getTransaction().commit();
        } finally {
            session.close();
        }
    }
}
