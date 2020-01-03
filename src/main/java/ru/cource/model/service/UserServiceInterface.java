package ru.cource.model.service;

import ru.cource.model.domain.User;

/**
 * Used to store {@link ru.cource.model.domain.User User}
 * 
 * 
 * @author AlexanderM-O
 *
 */
public interface UserServiceInterface {
	public User findUserByName(String name);

	public void registerUser(User user);

	public void updateUser(User user);

	public void deleteUserByName(String name);

	public User findUserByEmail(String email);
}
