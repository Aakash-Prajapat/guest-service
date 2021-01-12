package com.epam.incubation.service.guestprofile.service;

import java.util.Optional;

import com.epam.incubation.service.guestprofile.datamodel.GuestDataModel;

public interface GuestService {

	public Optional<GuestDataModel> guestById(Integer id);

	public GuestDataModel saveGuest(GuestDataModel guest);

	public GuestDataModel disableGuest(Integer id);
}
