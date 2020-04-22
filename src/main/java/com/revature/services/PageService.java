package com.revature.services;

import java.io.IOException;
import java.util.List;

import com.google.maps.errors.ApiException;
import com.revature.beans.User;


public interface PageService {
 
	public List<User> getPage (int id, int batch, int filter, int page) throws ApiException, InterruptedException, IOException ;
	
	// Place key googleMapAPIKey & value apiKey (to be shared on slack) into Environment Vars.
	public  String getGoogleMAPKey();
	
}