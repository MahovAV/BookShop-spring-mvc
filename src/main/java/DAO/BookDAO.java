package DAO;

import Domain.Book;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import Domain.Author;
import org.hibernate.query.Query;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by user on 05.11.2019.
 */

// TODO: 08.11.2019 ADD CLOSE TRANSACTION AND STRATEGY PATTERN AND INTERFACE 

public class BookDAO extends abstractDao {
    public BookDAO(SessionFactory factory){
        super(factory);
    }

    public void addBook(Book book){
        try {
            session = factory.openSession();
            session.getTransaction().begin();

            List<String> namesOfAutors=new ArrayList<String>();
            Set<Author> authors =book.getAuthors();

            for(Author author:authors){
                namesOfAutors.add(author.getName());
            }

            List<Author> WhoInDataBase=new ArrayList<Author>();

            List<Author> WhoIsNotInDataBase=new ArrayList<Author>();

            for(int i=0;i<namesOfAutors.size();++i){
                if(BookDAO.getExisted(session,namesOfAutors.get(i))!=null){
                    //will add as existed
                    Author author=BookDAO.getExisted(session,namesOfAutors.get(i));
                    boolean itWas=false;
                    //check whether it was or not
                    for(Author a:WhoInDataBase){
                        if(a.getName().equals(author.getName())){
                            //already here shouldn't add
                            itWas=true;
                        }
                    }
                    if(!itWas){
                        //adding new if wasn t in database
                        WhoInDataBase.add(author);
                    }
                }else{
                    //just finding object from book and adding him
                    Author auth=null;
                    for(Author author:authors){
                        if(author.getName().equals(namesOfAutors.get(i))){
                            auth=author;
                        }
                    }
                    WhoIsNotInDataBase.add(auth);
                }
            }
            Set<Author> ResultSet = new HashSet<Author>();

            //adding into result Set all entity
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
    public static Author getExisted(Session session,String name) {
        Query query = session.createQuery("FROM Author A WHERE name = :paramName");
        query.setParameter("paramName", name);
        return (Author) query.uniqueResult();
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
}
