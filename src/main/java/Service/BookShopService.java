package Service;

import DAO.AuthorDAO;
import DAO.BookDAO;
import Domain.Book;
import Domain.Author;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by user on 07.11.2019.
 */

// TODO: 07.11.2019 implement create book method which will handle authors also 
public class BookShopService {
    private  BookDAO bookDAO;
    private  AuthorDAO authorDAO;

    public BookShopService(SessionFactory factory){
        bookDAO=new BookDAO(factory);
        authorDAO=new AuthorDAO(factory);
    }

    //********************************************************
    //  PUT HERE POJO WITH RELATIONSHIP
    //********************************************************
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
}
