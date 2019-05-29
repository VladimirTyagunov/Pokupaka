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

    Page<Order> findByIdLikeAndUser(String id,String user, Pageable page);

    int countByIdLike(String id);

    int countByIdLikeAndUser(String id,String user);

    List<Order> findByDealId(Long dealId);

    List<Order> findByUserId(Long dealId);
}
