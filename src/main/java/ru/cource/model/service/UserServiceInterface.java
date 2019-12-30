package ru.cource.model.service;

import ru.cource.model.domain.User;

public interface UserServiceInterface {
	public User findUserByName(String name);
	
	public void registerUser(User user);
	
	public void updateUser(User user);
	
	public void deleteUserByName(String name);
	
	public User findUserByEmail(String email);
}
