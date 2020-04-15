package com.revature.services.impl;

import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import com.google.maps.errors.ApiException;
import com.revature.beans.Batch;
import com.revature.beans.User;
import com.revature.repositories.CarRepository;
import com.revature.repositories.UserRepository;
import com.revature.services.CarService;
import com.revature.services.PageService;
import com.revature.services.UserService;

@RunWith(SpringRunner.class)
public class PageServiceTest {

	@InjectMocks
	private PageServiceImpl ps;

	@Mock
	private UserService us;
	
	@MockBean
	private CarService cs;
	
	@MockBean
	private CarRepository cr;
	
	@Mock
	private UserRepository ur;
	
	@Mock DistanceServiceImpl dsi;

	@Test
	public void test() {
		List<User> users = new ArrayList<>();
		users.add(new User(1,"gpichmann0", new Batch(), "Grady", "Pichmann", "gpichmann0@artisteer.com", "212-374-3466", "5 Carpenter Plaza", "New York City", "10275", "NY", "30401 Esker Point", "Des Moines", "50347", "IA"));
		users.add(new User(2,"gpichmann1", new Batch(), "Grady", "Pichmann", "gpichmann1@artisteer.com", "212-374-3466", "6 Carpenter Plaza", "New York City", "10275", "NY", "30401 Esker Point", "Des Moines", "50347", "IA"));
		users.add(new User(3,"gpichmann2", new Batch(), "Grady", "Pichmann", "gpichmann2@artisteer.com", "212-374-3466", "7 Carpenter Plaza", "New York City", "10275", "NY", "30401 Esker Point", "Des Moines", "50347", "IA"));
		users.add(new User(4,"gpichmann3", new Batch(), "Grady", "Pichmann", "gpichmann3@artisteer.com", "212-374-3466", "8 Carpenter Plaza", "New York City", "10275", "NY", "30401 Esker Point", "Des Moines", "50347", "IA"));
		users.add(new User(5,"gpichmann4", new Batch(), "Grady", "Pichmann", "gpichmann4@artisteer.com", "212-374-3466", "9 Carpenter Plaza", "New York City", "10275", "NY", "30401 Esker Point", "Des Moines", "50347", "IA"));
		Mockito.when(us.getUserById(1)).thenReturn(users.get(0));
		Mockito.when(us.getActiveDriversWithOpenSeats(1)).thenReturn(users);
		Mockito.when(us.getGoogleHomeAddress(users.get(0)))
		.thenReturn(users.get(0).gethAddress() + ", " 
		+ users.get(0).gethCity() + ", " 
		+ users.get(0).gethState());
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
