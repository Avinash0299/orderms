package com.ordermanagement.gp8.order.repository;

import org.springframework.data.repository.CrudRepository;

import com.ordermanagement.gp8.order.entity.ProductsOrdered;
import com.ordermanagement.gp8.order.utility.Orderpk;

public interface ProductsOrderedRepository extends CrudRepository<ProductsOrdered, Orderpk>{

}
