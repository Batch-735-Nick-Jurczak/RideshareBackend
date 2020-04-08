package com.revature.security;

import com.google.common.collect.Sets;
import java.util.Set;

import static com.revature.security.ApplicationUserPermissions.*;

public enum ApplicationUserRole {
	ADMIN(Sets.newHashSet(ADMIN_READ, ADMIN_WRITE,USER_READ,USER_WRITE)),
	ADMINTRAINEE(Sets.newHashSet(ADMIN_READ, ADMIN_WRITE)),
	USER(Sets.newHashSet(USER_READ, USER_WRITE));
	
	private final Set<ApplicationUserPermissions> permissions;

	ApplicationUserRole(Set<ApplicationUserPermissions> permissions) {
		this.permissions = permissions;
	}
	
	public Set<ApplicationUserPermissions> getPermissions(){
		return permissions;
	}
	
}
