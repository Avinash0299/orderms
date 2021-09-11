package com.ordermanagement.gp8.order.dto;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

public class ProductDTO {
	
	 String prodId;
	@NotNull(message = "Please provide product name")
//	@Pattern(regexp = "[A-Za-z]+( [A-Za-z]+)*", message = "Name should contain only alphabets and space")
	String productName;
	@NotNull(message = "Please provide price of the product")
	@Min(200)
	Float price;
	@Min(10)
	Integer stock;
	@Pattern(regexp = "([^\\s]+(\\.(?i)(jpeg|png))$)", message = "Image should be in jpeg or png format only")
	String image;
	String sellerId;
	@Size(min=1,max=500)
	String description;
	String category;
    String subCategory;
	Float productRating;
	
	
	public String getSellerId() {
		return sellerId;
	}
	public void setSellerId(String sellerId) {
		this.sellerId = sellerId;
	}
	public String getProdId() {
		return prodId;
	}
	public void setProdId(String prodId) {
		this.prodId = prodId;
	}
	public String getProductName() {
		return productName;
	}
	public void setProductName(String productName) {
		this.productName = productName;
	}
	public Float getPrice() {
		return price;
	}
	public void setPrice(Float price) {
		this.price = price;
	}
	public Integer getStock() {
		return stock;
	}
	public void setStock(Integer stock) {
		this.stock = stock;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getImage() {
		return image;
	}
	public void setImage(String image) {
		this.image = image;
	}
	public String getCategory() {
		return category;
	}
	public void setCategory(String category) {
		this.category = category;
	}
	public String getSubCategory() {
		return subCategory;
	}
	public void setSubCategory(String subCategory) {
		this.subCategory = subCategory;
	}
	public Float getProductRating() {
		return productRating;
	}
	public void setProductRating(Float productRating) {
		this.productRating = productRating;
	}
	

}
