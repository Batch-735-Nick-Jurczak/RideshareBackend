package com.revature.beans;


import java.util.Collection;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;



/*
 * This class implements a Spring Security Interface "UserDetails". This will handle checking the username and passwords of users and get what they're
 * Authorized to do. It also checks for expired accounts, credentials, locked, or disabled accounts. It's essential a 
 * Springboot model for a user.
 */
@Entity
@Table(name = "applicationusers")
public class ApplicationUser implements UserDetails {
	
	
	/*
	 * Initial values for the application user
	 */
	@Transient
	private final List<? extends GrantedAuthority> grantedAuthority;
	
	@Id
	@Valid
	@NotBlank
	@Column(name="user_name")
	@Size(min=3,max=12)
	@Pattern(regexp="^\\w+\\.?\\w+$")
	private String username;
	
	
	@Valid
	@NotBlank
	@Column(name = "password")
	@Size(min=6, max=20)
	private String password;
	
	@Valid
	@NotBlank
	@Column(name = "role")
	private String role;
	
	
	
	
	@Column(name = "isAccountNonExpired")
	private final boolean isAccountNonExpired;
	
	@Column(name = "isAccountNonLocked")
	private final boolean isAccountNonLocked;
	
	@Column(name = "isCredentialsNonExpired")
	private final boolean isCredentialsNonExpired;
	
	@Column(name = "isEnabled")
	private final boolean isEnabled;
	
	
	
	
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return grantedAuthority;
	}

	@Override
	public String getPassword() {
		return password;
	}

	@Override
	public String getUsername() {
		return password;
	}

	@Override
	public boolean isAccountNonExpired() {
		return isAccountNonExpired;
	}

	@Override
	public boolean isAccountNonLocked() {
		return isAccountNonLocked;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return isCredentialsNonExpired;
	}

	@Override
	public boolean isEnabled() {
		return isEnabled;
	}

	public ApplicationUser(List<? extends GrantedAuthority> grantedAuthority, String username, String password,
			boolean isAccountNonExpired, boolean isAccountNonLocked, boolean isCredentialsNonExpired,
			boolean isEnabled) {
		
		super();
		this.grantedAuthority = grantedAuthority;
		this.username = username;
		this.password = password;
		this.isAccountNonExpired = isAccountNonExpired;
		this.isAccountNonLocked = isAccountNonLocked;
		this.isCredentialsNonExpired = isCredentialsNonExpired;
		this.isEnabled = isEnabled;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((password == null) ? 0 : password.hashCode());
		result = prime * result + ((username == null) ? 0 : username.hashCode());
		return result;
	}

	
	/*
	 * For now the .equals method will only check username and password, but ultimately this can check if the other checks
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ApplicationUser other = (ApplicationUser) obj;
		if (password == null) {
			if (other.password != null)
				return false;
		} else if (!password.equals(other.password))
			return false;
		if (username == null) {
			if (other.username != null)
				return false;
		} else if (!username.equals(other.username))
			return false;
		return true;
	}
	
	
	

}
