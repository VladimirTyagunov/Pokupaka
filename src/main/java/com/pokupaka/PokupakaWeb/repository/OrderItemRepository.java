package com.pokupaka.PokupakaWeb.repository;

import com.pokupaka.PokupakaWeb.domain.OrderItem;
import org.springframework.data.repository.CrudRepository;

public interface OrderItemRepository extends CrudRepository<OrderItem,Long> {
}
