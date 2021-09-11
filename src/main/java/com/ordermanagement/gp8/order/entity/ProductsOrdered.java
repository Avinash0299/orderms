package com.ordermanagement.gp8.order.entity;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.ordermanagement.gp8.order.utility.Orderpk;

@Entity
@Table(name = "products_ordered")
public class ProductsOrdered {
	
	@EmbeddedId
	 Orderpk prod_orderedId;
	
	 String sellerId;	
	 Integer quantity;
	
	
	public Orderpk getProd_orderedId() {
		return prod_orderedId;
	}
	public void setProd_orderedId(Orderpk prod_orderedId) {
		this.prod_orderedId = prod_orderedId;
	}
	public String getSellerId() {
		return sellerId;
	}
	public void setSellerId(String sellerId) {
		this.sellerId = sellerId;
	}
	public Integer getQuantity() {
		return quantity;
	}
	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}
	
	
}
