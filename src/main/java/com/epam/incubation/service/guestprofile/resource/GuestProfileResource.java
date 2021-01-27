package com.epam.incubation.service.guestprofile.resource;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;

import com.epam.incubation.service.guestprofile.datamodel.GuestDataModel;
import com.epam.incubation.service.guestprofile.datamodel.UserReservationData;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

/**
 * Controller APIs to make, update and retrieve guest information.
 */
@Api(value = "Guest Profile Service")
public interface GuestProfileResource {

	/**
     * @param GuestDataModel , Guest information needs to create new guest.
     * @return GuestDataModel, holds information of guest.
     */
	@ApiOperation(value = "Add Guest")
	public ResponseEntity<GuestDataModel> add(
			@ApiParam(value = "Guest object store in database table", required = true) GuestDataModel guest);

	/**
     * @param id , guest ID to fetch information.
     * @return GuestDataModel, holds information of guest.
     * @throws RecordNotRecordNotFoundException
     */
	@ApiOperation(value = "Get Guest Details by Id")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Successfully retrieved"),
			@ApiResponse(code = 404, message = "The resource you were trying to reach is not found") })
	public GuestDataModel getGuestById(Integer id);

	/**
     * @param id , guest ID to disable the guest.
     * @return GuestDataModel, holds information of guest.
     * @throws RecordNotRecordNotFoundException
     */
	@ApiOperation(value = "Disable Guest")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Successfully disabled"),
			@ApiResponse(code = 404, message = "The resource you were trying to reach is not found") })
	public ResponseEntity<GuestDataModel> disableGuest( @PathVariable(value = "id") Integer id);
	
	/**
     * @param id , guest ID to fetch the history of guest.
     * @return List of UserReservationData, holds history of guest.
     * @throws RecordNotRecordNotFoundException
     */
	@ApiOperation(value = "Guest History")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Successfully retrieved"),
			@ApiResponse(code = 404, message = "The resource you were trying to reach is not found") })
	public List<UserReservationData> getGuestHistory(@PathVariable(value = "id") Integer id) throws Exception;

}
