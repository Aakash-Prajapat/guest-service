package com.epam.incubation.service.guestprofile.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import com.epam.incubation.service.guestprofile.exception.RecordNotFoundException;
import com.epam.incubation.service.guestprofile.model.Guest;
import com.epam.incubation.service.guestprofile.model.GuestDetails;
import com.epam.incubation.service.guestprofile.repository.GuestRepository;

@Service
public class GuestDetailsServiceImpl implements UserDetailsService {

	@Autowired
	GuestRepository guestRepository;

	@Override
	public GuestDetails loadUserByUsername(String userName) throws RecordNotFoundException {
		Guest guest = guestRepository.getGuestByUserName(userName);
		if (guest == null) {
			throw new RecordNotFoundException("Could not find guest by " + userName);
		}

		return new GuestDetails(guest);
	}

}
