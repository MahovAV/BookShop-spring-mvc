package ru.cource.model.domain;

import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import com.sun.istack.NotNull;

import ru.cource.controller.ControllerUtils;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;

import java.util.*;
import java.util.stream.Collectors;


/**
 * Created by user on 05.11.2019.
 */
@Entity
@Table(name = "Book")
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int Id;

    @NotEmpty(message="Name should contain at least 1 character")
    private String name;

    @ElementCollection(fetch = FetchType.EAGER)
    @LazyCollection(LazyCollectionOption.FALSE)
    @CollectionTable(name = "book_NameOfCustommers", joinColumns = @JoinColumn(name = "book_id"))
    @Column(name = "name")
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

    public Book(){}
    
    public Book(String name,Collection<String> NameOfCustommers){
        this.name=name;
        this.NameOfCustommers=NameOfCustommers;
    }
    //old setters and getters
    
    public Book (String name) {
        this.name=name;
    }

	public void setName(String name) {
        this.name = name;
    }
    
    public String getName() {return name;}
    

    public void setGenre(Set<enumOfGenres> genre) {this.genre = genre;}

    public Set<enumOfGenres> getGenre() {
        return genre;
    }

        
    public Set<Author> getAuthors() {
        return Authors;
    }

    
    public void setAuthors(Set<Author> authors) {
        this.Authors = authors;
    }

    public int getId() {
        return Id;
    }

    public void setId(int id) {
        this.Id = id;
    }

    public Collection<String> getNameOfCustommers() {
        return NameOfCustommers;
    }

    public void setNameOfCustommers(Collection<String> nameOfCustommers) {
        NameOfCustommers = nameOfCustommers;
    }

    //Methods for getting and setting information for view
    public void setInputedAuthor(String Authors) {
    	System.out.println("setAuthors");
        this.Authors = getAuthorsFromString(Authors);
    }
    

    public void setEnumOfGenre(Set<String> checkboxValues) {
    	System.out.println("setGenre");
        this.genre = getGenresFromCheckBox(checkboxValues);
    }
    
    public Set<String> getStringsFromCheckedGenres(){
    	System.out.println("getStringsFromCheckedGenres");
        Set<String> Genres=new HashSet<String>();
        for(enumOfGenres e:genre){
            Genres.add(e.toString());
        }
        return Genres;
    }
    
    public String getStringFromAuthors() {
    	System.out.println("getStringFromAuthors");
        String result=new String();
        for(Author s:Authors){
            result+=s.getName()+",";
        }
        return (result.length()>0)?result.substring(0,result.length()-1):"";
    }
    
    //other methods
    public void removeAuthor(Author author){
        //delete POJO which was associated with database
        Authors.remove(author);
    }
    private static Set<Author> getAuthorsFromString(String authors){
    	if(authors!=null) {
    	authors.replaceAll(" ","");
    	return Arrays.asList(authors.split(",")).stream()
    											.filter(author->!author.isEmpty())
    											.map(author->new Author(author))
    											.collect(Collectors.toSet());
    	}else return new HashSet<Author>();
    }
    private static Set<enumOfGenres> getGenresFromCheckBox (Set<String> checkboxValues) {
        //Set<String> checkboxValues  не явно создает коллекцию
        //если нету значений то null а не пустая коллекция!!!
    	
    	if(checkboxValues!=null) {
    	return checkboxValues.stream()
    						 .map((str)->enumOfGenres.valueOf(str))
    					     .collect(Collectors.toSet());
    	}else return new HashSet<enumOfGenres>();
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

        if(this.Id==book.getId() &&checkCollection(this.Authors,book.getAuthors()))
            return true;
        else return false;
    }
    private boolean checkCollection(Set<Author> f,Set<Author> s){
        //could not be null
        return f.equals(s);
    }
}
