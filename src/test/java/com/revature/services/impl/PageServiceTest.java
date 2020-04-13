package com.revature.services.impl;

import static org.junit.Assert.*;
import static org.mockito.Mockito.when;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit4.SpringRunner;

import com.google.maps.errors.ApiException;
import com.revature.beans.Batch;
import com.revature.beans.User;
import com.revature.repositories.CarRepository;
import com.revature.repositories.UserRepository;

@RunWith(SpringRunner.class)
public class PageServiceTest {

	@InjectMocks
	private PageServiceImpl ps;
	@InjectMocks
	private UserServiceImpl us;
	@InjectMocks
	private CarServiceImpl cs;
	
	@Mock
	private CarRepository cr;
	
	@Mock
	private UserRepository ur;

	@Test
	public void test() {
		List<User> users = new ArrayList<>();
		users.add(new User(1,"gpichmann0", new Batch(), "Grady", "Pichmann", "gpichmann0@artisteer.com", "212-374-3466", "5 Carpenter Plaza", "New York City", "10275", "NY", "30401 Esker Point", "Des Moines", "50347", "IA"));
		users.add(new User(2,"gpichmann0", new Batch(), "Grady", "Pichmann", "gpichmann0@artisteer.com", "212-374-3466", "5 Carpenter Plaza", "New York City", "10275", "NY", "30401 Esker Point", "Des Moines", "50347", "IA"));
		users.add(new User(3,"gpichmann0", new Batch(), "Grady", "Pichmann", "gpichmann0@artisteer.com", "212-374-3466", "5 Carpenter Plaza", "New York City", "10275", "NY", "30401 Esker Point", "Des Moines", "50347", "IA"));
		users.add(new User(4,"gpichmann0", new Batch(), "Grady", "Pichmann", "gpichmann0@artisteer.com", "212-374-3466", "5 Carpenter Plaza", "New York City", "10275", "NY", "30401 Esker Point", "Des Moines", "50347", "IA"));
		users.add(new User(5,"gpichmann0", new Batch(), "Grady", "Pichmann", "gpichmann0@artisteer.com", "212-374-3466", "5 Carpenter Plaza", "New York City", "10275", "NY", "30401 Esker Point", "Des Moines", "50347", "IA"));
		when(us.getUserById(1)).thenReturn(users.get(1));
		//when(us.getGoogleHomeAddress()).then
		List<User> results = new ArrayList<User>();
		try {
			results = ps.getPage(1, 1, 0, 1);
		} catch (ApiException e) {
			e.printStackTrace();
		} catch (InterruptedException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		assertTrue(results.size() == 5);
	}

}
