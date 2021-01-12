package com.epam.incubation.service.guestprofile.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.epam.incubation.service.guestprofile.datamodel.GuestDataModel;
import com.epam.incubation.service.guestprofile.exception.RecordNotFoundException;
import com.epam.incubation.service.guestprofile.model.Address;
import com.epam.incubation.service.guestprofile.model.Guest;
import com.epam.incubation.service.guestprofile.model.Name;
import com.epam.incubation.service.guestprofile.repository.GuestRepository;

@Service
public class GuestServiceImpl implements GuestService {

	@Autowired
	private GuestRepository guestRepository;
	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;

	public Optional<GuestDataModel> guestById(Integer id) {
		Optional<GuestDataModel> guestDataModel = Optional.ofNullable(null);
		Optional<Guest> guest = guestRepository.findById(id);
		if (guest.isPresent()) {
			guestDataModel = Optional.of(convertEntityToDataModel(guest.get()));
		}
		return guestDataModel;
	}

	public GuestDataModel disableGuest(Integer id) {
		Optional<GuestDataModel> guestDataModel;
		Optional<Guest> guest = guestRepository.findById(id);
		if (guest.isPresent()) {
			Guest guestUpdate = guest.get();
			guestUpdate.setActive(false);
			guestUpdate = guestRepository.save(guestUpdate);
			guestDataModel = Optional.of(convertEntityToDataModel(guestUpdate));
		} else
			throw new RecordNotFoundException("Guest Not found with " + id);
		return guestDataModel.get();
	}

	public GuestDataModel saveGuest(GuestDataModel guest) {
		return convertEntityToDataModel(guestRepository.save(convertDTOtoEntity(guest)));
	}

	private Guest convertDTOtoEntity(GuestDataModel guestDataModel) {
		Guest guest = new Guest();
		guest.setName(
				new Name(guestDataModel.getFirstName(), guestDataModel.getMiddleName(), guestDataModel.getLastName()));
		guest.setUserName(guestDataModel.getUserName());
		guest.setPassword(bCryptPasswordEncoder.encode(guestDataModel.getPassword()));
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

}
