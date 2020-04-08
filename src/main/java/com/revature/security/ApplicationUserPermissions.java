package com.revature.security;

public enum ApplicationUserPermissions {
	ADMIN_READ("admin:read"),
	ADMIN_WRITE("admin:write"),
	USER_READ("user:read"),
	USER_WRITE("user:write");
	
	private final String permission;

	private ApplicationUserPermissions(String permission) {
		this.permission = permission;
	}
	
	public String getPermission() {
		return permission;
	}


}
