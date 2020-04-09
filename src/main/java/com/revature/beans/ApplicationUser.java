package com.revature.beans;


import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

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
import org.springframework.security.core.authority.SimpleGrantedAuthority;
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
	private List<? extends GrantedAuthority> grantedAuthority;
	
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
	private boolean isAccountNonExpired;
	
	@Column(name = "isAccountNonLocked")
	private boolean isAccountNonLocked;
	
	@Column(name = "isCredentialsNonExpired")
	private boolean isCredentialsNonExpired;
	
	@Column(name = "isEnabled")
	private boolean isEnabled;
	
	@Column(name = "active")
	private boolean active;
	
	
	
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return grantedAuthority;
	}

	@Override
	public String getPassword() {
		return password;
	}
	
	public void setPassword(String password) {
		this.password = password;
	}

	@Override
	public String getUsername() {
		return username;
	}

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return active;
	}
	
	

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	public boolean isActive() {
		return active;
	}

	public void setActive(boolean active) {
		this.active = active;
	}

	public void setUsername(String username) {
		this.username = username;
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
	public ApplicationUser(User user) {
		this.username = user.getFirstName();
		this.password = user.getPassword();
		this.active = user.isActive();
		this.grantedAuthority = Arrays.stream(user.getRole().split(",")).map(SimpleGrantedAuthority::new).collect(Collectors.toList());
	}

	
	public ApplicationUser(UserDetails userD) {
		super();
		this.grantedAuthority = Arrays.stream(userD.getAuthorities().toString().split(",")).map(SimpleGrantedAuthority::new).collect(Collectors.toList());
		this.username = userD.getUsername();
		this.password = userD.getPassword();
		this.role = "User";
		this.isAccountNonExpired = userD.isAccountNonExpired();
		this.isAccountNonLocked = userD.isAccountNonLocked();
		this.isCredentialsNonExpired = userD.isCredentialsNonExpired();
		this.isEnabled = userD.isEnabled();
		
	}

	public ApplicationUser() {
		super();
		// TODO Auto-generated constructor stub
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
