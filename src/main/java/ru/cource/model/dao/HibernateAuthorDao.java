package ru.cource.model.dao;

import java.util.List;
import java.util.Set;

import javax.transaction.Transactional;

import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;

import ru.cource.model.domain.Author;
import ru.cource.model.domain.Book;
import ru.cource.model.dao.HibernateGenericAbstractDao;

/**
 * DAO class for {@link Author}
 * 
 * @author AlexanderM-O
 *
 */

@Repository
public class HibernateAuthorDao extends HibernateGenericAbstractDao<Author> {

	HibernateAuthorDao() {
		super(Author.class);
	}

	@Override
	public void update(Author entity) {
		session = factory.getCurrentSession();
		Author oldAuthor = session.get(Author.class, entity.getId());
		Set<Book> oldBooks = oldAuthor.getBooks();
		Set<Book> NewBooks = entity.getBooks();
		for (Book oldBook : oldBooks) {
			if (NewBooks == null || !NewBooks.contains(oldBook)) {
				// should change author and merge him
				oldBook.deleteAuthor(entity);
			}
		}
		session.merge(entity);
	}

	@Override
	public void delete(int id) {
		session = factory.getCurrentSession();
		// cannot execute it in open session!!!
		Book deletingBook = session.get(Book.class, id);
		Set<Author> authors = deletingBook.getAuthors();
		for (Author a : authors) {
			a.deleteBook(deletingBook);
			// NO NEED MERGE HERE AS WE WILL COMMIT
			// AUTOMATICLY WILL MANAGE IT VIA DIRTY CHECKING
		}

		// dont have relation ship with not existed book
		// could delete book
		session.delete(deletingBook);
	}

	@Override
	public void create(Author entity) {
		session = factory.getCurrentSession();
		session.save(entity);
	}

	@Override
	public List<Author> getAll() {
		List<Author> Data;
		session = factory.getCurrentSession();
		Query query = session.createQuery("FROM Author");
		Data = query.list();
		return Data;
	}

	public Author getByName(String name) {
		Author Data;
		session = factory.getCurrentSession();
		Query query = session.createQuery("FROM Author A WHERE name = :paramName");
		query.setParameter("paramName", name);
		Data = (Author) query.uniqueResult();
		return Data;
	}
}
