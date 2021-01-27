package com.epam.incubation.service.guestprofile.datamodel;

import java.util.Collection;

public class HotelDataModel {

	private String hotelName;
	private String hotelDescription;
	private String propertyNumber;
	private String street;
	private String city;
	private String state;
	private long zipcode;
	private String country;
	private Collection<AmenityDataModel> amenities;
	private Collection<RoomDataModel> rooms;
	private Boolean status;
	private int rating;

	public String getHotelName() {
		return hotelName;
	}

	public void setHotelName(String hotelName) {
		this.hotelName = hotelName;
	}

	public String getHotelDescription() {
		return hotelDescription;
	}

	public void setHotelDescription(String hotelDescription) {
		this.hotelDescription = hotelDescription;
	}

	public String getPropertyNumber() {
		return propertyNumber;
	}

	public void setPropertyNumber(String propertyNumber) {
		this.propertyNumber = propertyNumber;
	}

	public String getStreet() {
		return street;
	}

	public void setStreet(String street) {
		this.street = street;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public long getZipcode() {
		return zipcode;
	}

	public void setZipcode(long zipcode) {
		this.zipcode = zipcode;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public Collection<AmenityDataModel> getAmenities() {
		return amenities;
	}

	public void setAmenities(Collection<AmenityDataModel> amenities) {
		this.amenities = amenities;
	}

	public Collection<RoomDataModel> getRooms() {
		return rooms;
	}

	public void setRooms(Collection<RoomDataModel> rooms) {
		this.rooms = rooms;
	}

	public Boolean getStatus() {
		return status;
	}

	public void setStatus(Boolean status) {
		this.status = status;
	}

	public int getRating() {
		return rating;
	}

	public void setRating(int rating) {
		this.rating = rating;
	}
}
