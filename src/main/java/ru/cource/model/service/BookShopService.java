package ru.cource.model.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ru.cource.model.dao.HibernateAuthorDao;
import ru.cource.model.dao.HibernateBookDao;
import ru.cource.model.domain.Author;
import ru.cource.model.domain.Book;



/**
 * Created by user on 07.11.2019.
 */
@Transactional
@Service
public class BookShopService implements BookShopServiceInterface {
    @Autowired
    private  HibernateBookDao bookDAO;
    @Autowired
    private  HibernateAuthorDao authorDAO;

    public BookShopService(){
    }

    public void createBook(Book book){
        bookDAO.create(book);
    }

    public Book getBookById(int id){
        return bookDAO.getEntityById(id);
    }
    
    public Book getBookByName(String Name) {
		return bookDAO.getByName(Name);
    }
    
    public Author getAuthorByName(String Name) {
		return authorDAO.getByName(Name);
    	
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

    public void deleteBookById(int id){
        bookDAO.delete(id);
    }
}
