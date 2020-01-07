package ru.cource.model.domain;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

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

	private String name;

	@Embedded
	private Addres addres;

	private String information;

	private String avatarFileName;

	public Addres getAddres() {
		return addres;
	}

	public void setAddres(Addres addres) {
		this.addres = addres;
	}

	public Author() {
	}

	public Author(String name) {
		this.name = name;
	}

	@ManyToMany(mappedBy = "Authors", fetch = FetchType.EAGER, cascade = CascadeType.MERGE)
	Set<Book> books = new HashSet<Book>();

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

	public Set<Book> getBooks() {
		return books;
	}

	public void setBooks(Set<Book> books) {
		this.books = books;
	}

	public void deleteBook(Book book) {
		books.remove(book);
	}

	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return this.name;
	}

	public String getInformation() {
		return information;
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
	
	
}
