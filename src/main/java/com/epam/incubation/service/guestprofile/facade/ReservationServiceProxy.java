package com.epam.incubation.service.guestprofile.facade;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.epam.incubation.service.guestprofile.datamodel.UserReservationDataResponse;
import com.epam.incubation.service.guestprofile.responsemodel.GuestResponseModel;

@FeignClient(name="gatewayserver")
public interface ReservationServiceProxy {
	
	@GetMapping("/reservationservice/reservationservice/guestreservationshistory/{id}")
	public GuestResponseModel<UserReservationDataResponse> getGuestReservationHistory(@PathVariable(name = "id") Integer guestId);

}
