package ru.cource.model.domain;

import ru.cource.model.domain.Author;
import ru.cource.model.domain.Book;
import ru.cource.model.domain.Addres;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Test HashCode-equals contract
 * 
 * @author AlexanderM-O
 *
 */
public class BookEqualsTest {
	private static Author author1;
	private static Author author2;
	private static Book book;
	private static Book copyOfBook1;
	private static Book copyOfBook2;

	@BeforeEach
	public void initAuthors() {
		author1 = new Author("Marks");
		author1.setAddres(new Addres("USA"));

		author2 = new Author("Oryell");
		author2.setAddres(new Addres("Russia"));

		book = new Book("1984");

		book.getAuthors().add(author1);
		book.getAuthors().add(author2);

		copyOfBook1 = new Book("1984");

		copyOfBook1.getAuthors().add(author1);
		copyOfBook1.getAuthors().add(author2);

		copyOfBook2 = new Book("1984");

		copyOfBook2.getAuthors().add(author1);
		copyOfBook2.getAuthors().add(author2);
	}

	@Test
	public void reflexive() {
		assertTrue(book.equals(book));
		assertEquals(book.hashCode(), book.hashCode());
	}

	@Test
	public void symmetric() {
		assertTrue(book.equals(copyOfBook1));
		assertTrue(copyOfBook1.equals(book));
		assertEquals(book.hashCode(), copyOfBook1.hashCode());
	}

	@Test
	public void transitive() {
		assertTrue(book.equals(copyOfBook1));
		assertTrue(copyOfBook1.equals(copyOfBook2));
		assertTrue(book.equals(copyOfBook2));
		assertEquals(book.hashCode(), copyOfBook1.hashCode(), copyOfBook2.hashCode());
	}

	@Test
	public void notNull() {
		assertFalse(book.equals(null));
	}
}
