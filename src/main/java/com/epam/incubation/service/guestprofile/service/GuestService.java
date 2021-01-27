package com.epam.incubation.service.guestprofile.service;

import java.util.List;

import com.epam.incubation.service.guestprofile.datamodel.GuestDataModel;
import com.epam.incubation.service.guestprofile.datamodel.UserReservationData;

public interface GuestService {

	public GuestDataModel guestById(Integer id);

	public GuestDataModel saveGuest(GuestDataModel guest);

	public GuestDataModel disableGuest(Integer id);
	
	public List<UserReservationData> guestHistory(Integer id) throws Exception;
		
}
