package com.pokupaka.backend.repositories;

import com.pokupaka.backend.data.entity.Category;
import com.pokupaka.backend.data.entity.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Long> {

    List<Product> findByNameContaining(String name);

    Page<Product> findBy(Pageable page);

    Page<Product> findByNameLikeIgnoreCase(String name, Pageable page);

    int countByNameLikeIgnoreCase(String name);

    List<Product> findByCategory(Category category);
}