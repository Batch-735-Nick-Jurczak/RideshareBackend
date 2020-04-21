package com.revature.services.impl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
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
import com.revature.services.DistanceService;
import com.revature.services.UserService;

@Service
public class DistanceServiceImpl implements DistanceService {

	@Autowired
	private UserService us;

	
	/**
	 * Creates a Distance Matrix used by Google Maps Distance API to calculate distances between drivers and user.
	 * 
	 * Creates a Distance Matrix for use with the Google Maps Distance API, which calculates the distance between every given origin
	 * point as an array of strings, to every given destination as an array of strings.
	 * 
	 * @return A list of 5 of the closest Drivers.
	 * @deprecated use PageServiceImpl instead to utilize Sorting and Pagination.
	 * */
	
	// This function gets all of the available drivers and then returns the 5 closest. This should be used for best client performance,
	// however we found the function broken and elected to sort drivers on the front end. A fixed version of this method will
	// optimize the application.
	@Override
	public List<User> distanceMatrix(String[] origins, String[] destinations)
			throws ApiException, InterruptedException, IOException {

		Map<String, User> userDestMap = new HashMap<String, User>();

		List<String> destinationList = new ArrayList<String>();
		
		// Create an Array of Drivers, and navigate that Array to pull their address information.
		for(User d : us.getActiveDrivers()) {
			
			String add = d.gethAddress();
			String city = d.gethCity();
			String state = d.gethState();
			
			// Compile separate address fields into a single string.
			String fullAdd = add + ", " + city + ", " + state;
			
			// Add the full address string to a list of destinations.
			destinationList.add(fullAdd);
			
			// Add the full address string to a destination map.
			// (The address is a poor choice for a key, as multiple users can
			// have the same address, resulting in unexpected returns.)
			userDestMap.put(fullAdd, d);			
		}
		
		// Creates a new string with the same size as DestinationList and copies
		// DestinationList to it... why?
		destinations = new String[destinationList.size()];
		destinations = destinationList.toArray(destinations);
		
		// Create a Google Maps Distance API Call to calculate the distance between the given origin(s) and destinations.
		GeoApiContext context = new GeoApiContext.Builder().apiKey(getGoogleMAPKey()).build();
		List<Double> arrlist = new ArrayList<Double>();
		DistanceMatrixApiRequest req = DistanceMatrixApi.newRequest(context);
		DistanceMatrix t = req.origins(origins).destinations(destinations).mode(TravelMode.DRIVING).units(Unit.IMPERIAL)
				.await();
		
		// A temporary map to store the distance to each address.
		Map< Double, String> unsortMap = new HashMap<>();

		for (int i = 0; i < origins.length; i++) {
			for (int j = 0; j < destinations.length; j++) {
				try {
					// System.out.println((j+1) + "): " + t.rows[i].elements[j].distance.inMeters + " meters");
					
					// Adds the calculated distance to an array list.
					arrlist.add((double) t.rows[i].elements[j].distance.inMeters);
					
					// pairs the calculated distance to its address into an unsorted map.
					unsortMap.put((double) t.rows[i].elements[j].distance.inMeters, destinations[j]);
					
					// System.out.println((double) t.rows[i].elements[j].distance.inMeters);
					
				} catch (Exception e) {
					System.out.println("invalid address");
				}
			}
		}
		System.out.println("-");
		
		// Previous iteration used arrlist as a means to sort drivers by distance. However this logic
		// breaks down when multiple drivers use the same address because the address is used as the
		// map key above to retrieve stored users from the map.
		
		// Better Solution:
		
		// Use the Collections sort method on an array of drivers, using a custom Comparator Function
		// to sort the drivers by distance. Then the users can be used as the key for the map, ensuring
		// each key to the map is unique and won't produce unexpected results.
		Collections.sort(arrlist);

		System.out.println(arrlist);
		List<String> destList = new ArrayList<String>();
		
		// remove from this list any entry after #5.
	     arrlist.removeIf(r ->(arrlist.indexOf(r)>4));
			
	    // Create a new double array. 
	    Double [] arrArray = new Double[arrlist.size()];
			
		// Copies the ArrayList of distances into the double array - unnecessary conversion.
		arrArray = arrlist.toArray(arrArray);
			
		System.out.println(arrArray);
			
		// For each entry in the double array, retrieve the address from the unsorted map
		// using the distance as a key.
		for(int c=0; c< arrArray.length; c++) {
			String destination = unsortMap.get(arrArray[c]);
			destList.add(destination);
		}
			
		System.out.println(destList);

		// Creates a new String Array for Destination.
		String [] destArray = new String[destList.size()];
		
		// Copies existing ArrayList into the String Array - another unnecessary conversion.
		destArray = destList.toArray(destArray);

		List<User> userList = new ArrayList<User>();
		
		// For each destination, retrieve a user from the userDestMap using an address from the destArray
		// And add that user to userList.
		for(int x=0; x< destArray.length; x++) {
			User a = userDestMap.get(destArray[x]);
			System.out.println(a);
			userList.add(a);
			System.out.println(userList);
		}

		return userList;
	}
	
	/**
	 * Retrieves a GoogleMap Key from the user's System Environment Variables.
	 * 
	 * Retrieves all of the user's system environment variables as a map then returns 
	 * the first variable found named as "googleMapAPIKey" (Security Issue?)
	 * 
	 * */
	
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