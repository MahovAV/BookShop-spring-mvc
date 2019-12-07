package ru.cource.model.dao;

import ru.cource.config.SpringConfig;
import ru.cource.model.domain.Author;
import ru.cource.model.domain.Book;
import ru.cource.model.domain.Addres;
import ru.cource.model.domain.enumOfGenres;
import ru.cource.model.service.BookShopService;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.NativeQuery;
import org.junit.*;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Import;
import java.util.*;

/**
 * Created by user on 06.11.2019.
 */
@ComponentScan({"ru.cource.config"})
public class BookDAOTest {
    private static Logger logger= LoggerFactory.getLogger(BookDAOTest.class);
    private static ApplicationContext context;
    private static BookShopService bookShopService;

    @BeforeClass
    public static void init(){
        System.out.println("calling init method setting context and creating bean manually");
        context=new AnnotationConfigApplicationContext(SpringConfig.class);
        bookShopService=(BookShopService)context.getBean("Service");
    }

    @AfterClass
    public static void cleanBaseAfter(){
        cleanBase();
    }

    @Before
    public  void cleanBaseBeforeEachTest(){
        cleanBase();
    }

    public static void cleanBase(){
        SessionFactory Factorysession=(SessionFactory)context.getBean("getFactory");
        Session session=Factorysession.openSession();
        session.getTransaction().begin();
        String [] quries={"SET FOREIGN_KEY_CHECKS = 0;","TRUNCATE TABLE author;",
                "TRUNCATE TABLE book;",
                "TRUNCATE TABLE book_author;",
                "TRUNCATE TABLE book_genre;",
                "TRUNCATE TABLE book_nameofcustommers;",
                "SET FOREIGN_KEY_CHECKS = 1;"};
        for(int i=0;i<quries.length;i++){
            NativeQuery query=session.createSQLQuery(quries[i]);
            query.executeUpdate();
        }
    }
    @Test
    public void WithouAuthor(){

        Book book=new Book("1984",new ArrayList<String>());
        book.getNameOfCustommers().add("JESSY");
        book.getNameOfCustommers().add("WOLTER");

        logger.info("putting book without author");
        bookShopService.createBook(book);
        //HAVE PERSISTED BOOK  need check this
        Book bookFromShop=bookShopService.getBookById(book.getId());

        Assert.assertTrue(bookFromShop.equals(book));
    }

    @Test
    public void OneNewAuthor(){
        Book book=new Book("1984",new ArrayList<String>());
        book.getNameOfCustommers().add("JESSY");
        book.getNameOfCustommers().add("WOLTER");

        Author marks=new Author("marks");
        marks.setAddres(new Addres("USA"));

        Author oryell=new Author("Pushkin");
        marks.setAddres(new Addres("Russia"));

        book.getAuthors().add(marks);
        book.getAuthors().add(oryell);
        bookShopService.createBook(book);

        Book bookFromShop=bookShopService.getBookById(book.getId());

        Set<Author> AuthorsFromBookFromShop=bookFromShop.getAuthors();

        //each author and book are related
        for(Author a:AuthorsFromBookFromShop){
            Assert.assertTrue(a.getBooks().contains(bookFromShop));
        }
    }

    @Test
    public void AlreadyExistingAuthorAndTwoBooks(){
        Book book=new Book("1984",new ArrayList<String>());
        book.getNameOfCustommers().add("JESSY");
        book.getNameOfCustommers().add("WOLTER");

        Book book2=new Book("Narny",new ArrayList<String>());
        book2.getNameOfCustommers().add("Jack");

        Author marks=new Author("marks");
        marks.setAddres(new Addres("USA"));

        Author oryell=new Author("oryell");
        oryell.setAddres(new Addres("Russia"));

        Author CopyOfMarks1=marks;

        Author CreatedCopyOfMarks=new Author("marks");
        CreatedCopyOfMarks.setAddres(new Addres("Created"));

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

        // TODO: 10.11.2019 add exception support

        Assert.assertEquals(2,books.size());

        Assert.assertEquals(2,authors.size());

        for(Book b:books){
            for (Author a:b.getAuthors()){
                Assert.assertTrue(a.getBooks().contains(b));
            }
        }
    }
    @Test
    public void UpdateTest(){
        Book book=new Book("1984",new ArrayList<String>());
        book.setGenre(new HashSet<enumOfGenres>(Arrays.asList(enumOfGenres.ADVENTURE,enumOfGenres.HORROR)));
        Author marks=new Author("marks");
        marks.setAddres(new Addres("USA"));
        book.setAuthors(new HashSet<Author>(Arrays.asList(marks)));
        //was
        bookShopService.createBook(book);
        //delete author(bot not from database!!!),delete 1 GENRE
        //add new genre

        book.removeAuthor(marks);
        book.setGenre(new HashSet<enumOfGenres>(Arrays.asList(enumOfGenres.ROMANS)));


        //Should have:1.marks in data base and have no link to book
        //2.book from data base and book POJO are equivalent
        bookShopService.updateBook(book);

        List<Author> authors=bookShopService.GetAllAuthors();
        Assert.assertEquals(1,authors.size());
        Assert.assertEquals(authors.get(0).getBooks().size(),0);
        //should check book

        Assert.assertTrue(book.equals(bookShopService.getBookById(book.getId())));


        //there is no changes should have the same result

    }

    @Test
    public void DeleteTestOneAuthor(){
        Book book=new Book("1984",new ArrayList<String>());
        book.setGenre(new HashSet<enumOfGenres>(Arrays.asList(enumOfGenres.ADVENTURE,enumOfGenres.HORROR)));
        Author marks=new Author("marks");
        marks.setAddres(new Addres("USA"));
        book.setAuthors(new HashSet<Author>(Arrays.asList(marks)));
        bookShopService.createBook(book);

        bookShopService.deleteById(book.getId());

        Assert.assertEquals(bookShopService.GetAllBook().size(),0);
        Assert.assertEquals(bookShopService.GetAllAuthors().size(),1);
    }
}
