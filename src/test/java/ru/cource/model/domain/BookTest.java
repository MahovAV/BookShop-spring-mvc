package ru.cource.model.domain;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import ru.cource.model.domain.Author;
import ru.cource.model.domain.Book;
import ru.cource.model.domain.Addres;

import java.util.*;

/**
 * Created by user on 08.11.2019.
 */
public class BookTest {
    private static Author oruell;
    private static Author copyOfOruel1;
    private static Author copyOfOryel2;
    private static Author marks;


    private static Book thousand;
    private static Book copyOfthousand1;
    private static Book copyOfthousand2;
    private static Book Narny;
    @BeforeClass
    public static void initAuthors(){
        //instance 2 copy and one other object
        oruell=new Author("oruell");
        oruell.setId(1);
        oruell.setAddres(new Addres("USA"));
        oruell.setBooks(new HashSet<Book>(
                Arrays.asList(new Book("1984", Arrays.asList("jack")))));


         copyOfOruel1=oruell;


        copyOfOryel2=new Author("oruell");
        copyOfOryel2.setId(1);
        copyOfOryel2.setAddres(new Addres("USA"));
        copyOfOryel2.setBooks(new HashSet<Book>(
                Arrays.asList(new Book("1984", Arrays.asList("jack")))));


        marks=new Author("marks");
        marks.setId(2);
        marks.setAddres(new Addres("USA"));
        marks.setBooks(new HashSet<Book>(
                Arrays.asList(new Book("1984", Arrays.asList("jack")))));



        //INSTANSE COLLECTION
        Set<Author> authors=new HashSet<Author>();
        authors.add(oruell);
        List<Author> TheSameAuthors=new ArrayList<Author>();
        TheSameAuthors.add(copyOfOryel2);
        List<Author> other=new ArrayList<Author>();
        other.add(marks);


        //create 3 equalent book

        thousand=new Book("1984",Arrays.asList("john","BOB"));
        thousand.setAuthors(authors);
        copyOfthousand1=thousand;
        copyOfthousand2=new Book("1984",Arrays.asList("john","BOB"));
        copyOfthousand2.setAuthors(authors);
    }

    @Test
    public void TestEquals(){

        //TESTING AUTHORS NOT BOOKS!!!

        //reflection
        Assert.assertTrue(thousand.equals(thousand));
        //if x=y then y=x
        Assert.assertTrue(thousand.equals(copyOfthousand1));
        Assert.assertTrue(copyOfthousand1.equals(thousand));
        //if x=y and y=z then x=z
        Assert.assertTrue(thousand.equals(copyOfthousand1));
        Assert.assertTrue(copyOfthousand1.equals(copyOfthousand2));
        Assert.assertTrue(thousand.equals(copyOfthousand2));
        //if null then false
        Assert.assertFalse(thousand.equals(null));
    }

    @Test
    public void TestHashCode(){

    }
}
