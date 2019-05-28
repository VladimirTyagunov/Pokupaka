package com.pokupaka.backend.repositories;

import com.pokupaka.backend.data.entity.Deal;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DealRepository extends JpaRepository<Deal, Long> {

    List<Deal> findByNameContaining(String name);

    Page<Deal> findBy(Pageable page);

    Page<Deal> findByNameLikeIgnoreCase(String name, Pageable page);

    int countByNameLikeIgnoreCase(String name);
}