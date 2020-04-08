package com.revature.security;

import com.google.common.collect.Sets;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

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
	
	
	/**
	 * This methods will set permissions for a level of authority
	 * @return
	 */
	public Set<SimpleGrantedAuthority> getGrantedAuthorities(){
		Set<SimpleGrantedAuthority> permissions = getPermissions().stream().map(permission->new SimpleGrantedAuthority(permission.getPermission())).collect(Collectors.toSet());
		permissions.add(new SimpleGrantedAuthority("ROLE_" + this.name()));
		return permissions;
	}
	
}
