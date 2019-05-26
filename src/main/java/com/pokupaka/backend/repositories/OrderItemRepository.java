package com.pokupaka.backend.repositories;

import com.pokupaka.backend.data.entity.OrderItem;
import org.springframework.data.repository.CrudRepository;

public interface OrderItemRepository extends CrudRepository<OrderItem,Long> {
}
