package com.pokupaka.PokupakaWeb.repository;

import com.pokupaka.PokupakaWeb.domain.Product;
import org.springframework.data.repository.CrudRepository;

public interface ProductRepository extends CrudRepository<Product,Long> {

}
