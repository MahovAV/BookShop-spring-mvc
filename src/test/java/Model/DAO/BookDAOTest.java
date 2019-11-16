package Model.DAO;

import Model.Domain.Book;
import Model.Domain.addres;
import Model.Domain.Author;
import Model.Domain.enumOfGenres;
import Model.Service.BookShopService;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.junit.*;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;

/**
 * Created by user on 06.11.2019.
 */

public class BookDAOTest {
    private static Logger logger= LoggerFactory.getLogger(BookDAOTest.class);
    private static Configuration configuration;
    private static SessionFactory factorySession;
    private static BookShopService bookShopService;
    //// TODO: 15.11.2019 make method which will return us object also needed
    // pathern command

    @Test
    public void WithouAuthor(){
        configuration = new Configuration().configure();
        configuration.addAnnotatedClass(Book.class);
        configuration.addAnnotatedClass(Author.class);
        factorySession = configuration.buildSessionFactory();
        bookShopService=new BookShopService(factorySession);


        Book book=new Book("1984",new ArrayList<String>());
        book.getNameOfCustommers().add("JESSY");
        book.getNameOfCustommers().add("WOLTER");

        logger.info("putting book without author");
        bookShopService.createBook(book);
        //HAVE PERSISTED BOOK  need check this
        Book bookFromShop=bookShopService.getById(book.getId());

        Assert.assertTrue(bookFromShop.equals(book));
    }

    @Test
    public void OneNewAuthor(){
        configuration = new Configuration().configure();
        configuration.addAnnotatedClass(Book.class);
        configuration.addAnnotatedClass(Author.class);
        factorySession = configuration.buildSessionFactory();
        bookShopService=new BookShopService(factorySession);

        Book book=new Book("1984",new ArrayList<String>());
        book.getNameOfCustommers().add("JESSY");
        book.getNameOfCustommers().add("WOLTER");

        Author marks=new Author("marks");
        marks.setAddres(new addres("USA"));

        Author oryell=new Author("Pushkin");
        marks.setAddres(new addres("Russia"));

        book.getAuthors().add(marks);
        book.getAuthors().add(oryell);
        bookShopService.createBook(book);

        Book bookFromShop=bookShopService.getById(book.getId());
        //*************************************************
        //one book and one author
        //*************************************************

        Set<Author> AuthorsFromBookFromShop=bookFromShop.getAuthors();

        //each author and book are related
        for(Author a:AuthorsFromBookFromShop){
            Assert.assertTrue(a.getBooks().contains(bookFromShop));
        }
    }

    @Test
    public void AlreadyExistingAuthorAndTwoBooks(){
        configuration = new Configuration().configure();
        configuration.addAnnotatedClass(Book.class);
        configuration.addAnnotatedClass(Author.class);
        factorySession = configuration.buildSessionFactory();
        bookShopService=new BookShopService(factorySession);


        Book book=new Book("1984",new ArrayList<String>());
        book.getNameOfCustommers().add("JESSY");
        book.getNameOfCustommers().add("WOLTER");

        Book book2=new Book("Narny",new ArrayList<String>());
        book2.getNameOfCustommers().add("Jack");

        Author marks=new Author("marks");
        marks.setAddres(new addres("USA"));

        Author oryell=new Author("oryell");
        oryell.setAddres(new addres("Russia"));

        Author CopyOfMarks1=marks;

        Author CreatedCopyOfMarks=new Author("marks");
        CreatedCopyOfMarks.setAddres(new addres("Created"));

        book.getAuthors().add(marks);
        book.getAuthors().add(oryell);

        //book already have marks so we don t need have copy
        book2.getAuthors().add(marks);
        book2.getAuthors().add(CopyOfMarks1);
        book2.getAuthors().add(CreatedCopyOfMarks);



        bookShopService.createBook(book);
        bookShopService.createBook(book2);
        //should get exception here or some notify

        List<Book> books=bookShopService.GetAllBook();

        List<Author> authors=bookShopService.GetAllAuthors();
        //*************************************************
        //SHOULD HAVE 2 BOOK AND 2 AUTHORS
        //*************************************************
        // TODO: 10.11.2019 add exception support

        Assert.assertEquals(2,books.size());

        Assert.assertEquals(2,authors.size());

        //*************************************************
        //CORRECT RELATIONSHIP (BOOK->AUTHOR->BOOK)
        //*************************************************

        for(Book b:books){
            for (Author a:b.getAuthors()){
                Assert.assertTrue(a.getBooks().contains(b));
            }
        }
    }
    @Test
    public void UpdateTest(){
        BookShopService shopService=BookDAOTest.getService();

        Book book=new Book("1984",new ArrayList<String>());
        book.setGenre(new HashSet<enumOfGenres>(Arrays.asList(enumOfGenres.ADVENTURE,enumOfGenres.HORROR)));
        Author marks=new Author("marks");
        marks.setAddres(new addres("USA"));
        book.setAuthors(new HashSet<Author>(Arrays.asList(marks)));
        //was
        shopService.createBook(book);
        //delete author(bot not from database!!!),delete 1 GENRE
        //add new genre

        book.removeAuthor(marks);
        book.setGenre(new HashSet<enumOfGenres>(Arrays.asList(enumOfGenres.ROMANS)));


        //Should have:1.marks in data base and have no link to book
        //2.book from data base and book POJO are equivalent
        shopService.updateBook(book);

        List<Author> authors=shopService.GetAllAuthors();
        Assert.assertEquals(1,authors.size());
        Assert.assertEquals(authors.get(0).getBooks().size(),0);
        //should check book

        Assert.assertTrue(book.equals(shopService.getById(book.getId())));


        //there is no changes should have the same result

    }

    @Test
    public void DeleteTestOneAuthor(){
        BookShopService shopService=BookDAOTest.getService();

        Book book=new Book("1984",new ArrayList<String>());
        book.setGenre(new HashSet<enumOfGenres>(Arrays.asList(enumOfGenres.ADVENTURE,enumOfGenres.HORROR)));
        Author marks=new Author("marks");
        marks.setAddres(new addres("USA"));
        book.setAuthors(new HashSet<Author>(Arrays.asList(marks)));
        shopService.createBook(book);

        shopService.deleteById(book.getId());

        Assert.assertEquals(shopService.GetAllBook().size(),0);
        Assert.assertEquals(shopService.GetAllAuthors().size(),1);
    }

    public static BookShopService getService(){
        configuration = new Configuration().configure();
        configuration.addAnnotatedClass(Book.class);
        configuration.addAnnotatedClass(Author.class);
        factorySession = configuration.buildSessionFactory();
        return new BookShopService(factorySession);
    }
}
