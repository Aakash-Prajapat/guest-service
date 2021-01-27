package com.epam.incubation.service.guestprofile.datamodel;

import java.util.List;

public class UserReservationLineDetails {

	private Integer roomId;
	private List<UserGuestDetails> guestDetails;

	public Integer getRoomId() {
		return roomId;
	}

	public void setRoomId(Integer roomId) {
		this.roomId = roomId;
	}

	public List<UserGuestDetails> getGuestDetails() {
		return guestDetails;
	}

	public void setGuestDetails(List<UserGuestDetails> guestDetails) {
		this.guestDetails = guestDetails;
	}

}
