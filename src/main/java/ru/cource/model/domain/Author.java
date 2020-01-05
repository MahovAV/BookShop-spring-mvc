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
		// type cast to goal
		if (this == obj)
			return true;
		if (obj == null || this.getClass() != obj.getClass())
			return false;
		// the same class and not null =>could cast
		Author author = (Author) obj;

		// in our case 2 authores are equal when names are equals

		return this.name.equals(author.getName());
	}

	@Override
	public int hashCode() {
		// TODO: WRITE HASH CODE LOGIC HERE
		return 1;
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
}
