package Domain;

import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import javax.persistence.*;
import java.util.*;


/**
 * Created by user on 05.11.2019.
 */
@Entity
@Table(name = "Book")
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int Another_Id;

    private String name;

    @ElementCollection(fetch = FetchType.EAGER)
    @LazyCollection(LazyCollectionOption.FALSE)
    private Collection<String> NameOfCustommers;


    @ManyToMany(fetch = FetchType.EAGER)
    @LazyCollection(LazyCollectionOption.FALSE)
    @JoinTable(name = "Book_Author",
               joinColumns = {@JoinColumn(name = "book")},
               inverseJoinColumns ={@JoinColumn(name ="author")})

    public Set<Author> mappedByAuthors=new HashSet<Author>();

    public Book(String name,Collection<String> NameOfCustommers){
        this.name=name;
        this.NameOfCustommers=NameOfCustommers;
    }

    public int getId() {
        return Another_Id;
    }

    public void setId(int id) {
        this.Another_Id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Collection<String> getNameOfCustommers() {
        return NameOfCustommers;
    }

    public void setNameOfCustommers(Collection<String> nameOfCustommers) {
        NameOfCustommers = nameOfCustommers;
    }

    public Set<Author> getAuthors() {
        return mappedByAuthors;
    }

    public void setAuthors(Set<Author> authors) {
        this.mappedByAuthors = authors;
    }

    @Override
    public boolean equals(Object obj) {
        //type cast to goal
        if(this==obj)return true;
        if(obj==null|| this.getClass()!=obj.getClass())return false;
        //the same class and not null =>could cast
        Book book = (Book) obj;

        //in our case 2 books are equal when id
        //and collection of authoes are equals

        if(this.Another_Id==book.getId() &&checkCollection(this.mappedByAuthors,book.getAuthors()))
            return true;
        else return false;
    }
    public boolean checkCollection(Set<Author> f,Set<Author> s){
        return f.equals(s);
    }

    public Book(){}
}
