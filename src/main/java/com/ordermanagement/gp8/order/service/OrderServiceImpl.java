package com.ordermanagement.gp8.order.service;

import java.time.LocalDate;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ordermanagement.gp8.order.dto.CartDTO;
import com.ordermanagement.gp8.order.dto.OrderDTO;
import com.ordermanagement.gp8.order.dto.OrderPlacedDTO;
import com.ordermanagement.gp8.order.dto.ProductDTO;
import com.ordermanagement.gp8.order.entity.Order;
import com.ordermanagement.gp8.order.entity.ProductsOrdered;
import com.ordermanagement.gp8.order.exception.OrderException;
import com.ordermanagement.gp8.order.repository.OrderRepository;
import com.ordermanagement.gp8.order.repository.ProductsOrderedRepository;
import com.ordermanagement.gp8.order.utility.Orderpk;
import com.ordermanagement.gp8.order.utility.OrderStatus;
import com.ordermanagement.gp8.order.validator.OrderValidator;


@Service(value = "orderService")
@Transactional
public class OrderServiceImpl implements OrderService {
	
	 static int o;
	
	@Autowired
	 OrderRepository orderRepo;
	
	@Autowired
    ProductsOrderedRepository prodOrderedRepo;
	
	static {
		o=100;
	}
	
//get order by orderid
		@Override
		public OrderDTO getOrderbyOrderId(String orderId) throws OrderException {
			Order order = orderRepo.findByOrderId(orderId);
			if(order == null)
				throw new OrderException("Service.PRODUCT_NOT_AVAILABLE");
			OrderDTO orddto = new OrderDTO();
			orddto.setOrderId(order.getOrderId());
			orddto.setBuyerId(order.getBuyerId());
			orddto.setAmount(order.getAmount());
			orddto.setAddress(order.getAddress());
			orddto.setDate(order.getDate());
			orddto.setStatus(order.getStatus());		
			return orddto;
		}

// get all orders placed
	@Override
	public List<OrderDTO> getAllOrders() throws OrderException {
		Iterable<Order> order = orderRepo.findAll();
		List<OrderDTO> List = new ArrayList<>();
		order.forEach(order1 -> {
			OrderDTO orderDTO = new OrderDTO();
			orderDTO .setOrderId(order1.getOrderId());
			orderDTO .setBuyerId(order1.getBuyerId());
			orderDTO .setAmount(order1.getAmount());
			orderDTO .setAddress(order1.getAddress());
			orderDTO .setDate(order1.getDate());
			orderDTO .setStatus(order1.getStatus());
			List.add(orderDTO );			
		});
		if(List.isEmpty()) throw new OrderException("orders is empty");
		return List;
	}
	
//get order by buyerid
	@Override
	public List<OrderDTO> getOrderBybuyerId(String buyerId) throws OrderException {
		List<Order> ord = orderRepo.findByBuyerId(buyerId);
	if(ord.isEmpty())throw new OrderException("PRODUCT_NOT_AVAILABLE");
		List<OrderDTO> List =new ArrayList<>();
		ord.forEach(order2->{
		//if(order2 == null)
			//throw new OrderException("PRODUCT_NOT_AVAILABLE");
		
		OrderDTO orderdto = new OrderDTO();
		orderdto.setOrderId(order2.getOrderId());
		orderdto.setBuyerId(order2.getBuyerId());
		orderdto.setAmount(order2.getAmount());
		orderdto.setAddress(order2.getAddress());
		orderdto.setDate(order2.getDate());
		orderdto.setStatus(order2.getStatus());
		List.add(orderdto);
	});
	return List;
}
//place order
	@Override
	public OrderPlacedDTO place_Order(List<ProductDTO> prodList, List<CartDTO> cartList, OrderDTO ordDTO)
	throws OrderException {
		Order ord = new Order();
		String orderid = "O" + o++;
		ord.setOrderId(orderid);
		ord.setAddress(ordDTO.getAddress());
		ord.setBuyerId(cartList.get(0).getBuyerId());
		ord.setDate(LocalDate.now());
		ord.setStatus(OrderStatus.ORDER_PLACED.toString());	
		ord.setAmount(0f);
		List<ProductsOrdered> prod_Ordered = new ArrayList<>();
		for(int i = 0; i<cartList.size();i++) {
		OrderValidator.validateStock(cartList.get(i), prodList.get(i));			
		ord.setAmount(ord.getAmount()+(cartList.get(i).getQuantity()*prodList.get(i).getPrice()));
		ProductsOrdered prod = new ProductsOrdered();
	    prod.setSellerId(prodList.get(i).getSellerId());
		prod.setProd_orderedId(new Orderpk(cartList.get(i).getBuyerId(),prodList.get(i).getProdId()));
		prod.setQuantity(cartList.get(i).getQuantity());
		prod_Ordered.add(prod);				
	}		
		prodOrderedRepo.saveAll(prod_Ordered);
		orderRepo.save(ord);
		OrderPlacedDTO orderPlaced = new OrderPlacedDTO();
		orderPlaced.setBuyerId(ord.getBuyerId());
		orderPlaced.setOrderId(ord.getOrderId());
		Integer rewardPoints = (int) (ord.getAmount()/100);		
		orderPlaced.setRewardPoints(rewardPoints);
		return orderPlaced;
	}

//reorder
	@Override
	public String reOrder(String buyerId, String orderId) throws OrderException {
		Order ord1 = orderRepo.findByOrderId(orderId);
		if(ord1 == null)
			throw new OrderException("Order not placed brfore");
		Order reorder = new Order();
		String orderid = "O" + o++;
		reorder.setOrderId(orderid);
		reorder.setBuyerId(ord1.getBuyerId());
		reorder.setAmount(ord1.getAmount());
		reorder.setAddress(ord1.getAddress());
		reorder.setDate(LocalDate.now());
		reorder.setStatus(ord1.getStatus());
		orderRepo.save(reorder);		
		return "Reorder placed successfully with ordwrId: "+ reorder.getOrderId();
				
	}

}
