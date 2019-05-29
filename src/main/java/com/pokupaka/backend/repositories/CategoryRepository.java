package com.pokupaka.backend.repositories;

import com.pokupaka.backend.data.entity.Category;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


public interface CategoryRepository  extends JpaRepository<Category, Long> {

        List<Category> findByNameContaining(String name);

        Page<Category> findBy(Pageable page);

        Page<Category> findByNameLikeIgnoreCase(String name, Pageable page);

        int countByNameLikeIgnoreCase(String name);
}
