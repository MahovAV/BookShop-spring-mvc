package ru.cource.model.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ru.cource.model.dao.HibernateUserDao;
import ru.cource.model.domain.User;

/**
 * Implementation of {@link UserServiceInterface}
 * 
 * 
 * @author AlexanderM-O
 *
 */

@Transactional
@Service
public class UserServiceHibernateImpl implements UserServiceInterface {

	@Autowired
	HibernateUserDao userDao;

	@Autowired
	PasswordEncoder encoder;

	@Override
	public User findUserByName(String name) {
		return userDao.getByName(name);
	}

	@Override
	public User findUserByEmail(String email) {
		return userDao.getByEmail(email);
	}

	@Override
	public void registerUser(User user) {
		// encode password and save to database
		user.setPassword(encoder.encode(user.getPassword()));
		userDao.create(user);
	}

	@Override
	public void updateUser(User user) {
		userDao.update(user);
	}

	@Override
	public void deleteUserByName(String name) {
		userDao.delete(userDao.getByName(name).getId());
	}

}
