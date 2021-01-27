package com.epam.incubation.service.guestprofile.datamodel;

import java.util.Date;
import java.util.List;

public class UserReservationData {

	private Integer guestId;
	private Integer hotelId;
	private Date checkInDate;
	private Date checkOutDate;
	private Date createDate;
	private String state;
	private Double totalAmount;
	private List<UserReservationLineDetails> reservationLineDetails;

	public Integer getGuestId() {
		return guestId;
	}

	public void setGuestId(Integer guestId) {
		this.guestId = guestId;
	}

	public Integer getHotelId() {
		return hotelId;
	}

	public void setHotelId(Integer hotelId) {
		this.hotelId = hotelId;
	}

	public Date getCheckInDate() {
		return checkInDate;
	}

	public void setCheckInDate(Date checkInDate) {
		this.checkInDate = checkInDate;
	}

	public Date getCheckOutDate() {
		return checkOutDate;
	}

	public void setCheckOutDate(Date checkOutDate) {
		this.checkOutDate = checkOutDate;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public Double getTotalAmount() {
		return totalAmount;
	}

	public void setTotalAmount(Double totalAmount) {
		this.totalAmount = totalAmount;
	}

	public List<UserReservationLineDetails> getReservationLineDetails() {
		return reservationLineDetails;
	}

	public void setReservationLineDetails(List<UserReservationLineDetails> reservationLineDetails) {
		this.reservationLineDetails = reservationLineDetails;
	}

}
