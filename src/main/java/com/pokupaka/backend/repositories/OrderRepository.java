package com.pokupaka.backend.repositories;

import com.pokupaka.backend.data.entity.Order;
import org.springframework.data.repository.CrudRepository;

public interface OrderRepository extends CrudRepository<Order,Long> {

}
