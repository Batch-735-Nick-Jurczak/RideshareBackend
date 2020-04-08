package com.revature.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.revature.auth.ApplicationUser;

public interface ApplicationUserRepository extends JpaRepository<ApplicationUser, String> {

}
