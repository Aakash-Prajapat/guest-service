package com.epam.incubation.service.guestprofile.stubbedIntegrate;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.AutoConfigureJsonTesters;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cloud.contract.stubrunner.spring.AutoConfigureStubRunner;
import org.springframework.cloud.contract.stubrunner.spring.StubRunnerProperties;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;

import com.epam.incubation.service.guestprofile.GuestProfileServiceApplication;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = GuestProfileServiceApplication.class,webEnvironment = SpringBootTest.WebEnvironment.MOCK)
//@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
@AutoConfigureJsonTesters
@AutoConfigureStubRunner(stubsMode = StubRunnerProperties.StubsMode.LOCAL, ids = "com.epam.incubation:reservation-service:+:stubs:8085")
class TestStubbedServicesReservation {

	Logger LOGGER = LoggerFactory.getLogger(TestStubbedServicesReservation.class);

	@Autowired
	private MockMvc mockMvc;

	@Test
	void testGetGuest() throws Exception {
		mockMvc.perform(MockMvcRequestBuilders.get("/guestservice/guesthistory/1")
				.with(user("Guest").password("test").roles("GUEST")).contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk()).andDo(MockMvcResultHandlers.print());
		// .andExpect(jsonPath("$[0].reservationId").value(1));

	}

}
