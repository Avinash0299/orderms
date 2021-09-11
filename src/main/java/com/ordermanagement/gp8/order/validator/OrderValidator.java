package com.ordermanagement.gp8.order.validator;

import com.ordermanagement.gp8.order.dto.CartDTO;

import com.ordermanagement.gp8.order.dto.ProductDTO;
import com.ordermanagement.gp8.order.exception.OrderException;

public class OrderValidator {
	
	
	public static void validateStock(CartDTO cart, ProductDTO product) throws OrderException {
				
		//Check if the required quantity of product is available in the stock
		if(!validateStock(product.getStock(),cart.getQuantity()))
			throw new OrderException("Insufficient stock");	
	}
	
	private static boolean validateStock(Integer stock, Integer quantity) {		
		return stock>=quantity;		
	}
}
