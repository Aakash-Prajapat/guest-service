package com.epam.incubation.service.guestprofile.model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

public class GuestDetails implements UserDetails {

	private static final long serialVersionUID = 1L;
	private Guest guest;

	public GuestDetails(Guest guest) {
		this.guest = guest;
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		List<String> roles = Arrays.asList(guest.getRole().split(","));
		List<SimpleGrantedAuthority> authorities = new ArrayList<>();

		for (String role : roles) {
			authorities.add(new SimpleGrantedAuthority(role));
		}

		return authorities;
	}

	@Override
	public String getPassword() {

		return guest.getPassword();
	}

	@Override
	public String getUsername() {
		return guest.getUserName();
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
		return guest.isActive();
	}

}
