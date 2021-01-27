package com.epam.incubation.service.guestprofile.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.lenient;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.epam.incubation.service.guestprofile.datamodel.ApiError;
import com.epam.incubation.service.guestprofile.datamodel.GuestDataModel;
import com.epam.incubation.service.guestprofile.datamodel.UserGuestDetails;
import com.epam.incubation.service.guestprofile.datamodel.UserReservationData;
import com.epam.incubation.service.guestprofile.datamodel.UserReservationDataResponse;
import com.epam.incubation.service.guestprofile.datamodel.UserReservationLineDetails;
import com.epam.incubation.service.guestprofile.exception.GlobalException;
import com.epam.incubation.service.guestprofile.exception.RecordNotFoundException;
import com.epam.incubation.service.guestprofile.facade.ReservationServiceProxy;
import com.epam.incubation.service.guestprofile.model.Address;
import com.epam.incubation.service.guestprofile.model.Guest;
import com.epam.incubation.service.guestprofile.model.Name;
import com.epam.incubation.service.guestprofile.repository.GuestRepository;

@ExtendWith(MockitoExtension.class)
class GuestServiceImplTest {

	@Mock
	private GuestRepository guestRepository;
	@Mock
	private BCryptPasswordEncoder bCryptPasswordEncoder;
	@InjectMocks
	GuestServiceImpl service;
	@Mock
	ReservationServiceProxy reservationServiceProxy;

	@Test
	void getGuestById_ShouldReturnGuest() throws ParseException {
		Optional<Guest> guest = Optional.ofNullable(getMockedGuest("getById").get(0));
		given(guestRepository.findById(1)).willReturn(guest);
		GuestDataModel getGuest = service.guestById(1);
		assertEquals("Amit", getGuest.getFirstName());

	}

	@Test
	void getGuestById_ShouldThrowException() throws Exception {
		given(guestRepository.findById(1)).willReturn(Optional.empty());
		assertThrows(RecordNotFoundException.class, () -> service.guestById(1));
	}

	@Test
	void disableGuest_ShouldReturnGuest() throws ParseException {
		Optional<Guest> guest = Optional.ofNullable(getMockedGuest("getById").get(0));
		guest.get().setActive(false);
		Guest g1 = guest.get();
		given(guestRepository.findById(1)).willReturn(guest);
		lenient().when(guestRepository.save(guest.get())).thenReturn(g1);
		GuestDataModel getGuest = service.disableGuest(1);
		assertEquals(false, getGuest.isActive());
	}

	@Test
	void disableGuest_ShouldThrowException() throws Exception {
		given(guestRepository.findById(1)).willReturn(Optional.empty());
		RecordNotFoundException thrown = assertThrows(RecordNotFoundException.class, () -> service.disableGuest(1));
		assertEquals("Guest Not found with 1", thrown.getMessage());
	}

	@Test
	void GuestHistory_ShouldReturnGuestHistory() throws Exception {
		SimpleDateFormat myFormat = new SimpleDateFormat("dd-MM-yyyy");
		UserReservationDataResponse userdata = new UserReservationDataResponse();
		UserReservationData reservations1 = new UserReservationData();
		reservations1.setCheckInDate(myFormat.parse("01-02-2021"));
		reservations1.setCheckOutDate(myFormat.parse("02-02-2021"));
		reservations1.setCreateDate(myFormat.parse("05-01-2021"));
		reservations1.setGuestId(1);
		reservations1.setHotelId(1);
		reservations1.setState("BOOKED");
		reservations1.setTotalAmount(1000.0);
		UserReservationLineDetails line1 = new UserReservationLineDetails();
		line1.setRoomId(1);
		UserGuestDetails guest1 = new UserGuestDetails();
		guest1.setEmail("abc@xyz.com");
		guest1.setFirstName("aakasj");
		guest1.setMiddleName("kumar");
		guest1.setLastName("fjlksj");
		guest1.setGender('M');
		line1.setGuestDetails(Arrays.asList(guest1));
		reservations1.setReservationLineDetails(Arrays.asList(line1));
		userdata.setReservations(Arrays.asList(reservations1));
		given(reservationServiceProxy.getGuestReservationHistory(1)).willReturn(userdata);
		List<UserReservationData> reservations = service.guestHistory(1);
		assertEquals(1, reservations.size());

	}

