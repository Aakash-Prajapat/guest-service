package com.epam.incubation.service.guestprofile.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.epam.incubation.service.guestprofile.model.Guest;

@Repository
public interface GuestRepository extends JpaRepository<Guest, Integer> {

	public Guest getGuestByUserName(String userName);
}
