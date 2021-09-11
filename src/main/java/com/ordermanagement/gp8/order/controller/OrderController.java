package com.ordermanagement.gp8.order.controller;

import java.util.ArrayList;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.server.ResponseStatusException;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ordermanagement.gp8.order.dto.CartDTO;
import com.ordermanagement.gp8.order.dto.OrderDTO;
import com.ordermanagement.gp8.order.dto.OrderPlacedDTO;
import com.ordermanagement.gp8.order.dto.ProductDTO;
import com.ordermanagement.gp8.order.service.OrderService;

@RestController
@CrossOrigin
@RequestMapping(value = "api")
public class OrderController {
	
	@Autowired
	 OrderService orderService;
	
	@Autowired
	DiscoveryClient client;
	
	@Value("${user.uri}")
	String userUri;
	
	@Value("${product.uri}")
	String productUri;
	
	
	
//add to cart
    @PostMapping(value = "/addCart/{buyerId}/{prodId}/{quantity}")
		public ResponseEntity<String> addToCart(@PathVariable String buyerId, @PathVariable String prodId,@PathVariable Integer quantity){
			try {
			String success = new RestTemplate().postForObject(userUri+"api/buyer/addtocart/"+buyerId+"/"+prodId+"/"+quantity, null, String.class);
	        return new ResponseEntity<>(success,HttpStatus.ACCEPTED);
		}
		catch(Exception exception)
		{
		String error = "error occured";
		if(exception.getMessage().equals("404 null"))
		{
		error = "product not found";
				}
		return new ResponseEntity<>(error,HttpStatus.UNAUTHORIZED);
			}		
		}
		
		
//remove from cart
		@PostMapping(value = "/removeCart/{buyerId}/{prodId}")
		public ResponseEntity<String> removeFromCart(@PathVariable String buyerId, @PathVariable String prodId) {
	       try {
	        String success = new RestTemplate().postForObject(userUri + "api/buyer/removecart/" + buyerId + "/" + prodId, null, String.class);
	       return new ResponseEntity<>(success, HttpStatus.ACCEPTED);
			}
	       catch (Exception exception) {
		  String error = "error occured";
		  if (exception.getMessage().equals("404 null")) {
			error = "product not found to remove";
		}
		return new ResponseEntity<>(error, HttpStatus.UNAUTHORIZED);
			}
		}	
		
//get order by orderid	
		@GetMapping(value = "/getOrder/{orderId}")
		public ResponseEntity<OrderDTO> viewsOrderByOrderId(@PathVariable String orderId){		
			try {
				OrderDTO order = orderService.getOrderbyOrderId(orderId);
				return new ResponseEntity<>(order,HttpStatus.OK);
			}
			catch(Exception exception)
			{
				throw new ResponseStatusException(HttpStatus.NOT_FOUND, exception.getMessage(), exception);
			}		
		}	
		
//get order by buyerid	
	@GetMapping(value = "/getOrder/{buyerId}")
		 public ResponseEntity<List<OrderDTO>> getOrderByBuyerId(@PathVariable String buyerId){		
		    try {
			List<OrderDTO> orders = orderService.getOrderBybuyerId(buyerId);
			 return new ResponseEntity<>(orders,HttpStatus.OK);
			   }
				catch(Exception exception)
				{
					throw new ResponseStatusException(HttpStatus.NOT_FOUND, exception.getMessage(), exception);
				}		
			}	
	
// get all order	
	@GetMapping(value = "/get/allOrder")
	 public ResponseEntity<List<OrderDTO>> viewAllOrder(){		
		try {
			List<OrderDTO> Or = orderService.getAllOrders();
			return new ResponseEntity<>(Or,HttpStatus.OK);
			}
			catch(Exception exception)
			{
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, exception.getMessage(), exception);
			}		
		}
	
//place order	
@PostMapping(value = "/placeOrder/{buyerId}")
 public ResponseEntity<String> placeOrder(@PathVariable String buyerId, @RequestBody OrderDTO orderDTO){
		try {
		ObjectMapper objmap = new ObjectMapper();
		List<ProductDTO> prodList = new ArrayList<>();
		List<CartDTO> cartList = objmap.convertValue(
		new RestTemplate().getForObject(userUri+"api/buyer/getcart/" + buyerId, List.class), 
	    new TypeReference<List<CartDTO>>(){}
	);
		cartList.forEach(item ->{
		ProductDTO product = new RestTemplate().getForObject(productUri+"api/product/ById/" +item.getProdId(),ProductDTO.class) ; //getByProdId/{productId}
		System.out.println(product.getDescription());
		prodList.add(product);
	}
	);
		OrderPlacedDTO Placed = orderService.place_Order(prodList,cartList,orderDTO);
		cartList.forEach(item1 ->{
		new RestTemplate().getForObject(productUri+"api/product/updateStock/" +item1.getProdId()+"/"+item1.getQuantity(), boolean.class) ;
		new RestTemplate().postForObject(userUri+"api/buyer/removecart/"+buyerId+"/"+item1.getProdId(),null, String.class);
	}
		);			
		new RestTemplate().getForObject(userUri+"api/updateRewardPoints/"+buyerId+"/"+Placed.getRewardPoints() , String.class);
		return new ResponseEntity<>(Placed.getOrderId(),HttpStatus.ACCEPTED);
	}
		catch(Exception exception)
		{
		String errorMsg = " error occured";
		if(exception.getMessage().equals("404 null"))
		{
			errorMsg = "Error while placing the order";
		}
		return new ResponseEntity<>(errorMsg,HttpStatus.UNAUTHORIZED);
	}		
		
	}
	
//reorder	
	@PostMapping(value = "/reorder/{buyerId}/{orderId}")
	public ResponseEntity<String> reOrder(@PathVariable String buyerId, @PathVariable String orderId){
		try {
		String success = orderService.reOrder(buyerId,orderId);
		return new ResponseEntity<>(success,HttpStatus.ACCEPTED);
		}
		catch(Exception exception)
		{
		return new ResponseEntity<>(exception.getMessage(),HttpStatus.UNAUTHORIZED);
		}		
	}
	
}

