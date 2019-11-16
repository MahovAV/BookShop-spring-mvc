package Model.Service;

import Model.DAO.AuthorDAO;
import Model.DAO.BookDAO;
import Model.Domain.Book;
import Model.Domain.Author;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by user on 07.11.2019.
 */

// TODO: 07.11.2019 implement create book method which will handle authors also
@Service
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

    public void updateBook(Book book){
        bookDAO.update(book);
    }

    public void deleteById(int id){
        bookDAO.delete(id);
    }
}
