package com.ordermanagement.gp8.order.service;

import java.util.List;

import com.ordermanagement.gp8.order.dto.CartDTO;
import com.ordermanagement.gp8.order.dto.OrderDTO;
import com.ordermanagement.gp8.order.dto.OrderPlacedDTO;
import com.ordermanagement.gp8.order.dto.ProductDTO;
import com.ordermanagement.gp8.order.exception.OrderException;

public interface OrderService {
	public OrderPlacedDTO place_Order(List<ProductDTO> productList, List<CartDTO> cartList, OrderDTO order) throws OrderException;
	public OrderDTO getOrderbyOrderId(String orderId) throws OrderException;
	public List<OrderDTO> getOrderBybuyerId(String buyerId)throws OrderException;
   public List<OrderDTO> getAllOrders() throws OrderException;
   public String reOrder(String buyerId, String orderId) throws OrderException;


}
