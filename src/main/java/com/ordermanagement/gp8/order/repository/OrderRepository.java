package com.ordermanagement.gp8.order.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import com.ordermanagement.gp8.order.entity.Order;

public interface OrderRepository extends CrudRepository<Order, String>{

	public List<Order> findByBuyerId(String buyerId);

	
	public Order findByOrderId(String orderId);
	

}
