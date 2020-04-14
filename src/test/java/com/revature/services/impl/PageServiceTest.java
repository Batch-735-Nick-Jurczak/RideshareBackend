package com.revature.services.impl;

import static org.junit.Assert.*;
import static org.mockito.Mockito.when;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Positive;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.ArgumentMatchers;
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
	
	@Mock
	private CarRepository cr;
	
	@Mock
	private UserRepository ur;

	@Test
	public void test() {
		List<User> users = new ArrayList<>();
		users.add(new User(0,"gpichmann0", new Batch(), "Grady", "Pichmann", "gpichmann0@artisteer.com", "212-374-3466", "5 Carpenter Plaza", "New York City", "10275", "NY", "30401 Esker Point", "Des Moines", "50347", "IA"));
		users.add(new User(1,"gpichmann1", new Batch(), "Grady", "Pichmann", "gpichmann1@artisteer.com", "212-374-3466", "6 Carpenter Plaza", "New York City", "10275", "NY", "30401 Esker Point", "Des Moines", "50347", "IA"));
		users.add(new User(2,"gpichmann2", new Batch(), "Grady", "Pichmann", "gpichmann2@artisteer.com", "212-374-3466", "7 Carpenter Plaza", "New York City", "10275", "NY", "30401 Esker Point", "Des Moines", "50347", "IA"));
		users.add(new User(3,"gpichmann3", new Batch(), "Grady", "Pichmann", "gpichmann3@artisteer.com", "212-374-3466", "8 Carpenter Plaza", "New York City", "10275", "NY", "30401 Esker Point", "Des Moines", "50347", "IA"));
		users.add(new User(4,"gpichmann4", new Batch(), "Grady", "Pichmann", "gpichmann4@artisteer.com", "212-374-3466", "9 Carpenter Plaza", "New York City", "10275", "NY", "30401 Esker Point", "Des Moines", "50347", "IA"));
		List<Car> cars = new ArrayList<>();
		cars.add(new Car(0, "Red", 3, "Generic", "Bus", 1995, users.get(0)));
		cars.add(new Car(1, "Yellow", 5, "Generic", "Bus", 1995, users.get(1)));
		cars.add(new Car(2, "Green", 7, "Generic", "Bus", 1995, users.get(2)));
		cars.add(new Car(3, "blue", 9, "Generic", "Bus", 1995, users.get(3)));
		cars.add(new Car(4, "Black", 1, "Generic", "Bus", 1995, users.get(4)));
		Mockito.when(us.getUserById(1)).thenReturn(users.get(0));
		Mockito.when(us.getActiveDriversWithOpenSeats(1)).thenReturn(users);
		Mockito.when(cs.getCarByUserId(0)).thenReturn(cars.get(0));
		Mockito.when(cs.getCarByUserId(1)).thenReturn(cars.get(1));
		Mockito.when(cs.getCarByUserId(2)).thenReturn(cars.get(2));
		Mockito.when(cs.getCarByUserId(3)).thenReturn(cars.get(3));
		Mockito.when(cs.getCarByUserId(4)).thenReturn(cars.get(4));
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
		
		System.out.println(results);
		assertTrue(results.size() == 5);
	}

}
