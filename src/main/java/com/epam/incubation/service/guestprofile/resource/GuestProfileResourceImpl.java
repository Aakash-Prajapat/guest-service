package com.epam.incubation.service.guestprofile.resource;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.epam.incubation.service.guestprofile.datamodel.GuestDataModel;
import com.epam.incubation.service.guestprofile.exception.RecordNotFoundException;
import com.epam.incubation.service.guestprofile.service.GuestServiceImpl;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@RestController
@RequestMapping("/api/v1")
@Api(value = "Guest Profile Service")
public class GuestProfileResourceImpl implements GuestProfileResource {

	@Autowired
	GuestServiceImpl guestService;

	@GetMapping("/guestservice/{id}")
	@ApiOperation(value = "Get Guest Details by Id")
	@ApiResponses(value = {
		    @ApiResponse(code = 200, message = "Successfully retrieved"),
		    @ApiResponse(code = 404, message = "The resource you were trying to reach is not found")
		})
	public GuestDataModel getGuestById(@PathVariable(value = "id") Integer id) {
		return guestService.guestById(id).orElseThrow(() -> new RecordNotFoundException("Guest Not found by " + id));
	}

	@PostMapping("/guestservice")
	@ApiOperation(value = "Add Guest")
	public ResponseEntity<GuestDataModel> add(
			@ApiParam(value = "Guest object store in database table", required = true)
			@Valid @RequestBody GuestDataModel guest) {
		GuestDataModel guest1 = guestService.saveGuest(guest);
		return new ResponseEntity<>(guest1, HttpStatus.OK);
	}

}
