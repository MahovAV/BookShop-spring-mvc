package ru.cource.model.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ru.cource.model.dao.AuthorDAO;
import ru.cource.model.dao.BookDAO;
import ru.cource.model.domain.Author;
import ru.cource.model.domain.Book;



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

    public Book getBookById(int id){
        return bookDAO.getById(id);
    }
    
    public Book getBookByName(String Name) {
		return bookDAO.getByName(Name);
    	
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
