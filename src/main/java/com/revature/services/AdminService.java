package com.revature.services;

import java.util.List;
import java.util.Optional;

import com.revature.beans.Admin;

public interface AdminService {
	
	public List<Admin> getAdmins();
	public Optional<Admin> getAdminById(String username);
	public Admin createAdmin(Admin admin);
	public Admin updateAdmin(Admin admin);
	public String deleteAdminById(String username);
}
