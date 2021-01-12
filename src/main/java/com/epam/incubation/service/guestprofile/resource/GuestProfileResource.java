package com.epam.incubation.service.guestprofile.resource;

import org.springframework.http.ResponseEntity;

import com.epam.incubation.service.guestprofile.datamodel.GuestDataModel;

public interface GuestProfileResource {

	public ResponseEntity<GuestDataModel> add(GuestDataModel guest);

	public ResponseEntity<GuestDataModel> disableGuest(Integer id);

}
