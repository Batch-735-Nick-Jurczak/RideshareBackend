package com.revature.services.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import com.google.maps.errors.ApiException;
import com.revature.beans.Batch;
import com.revature.beans.Car;
import com.revature.beans.User;
import com.revature.repositories.CarRepository;
import com.revature.repositories.UserRepository;
import com.revature.services.CarService;
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
	
	List<User> users;
	
	List<String> destinations;
	
	User user1;
	User user2;
	User user3;
	User user4;
	User user5;
	
	@Before
	public void setUp() throws Exception {
		users = new ArrayList<User>();
		destinations = new ArrayList<String>();
		Batch batch = new Batch(1, "Tampa");
		User user1 = new User(1,"gpichmann0", batch, "Grady", "Pichmann", "gpichmann0@artisteer.com", "212-374-3466", "5 Carpenter Plaza", "New York City", "10275", "NY", "30401 Esker Point", "Des Moines", "50347", "IA");
		User user2 = new User(2,"gpichmann1", batch, "Mike", "Pichmann", "gpichmann1@artisteer.com", "212-374-3466", "", "Columbus", "43220", "OH", "30401 Esker Point", "Des Moines", "50347", "IA");
		User user3 = new User(3,"gpichmann2", batch, "Minh", "Pichmann", "gpichmann2@artisteer.com", "212-374-3466", "830 Pierstorff Parkway", "Omaha", "68197", "NE", "30401 Esker Point", "Des Moines", "50347", "IA");
		User user4 = new User(4,"gpichmann3", batch, "Calvin", "Pichmann", "gpichmann3@artisteer.com", "212-374-3466", "86978 Sage Junction", "Elmira", "14905", "NY", "30401 Esker Point", "Des Moines", "50347", "IA");
		User user5 = new User(5,"gpichmann4", batch, "Tyler", "Pichmann", "gpichmann4@artisteer.com", "212-374-3466", "496 High St", "Morgantown", "26505", "WV", "30401 Esker Point", "Des Moines", "50347", "IA");
		Car car1 = new Car(1, "blue", 4, 1, "tesla", "model 3", 2020, user1);
		Car car2 = new Car(1, "blue", 4, 3, "tesla", "model 3", 2020, user2);
		Car car3 = new Car(1, "blue", 4, 2, "tesla", "model 3", 2020, user3);
		Car car4 = new Car(1, "blue", 4, 6, "tesla", "model 3", 2020, user4);
		Car car5 = new Car(1, "blue", 6, 4, "tesla", "model 3", 2020, user5);
		user1.setCar(car1);
		user2.setCar(car2);
		user3.setCar(car3);
		user4.setCar(car4);
		user5.setCar(car5);
		users.add(user1);
		users.add(user2);
		users.add(user3);
		users.add(user4);
		users.add(user5);
		destinations.add(user1.getGoogleHomeAddress());
		destinations.add(user2.getGoogleHomeAddress());
		destinations.add(user3.getGoogleHomeAddress());
		destinations.add(user4.getGoogleHomeAddress());
		destinations.add(user5.getGoogleHomeAddress());
	}

	@Test
	public void testGetPageReturnsDrivers() {
		Mockito.when(us.getUserById(1)).thenReturn(users.get(0));
		Mockito.when(us.getActiveDriversWithOpenSeats(1)).thenReturn(users);
		Mockito.when(us.getGoogleHomeAddress(users.get(0)))
		.thenReturn(users.get(0).getGoogleHomeAddress());
		List<User> results = new ArrayList<User>();
		try {
			results = ps.getPage(users.get(0), 0, 1);
		} catch (ApiException e) {
			e.printStackTrace();
		} catch (InterruptedException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		assertTrue(results.size() == 5);
	}
	
	@Test
	public void testGetPageFiltersByDistance() {
		List<User> usersFilteredByDistance = new ArrayList<>();
		usersFilteredByDistance.add(users.get(0));
		usersFilteredByDistance.add(users.get(4));
		usersFilteredByDistance.add(users.get(1));
		usersFilteredByDistance.add(users.get(2));
		usersFilteredByDistance.add(users.get(3));
		Mockito.when(us.getUserById(1)).thenReturn(users.get(0));
		Mockito.when(us.getActiveDriversWithOpenSeats(1)).thenReturn(users);
		Mockito.when(us.getGoogleHomeAddress(users.get(0)))
		.thenReturn(users.get(0).getGoogleHomeAddress());
		Mockito.when(us.getGoogleHomeAddress(users.get(1)))
		.thenReturn(users.get(1).getGoogleHomeAddress());
		Mockito.when(us.getGoogleHomeAddress(users.get(2)))
		.thenReturn(users.get(2).getGoogleHomeAddress());
		Mockito.when(us.getGoogleHomeAddress(users.get(3)))
		.thenReturn(users.get(3).getGoogleHomeAddress());
		Mockito.when(us.getGoogleHomeAddress(users.get(4)))
		.thenReturn(users.get(4).getGoogleHomeAddress());
		List<User> results = new ArrayList<User>();
		try {
			results = ps.getPage(users.get(0), 0, 1);
		} catch (ApiException e) {
			e.printStackTrace();
		} catch (InterruptedException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		assertEquals("results after filtering by distance should equal usersFilteredByDistance", results, usersFilteredByDistance);
	}
	
	@Test
	public void testGetPageFiltersByTime() {
		List<User> usersFilteredByTime = new ArrayList<>();
		usersFilteredByTime.add(users.get(0));
		usersFilteredByTime.add(users.get(4));
		usersFilteredByTime.add(users.get(1));
		usersFilteredByTime.add(users.get(2));
		usersFilteredByTime.add(users.get(3));
		Mockito.when(us.getUserById(1)).thenReturn(users.get(0));
		Mockito.when(us.getActiveDriversWithOpenSeats(1)).thenReturn(users);
		Mockito.when(us.getGoogleHomeAddress(users.get(0)))
		.thenReturn(users.get(0).getGoogleHomeAddress());
		Mockito.when(us.getGoogleHomeAddress(users.get(1)))
		.thenReturn(users.get(1).getGoogleHomeAddress());
		Mockito.when(us.getGoogleHomeAddress(users.get(2)))
		.thenReturn(users.get(2).getGoogleHomeAddress());
		Mockito.when(us.getGoogleHomeAddress(users.get(3)))
		.thenReturn(users.get(3).getGoogleHomeAddress());
		Mockito.when(us.getGoogleHomeAddress(users.get(4)))
		.thenReturn(users.get(4).getGoogleHomeAddress());
		List<User> results = new ArrayList<User>();
		try {
			results = ps.getPage(users.get(0), 1, 1);
		} catch (ApiException e) {
			e.printStackTrace();
		} catch (InterruptedException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		assertEquals("results after filtering by distance should equal usersFilteredByDistance", results, usersFilteredByTime);
	}
	
	@Test
	public void testGetPageFiltersByName() {
		List<User> usersFilteredByName = new ArrayList<>();
		usersFilteredByName.add(users.get(3));
		usersFilteredByName.add(users.get(0));
		usersFilteredByName.add(users.get(1));
		usersFilteredByName.add(users.get(2));
		usersFilteredByName.add(users.get(4));
		System.out.println("User 1 is : " + user1);
		Mockito.when(us.getUserById(1)).thenReturn(users.get(0));
		Mockito.when(us.getActiveDriversWithOpenSeats(1)).thenReturn(users);
		Mockito.when(us.getGoogleHomeAddress(users.get(0)))
		.thenReturn(users.get(0).getGoogleHomeAddress());
		Mockito.when(us.getGoogleHomeAddress(users.get(1)))
		.thenReturn(users.get(1).getGoogleHomeAddress());
		Mockito.when(us.getGoogleHomeAddress(users.get(2)))
		.thenReturn(users.get(2).getGoogleHomeAddress());
		Mockito.when(us.getGoogleHomeAddress(users.get(3)))
		.thenReturn(users.get(3).getGoogleHomeAddress());
		Mockito.when(us.getGoogleHomeAddress(users.get(4)))
		.thenReturn(users.get(4).getGoogleHomeAddress());
		List<User> results = new ArrayList<User>();
		try {
			results = ps.getPage(users.get(0), 3, 1);
		} catch (ApiException e) {
			e.printStackTrace();
		} catch (InterruptedException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		System.out.println("Results: \n" + results );
		System.out.println("Filtered List: \n" + usersFilteredByName );
		assertEquals("results after filtering by name should equal usersFilteredByName", results, usersFilteredByName);
	}
	
	@Test
	public void testGetPageFiltersByAvailableSeats() {
		List<User> usersFilteredByAvailableSeats = new ArrayList<>();
		usersFilteredByAvailableSeats.add(users.get(3));
		usersFilteredByAvailableSeats.add(users.get(0));
		usersFilteredByAvailableSeats.add(users.get(1));
		usersFilteredByAvailableSeats.add(users.get(2));
		usersFilteredByAvailableSeats.add(users.get(4));
		Mockito.when(us.getUserById(1)).thenReturn(users.get(0));
		Mockito.when(us.getActiveDriversWithOpenSeats(1)).thenReturn(users);
		Mockito.when(us.getGoogleHomeAddress(users.get(0)))
		.thenReturn(users.get(0).getGoogleHomeAddress());
		Mockito.when(us.getGoogleHomeAddress(users.get(1)))
		.thenReturn(users.get(1).getGoogleHomeAddress());
		Mockito.when(us.getGoogleHomeAddress(users.get(2)))
		.thenReturn(users.get(2).getGoogleHomeAddress());
		Mockito.when(us.getGoogleHomeAddress(users.get(3)))
		.thenReturn(users.get(3).getGoogleHomeAddress());
		Mockito.when(us.getGoogleHomeAddress(users.get(4)))
		.thenReturn(users.get(4).getGoogleHomeAddress());
		List<User> results = new ArrayList<User>();
		try {
			results = ps.getPage(users.get(0), 3, 1);
		} catch (ApiException e) {
			e.printStackTrace();
		} catch (InterruptedException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		assertEquals("results after filtering by distance should equal usersFilteredByDistance", results, usersFilteredByAvailableSeats);
	}

}
