package com.epam.incubation.service.guestprofile.datamodel;

import java.util.Date;

public class InventoryDataModel {
	private Date stayDate;
	private int quantity;
	private Double price;

	public Date getStayDate() {
		return stayDate;
	}

	public void setStayDate(Date stayDate) {
		this.stayDate = stayDate;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	public Double getPrice() {
		return price;
	}

	public void setPrice(Double price) {
		this.price = price;
	}
}
