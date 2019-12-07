package ru.cource.model.dao;

import ru.cource.config.SpringConfig;
import ru.cource.model.domain.Author;
import ru.cource.model.domain.Book;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Import;
import org.springframework.stereotype.Component;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;

/**
 * Created by user on 05.11.2019.
 */

// TODO: 08.11.2019 ADD CLOSE TRANSACTION AND STRATEGY PATTERN AND INTERFACE
@Component
public class BookDAO extends AbstractDao {


    @Autowired
    public BookDAO(SessionFactory factory){
        super(factory);
        logger= LoggerFactory.getLogger(BookDAO.class);
        logger.info("BookDAO is created");
    }

    public void addBook(Book book){
        try {
            session = factory.openSession();
            session.getTransaction().begin();

            Set<Author> allAuthors =book.getAuthors();

            List<Author> whoInDataBase=new ArrayList<Author>();

            List<Author> whoIsNotInDataBase=new ArrayList<Author>();

        	Predicate<Author> isInDataBase=(author)->getAuthorByName(session,author.getName()).isPresent();

        	Function<Author,Author> getFromDataBase=(pojoAuthor)->{
        		return getAuthorByName(session,pojoAuthor.getName()).get();
        	};   	

        	whoInDataBase=allAuthors.stream()
        								 .filter(isInDataBase)
        								 .map(getFromDataBase)
        								 .collect(Collectors.toList());

        	whoIsNotInDataBase=allAuthors.stream()
        			    					  .filter(isInDataBase.negate())
        			    					  .peek((newAuthor)->session.save(newAuthor))
        									  .collect(Collectors.toList());

            Set<Author> ResultSet = new HashSet<Author>();
            
            ResultSet.addAll(whoInDataBase);
            
            ResultSet.addAll(whoIsNotInDataBase);

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
    
	public Book getByName(String name) {
        Book Data;
        try {
            session = factory.openSession();
            Query query = session.createQuery("FROM Book A WHERE name = :paramName");
            query.setParameter("paramName", name);
            Data= (Book)query.uniqueResult();
        } finally {
            session.close();
        }
        return Data;
	}
    
    private static Optional<Author> getAuthorByName(Session session,String name) {
    	//if there is no object we return null
    	//rewrite with usage of optional
    	
        Query query = session.createQuery("FROM Author A WHERE name = :paramName");
        query.setParameter("paramName", name);
        return Optional.ofNullable((Author)query.uniqueResult());
    }


}
