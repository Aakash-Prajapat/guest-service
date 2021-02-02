package com.epam.incubation.service.guestprofile.resource;

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.lenient;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.FilterChainProxy;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.epam.incubation.service.guestprofile.datamodel.GuestDataModel;
import com.epam.incubation.service.guestprofile.datamodel.UserGuestDetails;
import com.epam.incubation.service.guestprofile.datamodel.UserReservationData;
import com.epam.incubation.service.guestprofile.datamodel.UserReservationLineDetails;
import com.epam.incubation.service.guestprofile.exception.RecordNotFoundException;
import com.epam.incubation.service.guestprofile.service.GuestServiceImpl;
import com.epam.incubation.service.guestprofile.utility.JwtUtil;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@ExtendWith(MockitoExtension.class)
@SpringBootTest
class GuestProfileResourceImplTest {

	private MockMvc mockMvc;

	@Mock
	private ObjectMapper objectMapper;

	@InjectMocks
	GuestProfileResourceImpl guestProfileResourceImpl;

	@Mock
	GuestServiceImpl service;

	@Autowired
	FilterChainProxy springSecurityFilterChain;
	
	@Autowired
	JwtUtil jwtUtil;

	@BeforeEach
	void setup() {
		mockMvc = MockMvcBuilders.standaloneSetup(guestProfileResourceImpl)
				.setControllerAdvice(new RestExceptionHandler()).apply(springSecurity(springSecurityFilterChain))
				.build();
	}

	@Test
	void getGuestById_ShouldReturnGuest() throws Exception {
		GuestDataModel guestDataModel = getMockedGuests("getById").get(0);
		UserDetails userDetails = new User("Guest", "test", Arrays.asList(() -> "ROLE_GUEST"));
		String token = jwtUtil.generateToken(userDetails);
		given(service.guestById(1)).willReturn(guestDataModel);
		mockMvc.perform(
				MockMvcRequestBuilders.get("/guestservice/1").header("Authorization", "Bearer "+token).with(user("Guest").password("password").roles("GUEST")))
				.andExpect(status().isOk()).andDo(MockMvcResultHandlers.print())
				.andExpect(jsonPath("$.firstName").value("Amit"));
	}
	
	@Test
	void getGuestById_ShouldThrowNotFoundException() throws Exception {
		given(service.guestById(1)).willThrow(new RecordNotFoundException("Record Not Found with 1"));

		mockMvc.perform(
				MockMvcRequestBuilders.get("/guestservice/1").with(user("Guest").password("test").roles("GUEST")))
				.andExpect(status().isNotFound()).andDo(MockMvcResultHandlers.print());
	}

	@Test
	void DisableGuest_ShouldDisableGuest() throws Exception {
		GuestDataModel guestDataModel = getMockedGuests("getAll").get(0);
		given(service.disableGuest(1)).willReturn(guestDataModel);
		mockMvc.perform(MockMvcRequestBuilders.put("/guestservice/disable//1")
				.with(user("Guest").password("password").roles("GUEST"))).andExpect(status().isOk())
				.andDo(MockMvcResultHandlers.print());
	}
	
	@Test
	void GuestHistory_ShouldReturnGuestHistory() throws Exception {
		SimpleDateFormat myFormat = new SimpleDateFormat("dd-MM-yyyy");
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
		UserGuestDetails guest1= new UserGuestDetails();
		guest1.setEmail("abc@xyz.com");
		guest1.setFirstName("aakasj");
		guest1.setMiddleName("kumar");
		guest1.setLastName("fjlksj");
		guest1.setGender('M');
		line1.setGuestDetails(Arrays.asList(guest1));
		reservations1.setReservationLineDetails(Arrays.asList(line1));
		
		given(service.guestHistory(1)).willReturn(Arrays.asList(reservations1));
		mockMvc.perform(MockMvcRequestBuilders.get("/guestservice/guesthistory/1")
				.with(user("Guest").password("test").roles("GUEST"))).andExpect(status().isOk())
				.andDo(MockMvcResultHandlers.print());
	}

