package Model.Domain;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by user on 05.11.2019.
 */

@Entity
@Table(name = "Author")
public class Author {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "Author_Name")
    private String name;

    @Embedded
    @Column(name = "Author_Addres")
    private addres addres;

    public Model.Domain.addres getAddres() {
        return addres;
    }

    public void setAddres(Model.Domain.addres addres) {
        this.addres = addres;
    }

    public Author(){}

    public Author(String name) {
        this.name = name;
    }

    @ManyToMany(mappedBy = "Authors",fetch = FetchType.EAGER,cascade = CascadeType.MERGE)
    Set<Book> books=new HashSet<Book>();

    @Override
    public boolean equals(Object obj) {
        //type cast to goal
        if(this==obj)return true;
        if(obj==null|| this.getClass()!=obj.getClass())return false;
        //the same class and not null =>could cast
        Author author = (Author) obj;

        //in our case 2 authores are equal when names are equals

        if(this.name==author.name)
            return true;
        else return false;
    }






    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    public Set<Book> getBooks() {
        return books;
    }

    public void setBooks(Set<Book> books) {
        this.books = books;
    }

    public void deleteBook(Book book){
        books.remove(book);
    }
}
