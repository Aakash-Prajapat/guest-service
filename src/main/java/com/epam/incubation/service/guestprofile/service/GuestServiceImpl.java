package com.epam.incubation.service.guestprofile.service;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;

import com.epam.incubation.service.guestprofile.datamodel.GuestDataModel;
import com.epam.incubation.service.guestprofile.datamodel.UserReservationData;
import com.epam.incubation.service.guestprofile.datamodel.UserReservationDataResponse;
import com.epam.incubation.service.guestprofile.exception.GlobalException;
import com.epam.incubation.service.guestprofile.exception.RecordNotFoundException;
import com.epam.incubation.service.guestprofile.facade.ReservationServiceProxy;
import com.epam.incubation.service.guestprofile.model.Address;
import com.epam.incubation.service.guestprofile.model.Guest;
import com.epam.incubation.service.guestprofile.model.Name;
import com.epam.incubation.service.guestprofile.repository.GuestRepository;

import brave.sampler.Sampler;

/**
 * Service layer to make, update and retrieve guest information.
 */
@Service
public class GuestServiceImpl implements GuestService {

	private final Logger logger = LoggerFactory.getLogger(GuestServiceImpl.class);
	
	@Autowired
	private GuestRepository guestRepository;

	@Autowired
	ReservationServiceProxy reservationServiceProxy;

	/**
	 * Responsible to return the guest by Id.
	 * 
	 * @param guestId , Id to which guest information get fetched.
	 * @return GuestDataModel, Holds guest information.
	 * @throws RecordNotFoundException
	 */
	@Override
	public GuestDataModel guestById(Integer id) {
		logger.info("Fetching the guest by id");
		Optional<Guest> guest = guestRepository.findById(id);
		if (guest.isPresent())
			return convertEntityToDataModel(guest.get());
		else
			throw new RecordNotFoundException("Guest Not found with " + id);
	}

	/**
	 * Responsible to disable the guest by Id.
	 * 
	 * @param guestId , Id to which guest information get fetched and disabled.
	 * @return GuestDataModel, Holds updated guest information.
	 * @throws RecordNotFoundException
	 */
	@Override
	public GuestDataModel disableGuest(Integer id) {
		logger.info("Disable the guest by id");
		Optional<Guest> guest = guestRepository.findById(id);
		if (guest.isPresent()) {
			Guest guestUpdate = guest.get();
			guestUpdate.setActive(false);
			guestUpdate = guestRepository.save(guestUpdate);
			return convertEntityToDataModel(guestUpdate);
		} else
			throw new RecordNotFoundException("Guest Not found with " + id);
	}

	/**
	 * Responsible to save the guest.
	 * 
	 * @param GuestDataModel , holds guest information need to save.
	 * @return GuestDataModel, Holds saved guest information.
	 */
	@Override
	public GuestDataModel saveGuest(GuestDataModel guest) {
		logger.info("Save the guest by id");
		return convertEntityToDataModel(guestRepository.save(convertDTOtoEntity(guest)));
	}

	/**
	 * Responsible to return the guest history.
	 * 
	 * @param guestId , Id to which guest information get fetched.
	 * @return list of UserReservationData, Holds guest history.
	 * @throws RecordNotFoundException
	 */
	@Override
	public List<UserReservationData> guestHistory(Integer id) throws Exception {
		logger.info("Calling reservation service to fetch the guest history");
		UserReservationDataResponse reservations = reservationServiceProxy.getGuestReservationHistory(id);
		if (null == reservations) {
			throw new RecordNotFoundException("Exception while calling the reservaionService");
		}
		if (null == reservations.getError() && !reservations.getReservations().isEmpty()) {
			return reservations.getReservations();
		} else {
			if (reservations.getError().getStatus().value() == 404)
				throw new RecordNotFoundException("Reservations not present with id " + id);
			else
				throw new GlobalException("Exception while retriving the guestHistory");
		}
	}

	private Guest convertDTOtoEntity(GuestDataModel guestDataModel) {
		Guest guest = new Guest();
		guest.setName(
				new Name(guestDataModel.getFirstName(), guestDataModel.getMiddleName(), guestDataModel.getLastName()));
		guest.setUserName(guestDataModel.getUserName());
		guest.setPassword(guestDataModel.getPassword());
		guest.setAddress(
				new Address(guestDataModel.getHouseNumber(), guestDataModel.getStreet(), guestDataModel.getCity(),
						guestDataModel.getState(), guestDataModel.getZipcode(), guestDataModel.getCountry()));
		guest.setEmail(guestDataModel.getEmail());
		guest.setPhoneNumber(guestDataModel.getPhoneNumber());
		guest.setGender(guestDataModel.getGender());
		guest.setActive(true);
		guest.setRole("STANDARD");
		return guest;
	}

	private GuestDataModel convertEntityToDataModel(Guest guest) {
		return new GuestDataModel(guest);
	}
	
	@Bean
	public Sampler defaultSampler() {
		return Sampler.ALWAYS_SAMPLE;
	}

}
