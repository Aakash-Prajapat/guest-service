package com.epam.incubation.service.guestprofile.resource;

import java.util.List;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.epam.incubation.service.guestprofile.datamodel.GuestDataModel;
import com.epam.incubation.service.guestprofile.datamodel.UserReservationData;
import com.epam.incubation.service.guestprofile.service.GuestServiceImpl;

@RestController
@RequestMapping("/guestservice")
public class GuestProfileResourceImpl implements GuestProfileResource {
	
	private final Logger logger = LoggerFactory.getLogger(GuestProfileResourceImpl.class);

	@Autowired
	GuestServiceImpl guestService;
	
	@GetMapping("/{id}")
	@PreAuthorize("hasRole('GUEST')")
	public GuestDataModel getGuestById(@PathVariable(value = "id") Integer id) {
		logger.info("Calling service to Fetch guest by id");
		return guestService.guestById(id);
	}
	
	@GetMapping("/guesthistory/{id}")
	@PreAuthorize("hasRole('GUEST')")
	public List<UserReservationData> getGuestHistory(@PathVariable(value = "id") Integer id) throws Exception {
		logger.info("Calling service to Fetch guest history by id");
		return guestService.guestHistory(id);
	}

	@PostMapping("/signup")
	@PreAuthorize("hasRole('GUEST')")
	public ResponseEntity<GuestDataModel> add(@Valid @RequestBody GuestDataModel guest) {
		logger.info("Calling service to save guest");
		GuestDataModel guestDataModel = guestService.saveGuest(guest);
		return new ResponseEntity<>(guestDataModel, HttpStatus.CREATED);
	}

	@PutMapping("/disable/{id}")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<GuestDataModel> disableGuest(@PathVariable(value = "id") Integer id) {
		logger.info("Calling service to disable guest by id");
		GuestDataModel guestDataModel = guestService.disableGuest(id);
		return new ResponseEntity<>(guestDataModel, HttpStatus.OK);
	}
	
	
}
