package ru.cource.model.domain;

import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;

import java.util.*;
import java.util.stream.Collectors;

/**
 * POJO domain object representing a Book.
 * 
 * @author AlexanderM-O
 *
 */
@Entity
@Table(name = "Book")
public class Book {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int Id;

	@NotEmpty(message = "Name should contain at least 1 character")
	private String name;

	@ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.MERGE)
	@LazyCollection(LazyCollectionOption.FALSE)
	@JoinTable(name = "Book_Author", joinColumns = { @JoinColumn(name = "book") }, inverseJoinColumns = {
			@JoinColumn(name = "author") })
	public Set<Author> Authors = new HashSet<Author>();

	@Enumerated(EnumType.STRING)
	@ElementCollection(fetch = FetchType.EAGER)
	@LazyCollection(LazyCollectionOption.FALSE)
	private Set<Genre> genre = new HashSet<Genre>();

	private String information;

	private String bookCoverFileName;

	@Transient
	private String authorError;

	public Book() {
	}

	public Book(String name) {
		this.name = name;
	}
	/**
	 * Used for getting data from form and fill book field 
	 * 
	 * @param Authors
	 */
	public void setInputedAuthor(String Authors) {
		authorError = validInputedAuthor(Authors);

		if (authorError == null) {
			this.Authors = convertStringToAuthors(Authors);
		} else {
			this.Authors = new HashSet<Author>();
		}
	}

	/**
	 * Used for getting data from form and fill book field 
	 * 
	 * @param checkboxValues
	 */
	public void setEnumOfGenre(Set<String> checkboxValues) {
		this.genre = convertStringsToGenres(checkboxValues);
	}

	/**
	 * Used to put data to model
	 * @return string representation of genres
	 */
	public Set<String> getStringsFromCheckedGenres() {
		return genre.stream().map(x -> x.toString()).collect(Collectors.toSet());
	}

	/**
	 * Used to put data to model
	 * @return string representation of authors
	 */
	public String getStringFromAuthors() {
		String result = new String();
		for (Author s : Authors) {
			result += s.getName() + ",";
		}
		return (result.length() > 0) ? result.substring(0, result.length() - 1) : "";
	}
	
	private static Set<Author> convertStringToAuthors(String authors) {
		if (authors != null) {
			authors.replaceAll(" ", "");
			return Arrays.asList(authors.split(",")).stream().filter(author -> !author.isEmpty())
					.map(author -> new Author(author)).collect(Collectors.toSet());
		} else
			return new HashSet<Author>();
	}

	private static Set<Genre> convertStringsToGenres(Set<String> checkboxValues) {
		if (checkboxValues != null) {
			return checkboxValues.stream().map((str) -> Genre.valueOf(str)).collect(Collectors.toSet());
		} else
			return new HashSet<Genre>();
	}

	/**
	 * Used to get error message for following validation.If there is no error
	 * return null
	 * 
	 * @param authors
	 * @return error message
	 */
	private String validInputedAuthor(String authors) {
		for (int i = 0; i < authors.length(); ++i) {
			if (authors.charAt(i) == ',') {
				if (i == authors.length() - 1 || authors.charAt(i + 1) == ',') {
					return "invalid string";
				}
			}
		}
		List<String> listWithDuplicates = Arrays.asList(authors.split(",")).stream().collect(Collectors.toList());
		List<String> listWithoutDuplicates = listWithDuplicates.stream().distinct().collect(Collectors.toList());
		if (listWithDuplicates.size() != listWithoutDuplicates.size()) {
			return "there is dublicates in string";
		}

		return null;
	}
	
	public void setName(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public void setGenre(Set<Genre> genre) {
		this.genre = genre;
	}

	public Set<Genre> getGenre() {
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

	public String getInformation() {
		return information;
	}

	public void setInformation(String information) {
		this.information = information;
	}

	public String getBookCoverFileName() {
		return bookCoverFileName;
	}

	public void setBookCoverFileName(String bookCoverFileName) {
		this.bookCoverFileName = bookCoverFileName;
	}

	@Override
	public String toString() {
		return name;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null || this.getClass() != obj.getClass())
			return false;
		Book book = (Book) obj;
		boolean isNamesAreEquals = this.name.equals(book.getName());
		boolean isAuthorsAreEquals = this.Authors.containsAll(book.Authors);
		return isNamesAreEquals && isAuthorsAreEquals;
	}

	@Override
	public int hashCode() {
		return this.name.hashCode();
	}
}
