package com.pokupaka.backend.repositories;

import com.pokupaka.backend.data.entity.Order;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderRepository extends JpaRepository<Order,Long> {

    List<Order> findByIdContaining(String id);

    Page<Order> findBy(Pageable page);

    Page<Order> findByIdLike(String id, Pageable page);

    int countByIdLike(String id);

    List<Order> findByDealId(Long dealId);
}