	@Test
	void saveGuest_ShouldSaveAndReturnSavedGuest() throws Exception {
		// 1. Guest
		GuestDataModel guestDataModel = new GuestDataModel();
		guestDataModel.setHouseNumber("1");
		guestDataModel.setStreet("Nagar Road");
		guestDataModel.setCity("Indore");
		guestDataModel.setState("MP");
		guestDataModel.setZipcode(422323);
		guestDataModel.setCountry("India");
		guestDataModel.setFirstName("Amit");
		guestDataModel.setMiddleName("Kumar");
		guestDataModel.setLastName("Jain");
		guestDataModel.setGender('M');
		guestDataModel.setEmail("abc@xyz.com");
		guestDataModel.setPassword("12345");
		guestDataModel.setPhoneNumber(12121212L);
		guestDataModel.setUserName("abc123");

		Guest guest = getMockedGuest("save").get(0);
		lenient().when(guestRepository.save(any(Guest.class))).thenReturn(guest);
		GuestDataModel guestModel = service.saveGuest(guestDataModel);
		assertEquals(guest.getEmail(), guestModel.getEmail());
	}

	@Test
	void GuestHistory_ShouldThrowNotFoundException() throws Exception {
		given(reservationServiceProxy.getGuestReservationHistory(1)).willReturn(null);
		assertThrows(RecordNotFoundException.class, () -> service.guestHistory(1));
	}

	@Test
	void GuestHistory_ShouldThrowNotFoundException1() throws Exception {
		UserReservationDataResponse response = new UserReservationDataResponse();
		ApiError error = new ApiError(HttpStatus.NOT_FOUND, "Reservations not present with id 1");
		response.setError(error);
		given(reservationServiceProxy.getGuestReservationHistory(1)).willReturn(response);
		assertThrows(RecordNotFoundException.class, () -> service.guestHistory(1));
	}

	@Test
	void GuestHistory_ShouldThrowGlobalException1() throws Exception {
		UserReservationDataResponse response = new UserReservationDataResponse();
		ApiError error = new ApiError();
		error.setStatus(HttpStatus.SERVICE_UNAVAILABLE);
		error.setSubErrors(null);
		error.setMessage("Exception while retriving the guestHistory");
		response.setError(error);
		given(reservationServiceProxy.getGuestReservationHistory(1)).willReturn(response);
		assertThrows(GlobalException.class, () -> service.guestHistory(1));
	}

	@Test
	void saveGuest_ShouldthrowException() throws Exception {
		// 1. Guest
		GuestDataModel guestDataModel = new GuestDataModel();
		guestDataModel.setHouseNumber("1");
		guestDataModel.setStreet("Nagar Road");
		guestDataModel.setCity("Indore");
		guestDataModel.setState("MP");
		guestDataModel.setZipcode(422323);
		guestDataModel.setCountry("India");
		guestDataModel.setFirstName("Amit");
		guestDataModel.setMiddleName("Kumar");
		guestDataModel.setLastName("Jain");
		guestDataModel.setGender('M');
		guestDataModel.setEmail("abc@xyz.com");
		guestDataModel.setPassword("12345");
		guestDataModel.setPhoneNumber(12121212L);
		guestDataModel.setUserName("abc123");

		Guest guest = getMockedGuest("save").get(0);
		lenient().when(guestRepository.save(any(Guest.class))).thenReturn(guest);
		GuestDataModel guestModel = service.saveGuest(guestDataModel);
		assertEquals(guest.getEmail(), guestModel.getEmail());
	}

	private List<Guest> getMockedGuest(String methodType) throws ParseException {

		// 1. Guest
		Address address1 = new Address();
		address1.setAddressLine1("1");
		address1.setAddressLine2("Nagar Road");
		address1.setCity("Indore");
		address1.setState("MP");
		address1.setZipcode(422323);
		address1.setCountry("India");

		Address address2 = new Address();
		address2.setAddressLine1("1");
		address2.setAddressLine2("Nagar Road");
		address2.setCity("Indore");
		address2.setState("MP");
		address2.setZipcode(422323);
		address2.setCountry("India");
		Name name1 = new Name();
		name1.setFirstName("Amit");
		name1.setMiddleName("Kumar");
		name1.setLastName("Jain");
		Guest guest1 = new Guest();
		guest1.setAddress(address1);
		guest1.setActive(true);
		guest1.setEmail("abc@xyz.com");
		guest1.setGender('M');
		guest1.setId(1);
		guest1.setName(name1);
		guest1.setPassword("test1234");
		guest1.setPhoneNumber(12344543L);
		guest1.setRole("STANDARD");
		guest1.setUserName("amit_jain");

		switch (methodType) {
		case "getById":
			return Arrays.asList(guest1);
		case "disableGuest":
			return Arrays.asList(guest1);
		case "save":
			return Arrays.asList(guest1);
		default:
			return Arrays.asList(guest1); // get All Guest
		}
	}

}
