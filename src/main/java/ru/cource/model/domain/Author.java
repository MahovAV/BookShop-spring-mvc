package ru.cource.model.domain;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * POJO domain object representing an Author.
 * 
 * @author AlexanderM-O
 *
 */
@Entity
@Table(name = "Author")
public class Author {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int Id;

	@NotEmpty(message = "Name should contain at least 1 character")
	private String name;

	@Embedded
	private Addres addres;

	private String information;

	private String avatarFileName;

	@Transient
	private String bookError;

	public Author() {
	}

	public Author(String name) {
		this.name = name;
	}

	@ManyToMany(mappedBy = "Authors", fetch = FetchType.EAGER, cascade = CascadeType.MERGE)
	Set<Book> books = new HashSet<Book>();

	public String getWrittenBooks() {
		String result = new String();
		for (Book s : books) {
			result += s.getName() + ",";
		}
		return (result.length() > 0) ? result.substring(0, result.length() - 1) : "";
	}

	public void setWrittenBooks(String books) {
		bookError = DomainRepresentationUtils.validCommaSeparated(books);

		if (bookError == null) {
			this.books = convertStringToBooks(books);
		} else {
			// object wont be saved in database
			this.books = new HashSet<Book>();
		}
	}

	private static Set<Book> convertStringToBooks(String books) {
		if (books != null) {
			return Arrays.asList(books.split(",")).stream().filter(book -> !book.isEmpty()).map(book -> new Book(book))
					.collect(Collectors.toSet());
		} else
			return new HashSet<Book>();
	}

	public int getId() {
		return Id;
	}

	public void setId(int id) {
		this.Id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Addres getAddres() {
		return addres == null ? new Addres("") : addres;
	}

	public void setAddres(Addres addres) {
		this.addres = addres;
	}

	public Set<Book> getBooks() {
		return books;
	}

	public void setBooks(Set<Book> books) {
		this.books = books;
	}

	public void deleteBook(Book book) {
		books.remove(book);
	}

	public String getInformation() {
		return information == null ? "" : information;
	}

	public void setInformation(String information) {
		this.information = information;
	}

	public String getAvatarFileName() {
		return avatarFileName;
	}

	public void setAvatarFileName(String avatarFileName) {
		this.avatarFileName = avatarFileName;
	}

	public String getBookError() {
		return bookError;
	}

	public void setBookError(String bookError) {
		this.bookError = bookError;
	}

	@Override
	public String toString() {
		return this.name;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null || this.getClass() != obj.getClass())
			return false;
		Author author = (Author) obj;
		return this.name.equals(author.getName());
	}

	@Override
	public int hashCode() {
		return this.name.hashCode();
	}
}
