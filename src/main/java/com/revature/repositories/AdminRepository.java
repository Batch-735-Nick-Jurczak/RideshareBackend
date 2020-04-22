package com.revature.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.revature.beans.Admin;
import com.revature.beans.User;

/**
 * AdminRepository which extends the JpaRepository.
 * This repository handles our various queries.
 * 
 * @author Adonis Cabreja
 *
 */

@Repository
public interface AdminRepository extends JpaRepository<Admin, String> {

	
	@Query("select a from Admin a where a.userName = ?1")
	public Optional<Admin> getAdminByUsername(String username);
}
