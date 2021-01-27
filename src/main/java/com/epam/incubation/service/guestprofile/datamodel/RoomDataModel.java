package com.epam.incubation.service.guestprofile.datamodel;

import java.util.List;

public class RoomDataModel {
	private String name;
	private String description;
	private boolean status;
	private List<InventoryDataModel> inventories;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public boolean isStatus() {
		return status;
	}

	public void setStatus(boolean status) {
		this.status = status;
	}

	public List<InventoryDataModel> getInventories() {
		return inventories;
	}

	public void setInventories(List<InventoryDataModel> inventories) {
		this.inventories = inventories;
	}
}
