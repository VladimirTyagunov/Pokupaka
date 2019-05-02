package com.pokupaka.PokupakaWeb;

import com.pokupaka.PokupakaWeb.domain.*;
import com.pokupaka.PokupakaWeb.repository.CategoryRepository;
import com.pokupaka.PokupakaWeb.repository.OrderItemRepository;
import com.pokupaka.PokupakaWeb.repository.OrderRepository;
import com.pokupaka.PokupakaWeb.repository.ProductRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.Arrays;


@SpringBootApplication
public class PokupakaWebApplication {

	private static final Logger log = LoggerFactory.getLogger(PokupakaWebApplication.class);

	public static void main(String[] args) {
		SpringApplication.run(PokupakaWebApplication.class, args);
	}

	@Bean
	public CommandLineRunner demo(CategoryRepository categoryRepository,
								  ProductRepository productRepository,
								  OrderItemRepository orderItemRepository,
								  OrderRepository orderRepository) {
		return (args) -> {

			// Filling the DB with some data

			Category stationery = new Category("Stationery","Сategory for some stationery stuff");
			Category householdGoods = new Category("Household Goods","Сategory for household products");
			Category furniture = new Category("Furniture","Сategory for different elemenst of furniture");

			Product pen = new Product("Pen",5.0,"Just a pen", stationery);
			Product pencil = new Product("Pencil",4.5,"Just a pencil", stationery);
			Product ruler = new Product("Ruler",3.3,"Just a ruler", stationery);

			Product mop = new Product("Mop",15.0,"Mop description", stationery);
			Product vacuumСleaner = new Product("Vacuum cleaner",150.0,"Vacuum cleaner description", furniture);
			Product cup = new Product("Cup",10.0,"Cup description", furniture);

			Product table = new Product("Table",100.0,"Table description", furniture);
			Product chair = new Product("Chair",50.0,"Chair description", furniture);
			Product sofa = new Product("Sofa",250.0,"Sofa description", furniture);

			Order order1 = new Order(Status.NOT_STARTED,null,stationery);

			OrderItem orderItem1 = new OrderItem(Status.NOT_STARTED,order1,pen,3);
			OrderItem orderItem2 = new OrderItem(Status.NOT_STARTED,order1,pen,5);

			order1.addOrderItem(orderItem1);
			order1.addOrderItem(orderItem2);


			// saving the data
			categoryRepository.save(stationery);
			categoryRepository.save(householdGoods);
			categoryRepository.save(furniture);

			productRepository.save(pen);
			productRepository.save(pencil);
			productRepository.save(ruler);
			productRepository.save(mop);
			productRepository.save(vacuumСleaner);
			productRepository.save(cup);
			productRepository.save(table);
			productRepository.save(chair);
			productRepository.save(sofa);

			//orderItemRepository.save(orderItem1);
			//orderItemRepository.save(orderItem2);

			orderRepository.save(order1);



			// fetch all categories
			log.info("Categories found with findAll():");
			log.info("-------------------------------");
			for (Category category: categoryRepository.findAll()) {
				log.info(category.toString());
			}
			log.info("");

			// fetch an individual category by ID
			categoryRepository.findById(1L)
					.ifPresent(category -> {
						log.info("Category found with findById(1L):");
						log.info("--------------------------------");
						log.info(category.toString());
						log.info("");
					});

			// fetch all products
			log.info("Products found with findAll():");
			log.info("-------------------------------");
			for (Product product: productRepository.findAll()) {
				log.info(product.toString());
			}
			log.info("");

			// fetch an individual product by ID
			productRepository.findById(3L)
					.ifPresent(product -> {
						log.info("Product found with findById(1L):");
						log.info("--------------------------------");
						log.info(product.toString());
						log.info("");
					});

		};
	}
}
