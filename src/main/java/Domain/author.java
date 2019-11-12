package Domain;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
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



    @Enumerated(EnumType.STRING)
    @Column(name = "Author_Genre")
    private TypeEnum genre;

    @Embedded
    @Column(name = "Author_Addres")
    private addres addres;

    public Domain.addres getAddres() {
        return addres;
    }

    public void setAddres(Domain.addres addres) {
        this.addres = addres;
    }

    public Author(){}

    public Author(String name) {
        this.name = name;
    }

    @ManyToMany(mappedBy = "mappedByAuthors",fetch = FetchType.EAGER)
    Set<Book> books=new HashSet<Book>();

    @Override
    public boolean equals(Object obj) {
        //type cast to goal
        if(this==obj)return true;
        if(obj==null|| this.getClass()!=obj.getClass())return false;
        //the same class and not null =>could cast
        Author author = (Author) obj;

        //in our case 2 authoes are equal when names are equals

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

    public TypeEnum getGenre() {
        return genre;
    }

    public void setGenre(TypeEnum genre) {
        this.genre = genre;
    }

    public Set<Book> getBooks() {
        return books;
    }

    public void setBooks(Set<Book> books) {
        this.books = books;
    }

}
