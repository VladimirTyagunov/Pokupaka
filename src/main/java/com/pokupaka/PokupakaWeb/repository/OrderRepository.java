package com.pokupaka.PokupakaWeb.repository;

import com.pokupaka.PokupakaWeb.domain.Order;
import org.springframework.data.repository.CrudRepository;

public interface OrderRepository extends CrudRepository<Order,Long> {

}