	@Test
	void saveGuest_ShouldSavedGuest() throws Exception {
		GuestDataModel guestDataModel = getMockedGuests("add").get(0);
		lenient().doReturn(guestDataModel).when(service).saveGuest(ArgumentMatchers.eq(guestDataModel));
		mockMvc.perform(MockMvcRequestBuilders.post("/guestservice/signup")
				.content(asJsonString(guestDataModel)).contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON).with(user("Guest").password("password").roles("GUEST")))
				.andExpect(status().isCreated()).andDo(MockMvcResultHandlers.print());
	}

	@Test
	void saveGuest_ShoudThrowException() throws Exception {
		GuestDataModel guestDataModel = getMockedGuests("add").get(0);
		guestDataModel.setCity(null);
		lenient().doReturn(guestDataModel).when(service).saveGuest(ArgumentMatchers.eq(guestDataModel));
		mockMvc.perform(MockMvcRequestBuilders.post("/guestservice/signup")
				.content(asJsonString(guestDataModel)).contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON).with(user("Guest").password("password").roles("GUEST")))
				.andExpect(status().isBadRequest()).andDo(MockMvcResultHandlers.print());
	}
	
	@Test
	void saveGuest_ShoudThrowException1() throws Exception {
		GuestDataModel guestDataModel = getMockedGuests("add").get(0);
		guestDataModel.setCity(null);
		lenient().doReturn(guestDataModel).when(service).saveGuest(ArgumentMatchers.eq(guestDataModel));
		mockMvc.perform(MockMvcRequestBuilders.post("/guestservice/signup")
				.content(asJsonString(guestDataModel)).contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isForbidden()).andDo(MockMvcResultHandlers.print());
	}

	private List<GuestDataModel> getMockedGuests(String methodType) throws ParseException {
		// 1. Guest
		GuestDataModel guestDataModel1 = new GuestDataModel();
		guestDataModel1.setHouseNumber("1");
		guestDataModel1.setStreet("Nagar Road");
		guestDataModel1.setCity("Indore");
		guestDataModel1.setState("MP");
		guestDataModel1.setZipcode(422323);
		guestDataModel1.setCountry("India");
		guestDataModel1.setFirstName("Amit");
		guestDataModel1.setMiddleName("Kumar");
		guestDataModel1.setLastName("Jain");
		guestDataModel1.setGender('M');
		guestDataModel1.setEmail("abc@xyz.com");
		guestDataModel1.setPassword("12345");
		guestDataModel1.setPhoneNumber(12121212L);
		guestDataModel1.setUserName("abc123");

		// 2. Guest
		GuestDataModel guestDataModel2 = new GuestDataModel();
		guestDataModel2.setHouseNumber("1");
		guestDataModel2.setStreet("Nagar Road");
		guestDataModel2.setCity("Indore");
		guestDataModel2.setState("MP");
		guestDataModel2.setZipcode(422323);
		guestDataModel2.setCountry("India");
		guestDataModel2.setFirstName("Amit");
		guestDataModel2.setMiddleName("Kumar");
		guestDataModel2.setLastName("Jain");
		guestDataModel2.setGender('M');
		guestDataModel2.setEmail("abc@xyz.com");
		guestDataModel2.setPassword("12345");
		guestDataModel2.setPhoneNumber(12121212L);
		guestDataModel2.setUserName("abc123");

		switch (methodType) {
		case "getById":
			return Arrays.asList(guestDataModel1);
		case "add":
			return Arrays.asList(guestDataModel1);
		default:
			return Arrays.asList(guestDataModel1, guestDataModel2);
		}
	}

	static String asJsonString(final Object obj) throws JsonProcessingException {
		return new ObjectMapper().writeValueAsString(obj);
	}
}
