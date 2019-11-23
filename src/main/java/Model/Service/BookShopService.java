package Model.Service;

import Model.DAO.AuthorDAO;
import Model.DAO.BookDAO;
import Model.Domain.Book;
import Model.Domain.Author;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

/**
 * Created by user on 07.11.2019.
 */
@Service("MainService")
public class BookShopService {
    private static Logger logger= LoggerFactory.getLogger(BookShopService.class);
    @Autowired
    private  BookDAO bookDAO;
    @Autowired
    private  AuthorDAO authorDAO;

    public BookShopService(){
        logger.info(BookShopService.class.getName()+"is created");
    }

    public void createBook(Book book){
        bookDAO.addBook(book);
    }

    public Book getById(int id){
        return bookDAO.getById(id);
    }

    public List<Book> GetAllBook(){
        return bookDAO.getAll();
    }

    public List<Author> GetAllAuthors(){
        return authorDAO.getAll();
    }

    public void updateBook(Book book){
        bookDAO.update(book);
    }

    public void deleteById(int id){
        bookDAO.delete(id);
    }
}
