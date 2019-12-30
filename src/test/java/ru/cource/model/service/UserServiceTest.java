package ru.cource.model.service;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Arrays;
import java.util.HashSet;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.NativeQuery;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.support.AnnotationConfigContextLoader;

import ru.cource.aspects.LoggerAspect;
import ru.cource.config.DataBaseConfig;
import ru.cource.model.domain.Addres;
import ru.cource.model.domain.Author;
import ru.cource.model.domain.Book;
import ru.cource.model.domain.Role;
import ru.cource.model.domain.User;

@ExtendWith(SpringExtension.class) // add spring support
@ContextConfiguration(classes = {DataBaseConfig.class,UserServiceImpl.class,LoggerAspect.class}, 
	loader = AnnotationConfigContextLoader.class)
public class UserServiceTest {
	
	@Autowired
	private SessionFactory sessionFactory;
	
	@Autowired
	UserServiceImpl service;
	
	User user;
	
	User admin;
	
	public void cleanBase() {

		Session session = sessionFactory.openSession();
		session.beginTransaction();
		//truncate all tables
		String[] quries = { "SET FOREIGN_KEY_CHECKS = 0;", 
				"TRUNCATE TABLE author;", "TRUNCATE TABLE book;",
				"TRUNCATE TABLE book_author;", "TRUNCATE TABLE book_genre;",
				"TRUNCATE TABLE Usr;","TRUNCATE TABLE user_role;",
				"SET FOREIGN_KEY_CHECKS = 1;" };
		for (int i = 0; i < quries.length; i++) {
			NativeQuery query = session.createSQLQuery(quries[i]);
			query.executeUpdate();
		}
		session.getTransaction().commit();
		session.close();
	}
	
	@BeforeEach
	public void prepareFixture() {
		cleanBase();
		admin=new User();
		admin.setName("admin");
		admin.setRoles(new HashSet<Role>(Arrays.asList(Role.ADMIN,Role.USER)));
		admin.setEmail("admin@mail.ru");
		
		user=new User();
		user.setName("admin");
		user.setRoles(new HashSet<Role>(Arrays.asList(Role.USER)));
		user.setEmail("user@mail.ru");
	}
	
	@Test
	public void registerShouldAddUserToDataBase() {
		service.registerUser(user);
		assertTrue(user.equals(service.findUserByName(user.getName())));
	}
	
}
