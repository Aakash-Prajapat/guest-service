package com.epam.incubation.service.guestprofile.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.epam.incubation.service.guestprofile.datamodel.GuestDataModel;
import com.epam.incubation.service.guestprofile.model.Address;
import com.epam.incubation.service.guestprofile.model.Guest;
import com.epam.incubation.service.guestprofile.model.Name;
import com.epam.incubation.service.guestprofile.repository.GuestReposity;

@Service
public class GuestServiceImpl implements GuestService {

	@Autowired
	GuestReposity guestRepository;

	public Optional<GuestDataModel> guestById(Integer id) {
		Optional<GuestDataModel> guestDataModel = Optional.ofNullable(null);
		Optional<Guest> guest = guestRepository.findById(id);
		if (guest.isPresent()) {
			guestDataModel = Optional.of(convertEntityToDataModel(guest.get()));
		}
		return guestDataModel;
	}

	public GuestDataModel saveGuest(GuestDataModel guest) {
		return convertEntityToDataModel(guestRepository.save(convertDTOtoEntity(guest)));
	}

	private Guest convertDTOtoEntity(GuestDataModel guestDataModel) {
		Guest guest = new Guest();
		guest.setName(
				new Name(guestDataModel.getFirstName(), guestDataModel.getMiddleName(), guestDataModel.getLastName()));
		guest.setAddress(
				new Address(guestDataModel.getHouseNumber(), guestDataModel.getStreet(), guestDataModel.getCity(),
						guestDataModel.getState(), guestDataModel.getZipcode(), guestDataModel.getCountry()));
		guest.setEmail(guestDataModel.getEmail());
		guest.setPhoneNumber(guestDataModel.getPhoneNumber());
		guest.setGender(guestDataModel.getGender());
		return guest;
	}

	private GuestDataModel convertEntityToDataModel(Guest guest) {
		return new GuestDataModel(guest);
	}

}