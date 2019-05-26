package com.pokupaka.backend.repositories;

import com.pokupaka.backend.data.entity.Category;
import org.springframework.data.repository.CrudRepository;

public interface CategoryRepository extends CrudRepository<Category,Long> {

}
