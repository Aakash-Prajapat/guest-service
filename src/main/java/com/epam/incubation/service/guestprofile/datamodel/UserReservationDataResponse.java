package com.epam.incubation.service.guestprofile.datamodel;

import java.util.List;

public class UserReservationDataResponse {
	List<UserReservationData> reservations;

	public List<UserReservationData> getReservations() {
		return reservations;
	}

	public void setReservations(List<UserReservationData> reservations) {
		this.reservations = reservations;
	}
}
