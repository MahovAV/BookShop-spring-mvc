package Model.Domain;

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


    @ManyToMany(fetch = FetchType.EAGER,cascade = CascadeType.MERGE)
    @LazyCollection(LazyCollectionOption.FALSE)
    @JoinTable(name = "Book_Author",
               joinColumns = {@JoinColumn(name = "book")},
               inverseJoinColumns ={@JoinColumn(name ="author")})
// cannot be null
    //if there is no authors just set wit size 0
    public Set<Author> Authors=new HashSet<Author>();

    @Enumerated(EnumType.STRING)
    @ElementCollection(fetch = FetchType.EAGER)
    @LazyCollection(LazyCollectionOption.FALSE)
    private Set<enumOfGenres>  genre;


    public Book(String name,Collection<String> NameOfCustommers){
        this.name=name;
        this.NameOfCustommers=NameOfCustommers;
    }

    public Book (String name) {
        this.name=name;
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
        return Authors;
    }

    public void setAuthors(Set<Author> authors) {
        this.Authors = authors;
    }

    @Override
    public boolean equals(Object obj) {
        //type cast to goal
        if(this==obj)return true;
        if(obj==null|| this.getClass()!=obj.getClass())return false;
        //the same class and not null =>could cast
        Book book = (Book) obj;

        //in our case 2 books are equal when id
        //and collection of authores are equals

        if(this.Another_Id==book.getId() &&checkCollection(this.Authors,book.getAuthors()))
            return true;
        else return false;
    }
    private boolean checkCollection(Set<Author> f,Set<Author> s){
        //could not be null
        return f.equals(s);
    }

    public Book(){}

    public Set<enumOfGenres> getGenre() {
        return genre;
    }

    public void setGenre(Set<enumOfGenres> genre) {
        this.genre = genre;
    }

    public void removeAuthor(Author author){
        //delete POJO which was associated with database
        //after this method should update book
        Authors.remove(author);
    }
}
