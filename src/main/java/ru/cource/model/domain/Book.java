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
    private Collection<String> NameOfCustommers=new HashSet<String>();


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
    private Set<EnumOfGenres>  genre=new HashSet<EnumOfGenres>();
    
    @Transient
    private String authorError;

    public Book(){}
    
    public Book(String name,Collection<String> NameOfCustommers){
        this.name=name;
        this.NameOfCustommers=NameOfCustommers;
    }

    
    public Book (String name) {
        this.name=name;
    }
    //old setters and getters
    
	public void setName(String name) {
        this.name = name;
    }
    
    public String getName() {return name;}
    

    public void setGenre(Set<EnumOfGenres> genre) {this.genre = genre;}

    public Set<EnumOfGenres> getGenre() {
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
    
	public String getAuthorError() {
		return authorError;
	}

	public void setAuthorError(String authorError) {
		this.authorError = authorError;
	}

    public Collection<String> getNameOfCustommers() {
        return NameOfCustommers;
    }

    public void setNameOfCustommers(Collection<String> nameOfCustommers) {
        NameOfCustommers = nameOfCustommers;
    }

    //Methods for getting and setting information for view
    
    public void setInputedAuthor(String Authors) {
    	//validate string and if there some errors should return null
    	authorError =validInputedAuthor(Authors);
    	//if string is valid we set our authors
    	if(authorError==null) {
        this.Authors = getAuthorsFromString(Authors);
    	}else {
    		this.Authors= new HashSet<Author>();
    	}
    }
    

    public void setEnumOfGenre(Set<String> checkboxValues) {
        this.genre = getGenresFromCheckBox(checkboxValues);
    }
    
    public Set<String> getStringsFromCheckedGenres(){
        Set<String> Genres=new HashSet<String>();
        for(EnumOfGenres e:genre){
            Genres.add(e.toString());
        }
        return Genres;
    }
    
    public String getStringFromAuthors() {
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
    private static Set<EnumOfGenres> getGenresFromCheckBox (Set<String> checkboxValues) {
        //Set<String> checkboxValues  не явно создает коллекцию
        //если нету значений то null а не пустая коллекция!!!
    	
    	if(checkboxValues!=null) {
    	return checkboxValues.stream()
    						 .map((str)->EnumOfGenres.valueOf(str))
    					     .collect(Collectors.toSet());
    	}else return new HashSet<EnumOfGenres>();
    }
    
    
    private String validInputedAuthor(String authors) {
    	//must have: no dublicates,valid string
    	for(int i=0;i<authors.length();++i) {
    		if(authors.charAt(i)==',') {
    			//we must have next character
    			if(i==authors.length()-1||authors.charAt(i+1)==',') {
    				return "invalid string";
    			}
    		}
    	}
        List<String> listWithDuplicates = Arrays.asList(authors.split(",")).stream()
        																   .collect(Collectors.toList());
        List<String> listWithoutDuplicates = listWithDuplicates.stream()
        													   .distinct()
        													   .collect(Collectors.toList());
        if(listWithDuplicates.size()!=listWithoutDuplicates.size()) {
        	return "there is dublicates in string";
        }
        //there is no problem with string
		return null;
    }
    public void deleteAuthor(Author author){
    	Authors.remove(author);
    }

	@Override
	public String toString() {
		return name;
	}
	
	 @Override
	    public boolean equals(Object obj) {
	        //type cast to goal
	        if(this==obj)return true;
	        if(obj==null|| this.getClass()!=obj.getClass())return false;
	        //the same class and not null =>could cast
	        Book book = (Book) obj;

	        //in our case 2 books are equal when id
	        //and collection of authors are equals
	        
	        boolean isNamesAreEquals=this.name.equals(book.getName());
	        boolean isAuthorsAreEquals=this.Authors.containsAll(book.Authors);
	        return isNamesAreEquals&&isAuthorsAreEquals;
	    }

		@Override
		public int hashCode() {
			return 1;
		}
	    
}
