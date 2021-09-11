package com.ordermanagement.gp8.order.dto;

import java.time.LocalDate;

import javax.validation.constraints.Max;
import javax.validation.constraints.NotNull;

public class OrderDTO {
	 String orderId;
	 String buyerId;
	 Float amount;
	 LocalDate date;
	@NotNull(message = "Please provide delivery address")
	@Max(value=100,message= "Delivery address should be within 100 charcters")
	 String address;
	 String status;
	public String getOrderId() {
		return orderId;
	}
	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}
	public String getBuyerId() {
		return buyerId;
	}
	public void setBuyerId(String buyerId) {
		this.buyerId = buyerId;
	}
	public Float getAmount() {
		return amount;
	}
	public void setAmount(Float amount) {
		this.amount = amount;
	}
	public LocalDate getDate() {
		return date;
	}
	public void setDate(LocalDate date) {
		this.date = date;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String addrerss) {
		this.address = addrerss;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String string) {
		this.status = string;
	}	
}
