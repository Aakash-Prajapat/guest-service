package com.epam.incubation.service.guestprofile.datamodel;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import com.epam.incubation.service.guestprofile.model.Guest;

public class GuestDataModel {

	@NotBlank(message = "First Name is mandatory")
	private String firstName;
	private String middleName;
	@NotBlank(message = "Last Name is mandatory")
	private String lastName;
	@NotBlank(message = "User Name is mandatory")
	private String userName;
	@NotBlank(message = "Password is mandatory")
	private String password;
	@NotBlank(message = "house number is mandatory")
	private String houseNumber;
	@NotBlank(message = "street is mandatory")
	private String street;
	@NotBlank(message = "City is mandatory")
	private String city;
	@NotBlank(message = "State is mandatory")
	private String state;
	@NotNull(message = "Zipcode is mandatory")
	private long zipcode;
	@NotBlank(message = "Country is mandatory")
	private String country;
	@Email
	@NotBlank(message = "Email is mandatory")
	private String email;
	@NotNull(message = "Phone Number is mandatory")
	private Long phoneNumber;
	@NotNull(message = "Gender is mandatory")
	private Character gender;

	public GuestDataModel(Guest guest) {
		this.firstName = guest.getName().getFirstName();
		this.middleName = guest.getName().getMiddleName();
		this.lastName = guest.getName().getLastName();
		this.userName = guest.getUserName();
		this.houseNumber = guest.getAddress().getAddressLine1();
		this.street = guest.getAddress().getAddressLine2();
		this.city = guest.getAddress().getCity();
		this.state = guest.getAddress().getState();
		this.zipcode = guest.getAddress().getZipcode();
		this.country = guest.getAddress().getCity();
		this.email = guest.getEmail();
		this.phoneNumber = guest.getPhoneNumber();
		this.gender = guest.getGender();
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getMiddleName() {
		return middleName;
	}

	public void setMiddleName(String middleName) {
		this.middleName = middleName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getHouseNumber() {
		return houseNumber;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public void setHouseNumber(String houseNumber) {
		this.houseNumber = houseNumber;
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

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Long getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(Long phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public Character getGender() {
		return gender;
	}

	public void setGender(Character gender) {
		this.gender = gender;
	}
}
