package com.revature.services;

import java.util.List;
import java.util.Optional;

import com.revature.beans.User;

public interface UserService {
	
	public List<User> getUsers();
	public User getUserById(int id);
	public Optional<User> getUserByUsername(String username);
	public List<User> getUsersByUsername(String username);
	public List<User> getUserByRole(boolean isDriver);
	public List<User> getUserByRoleAndLocation(boolean isDriver, String location);
	public User addUser(User user);
	public User updateUser(User user);
	public String deleteUserById(int id);
	public List<User> getActiveDrivers();
	public List<User> getActiveDriversWithOpenSeats(int batchNum);
}
