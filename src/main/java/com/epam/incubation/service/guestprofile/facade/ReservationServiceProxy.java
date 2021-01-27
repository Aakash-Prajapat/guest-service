package com.epam.incubation.service.guestprofile.facade;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.epam.incubation.service.guestprofile.datamodel.UserReservationDataResponse;

@FeignClient(name="gatewayserver")
public interface ReservationServiceProxy {
	
	@GetMapping("/reservationservice/reservationservice/guestreservationshistory/{id}")
	public UserReservationDataResponse getGuestReservationHistory(@PathVariable(name = "id") Integer guestId);

}
