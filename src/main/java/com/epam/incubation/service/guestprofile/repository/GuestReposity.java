package com.epam.incubation.service.guestprofile.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.epam.incubation.service.guestprofile.model.Guest;

@Repository
public interface GuestReposity extends CrudRepository<Guest, Integer> {

}
