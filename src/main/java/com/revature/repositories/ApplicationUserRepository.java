package com.revature.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.revature.beans.ApplicationUser;
import com.revature.beans.User;

/**
 * ApplicationUser Repository using JpaRepository with some custom queries.
 * 
 * @author David Anderson
 *
 */


@Repository
public interface ApplicationUserRepository extends JpaRepository<ApplicationUser, String> {
	
	/**
	 * Custom query that uses the Query annotation to select a user by username.
	 * 
	 * @param username represents the user's username.
	 * @return Returns an Optional with a user inside.
	 */
	@Query("select u from ApplicationUser u where u.username = ?1")
	public Optional<ApplicationUser> getUserByUsername(String username);
	/**
	 * Custom query that uses the Query annotation to select a user by username.
	 * 
	 * @return Returns a list of ApplicationUsers
	 */
	
	@Query("select u from User u where u.userName = ?1")
	public List<ApplicationUser> getAppUsersByUsername();

}
