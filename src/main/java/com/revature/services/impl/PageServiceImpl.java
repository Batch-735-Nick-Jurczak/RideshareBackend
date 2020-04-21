package com.revature.services.impl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.maps.DistanceMatrixApi;
import com.google.maps.DistanceMatrixApiRequest;
import com.google.maps.GeoApiContext;
import com.google.maps.errors.ApiException;
import com.google.maps.model.DistanceMatrix;
import com.google.maps.model.TravelMode;
import com.google.maps.model.Unit;
import com.revature.beans.User;
import com.revature.services.CarService;
import com.revature.services.PageService;
import com.revature.services.UserService;

/**
 * PageServiceImpl handles the Sorting and Pagination of drivers before passing them
 * to the front end for display.
 * 
 * @author Calvin England
 *
 */

@Service
public class PageServiceImpl implements PageService {
	
	@Autowired
	private UserService us;
	
	@Autowired
	private CarService cs;

	/**
	 * Page Size determines how many drivers are returned.
	 * 
	 * @author Calvin England
	 *
	 */
	// Currently set to 5 as has been the convention in previous iterations. 
	// Use a controller mapping to change the page size in future iterations.
	private static int pagesize = 5;
	
	public static int getPagesize() {
		return pagesize;
	}

	public static void setPagesize(int pagesize) {
		PageServiceImpl.pagesize = pagesize;
	}

	/**
	 * Returns a Sorted and Paginated List of all the valid drivers in an area.
	 * 
	 * Calls a function to get all of the valid drivers in an area from the DB, then uses the Google Maps API 
	 * to apply distance and time to those users before sorting those users by a given filter and finally 
	 * returning the range of drivers on the requested page.
	 * 
	 * @param id 		the id number of the current user for grabbing the origin address.
	 * @param batch 	the batch number for grabbing all of the drivers in an area.
	 * @param filter 	an integer that specifies which element to sort the drivers by.
	 * @param page 		The page number to view from the whole of the results.
	 * 
	 * @return A page of drivers sorted by the given filter.
	 * @author Calvin England
	 * */
	@Override
	public List<User> getPage(int id, int batch, int filter, int page) throws ApiException, InterruptedException, IOException {
		
		User current = us.getUserById(id);
		List<User> userlist = us.getActiveDriversWithOpenSeats(batch);
		List<User> result = new ArrayList<User>();
		
		String origin = current.getGoogleHomeAddress();
		System.out.println("Origin: " + origin);

		// Add distance and duration from distance matrix to each driver
		List<User> driversWithDistanceMatrix = addDistanceMatrixToDrivers(userlist, origin);
		System.out.println("-");
		
		// Sort based off of Filter
		List<User> filteredDrivers = filterDrivers(driversWithDistanceMatrix, filter);
		
		// Paginate
		for(int i = 0; i < pagesize; i++) {
			result.add(filteredDrivers.get((pagesize * (page - 1)) + i));
		}
		
		System.out.println(result);
		return result;
	}

	/**
	 * Uses the Google Maps API to add distance and time from origin to drivers
	 * 
	 * @param userlist the list of drivers before distance and time are added
	 * @param origin the address of the current user
	 * @return list of drivers with distance and time from origin
	 * @throws ApiException
	 * @throws InterruptedException
	 * @throws IOException
	 * @author Calvin England
	 */
	public List<User> addDistanceMatrixToDrivers(List<User> userlist, String origin) throws ApiException, InterruptedException, IOException {

		String[] destinations = new String[userlist.size()];
		
		// Create a list of destinations to be passed to Google API for calculating distances.
		for(int d = 0; d < userlist.size(); d++) {
			destinations[d] = userlist.get(d).getGoogleHomeAddress();	
			//destinations[d] = userlist.get(d).gethAddress() + ", " + userlist.get(d).gethCity() + ", " + userlist.get(d).gethState();
		}
		
		// Create a Google Maps Distance API Call to calculate the distance between the given origin(s) and destinations.
		GeoApiContext context = new GeoApiContext.Builder().apiKey(getGoogleMAPKey()).build();
		DistanceMatrixApiRequest req = DistanceMatrixApi.newRequest(context);
		DistanceMatrix t = req.origins(origin).destinations(destinations).mode(TravelMode.DRIVING).units(Unit.IMPERIAL)
				.await();

		for (int j = 0; j < destinations.length; j++) {
			try {
				// Add the calculated distance to the users.
				userlist.get(j).setDistance(t.rows[0].elements[j].distance.inMeters);
				
				// Add the calculated Time in Minutes to the users.
				// In the future, use durationInTraffic to get more realistic times separate from distance.
				userlist.get(j).setTime((t.rows[0].elements[j].duration.inSeconds)/60);
				
			} catch (Exception e) {
			System.out.println("invalid address");
			}
		}
		return userlist;
	}
	
	/**
	 * Sorts drivers by a given filter
	 * 
	 * filter = 0 (DEFAULT): sort by distance from origin
	 * filter = 1: sort by time from origin
	 * filter = 2: sort by available seats
	 * filter = 3: sort by first name
	 * 
	 * @param userlist the list of drivers with distance and time from origin
	 * @param filter the field to filter by
	 * @return the sorted list of drivers
	 * @author Calvin England
	 */
	public List<User> filterDrivers(List<User> userlist, int filter) {
		switch(filter) {
			case 1: {	// Sort by Time between origin and destination.
				userlist.sort((d1, d2) -> Double.compare(d1.getTime(), d2.getTime()));
				break;
				}
			case 2: {	// Sort by number of available seats.
				userlist.sort((d1, d2) -> Integer.compare(d1.getCar().getAvailableSeats(), 
						d2.getCar().getAvailableSeats()));
				break;
				}
			case 3: {	// Sort by First Name of Driver.
				userlist.sort((d1, d2) -> d1.getFirstName().compareTo(d2.getFirstName()));
				break;
				}
			default: {	// Sort by Distance from Origin.
				userlist.sort((d1, d2) -> Double.compare(d1.getDistance(), d2.getDistance()));
				break;
				}
			}
		return userlist;
	}

	/**
	 * Retrieves a GoogleMap Key from the System Environment Variables.
	 * 
	 * Retrieves all of the system environment variables as a map then
	 * @return the first variable found named as "googleMapAPIKey"
	 */
	public String getGoogleMAPKey() {
        Map<String, String> env = System.getenv();
        for (Map.Entry <String, String> entry: env.entrySet()) {
            if(entry.getKey().equals("googleMapAPIKey")) {
                return entry.getValue();
            }
        }
        return null;
    }

}