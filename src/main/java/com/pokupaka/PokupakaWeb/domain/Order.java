package com.pokupaka.PokupakaWeb.domain;


import javax.persistence.*;
import java.util.HashMap;
import java.util.Map;

@Entity
@Table(name = "order_table")
public class Order {
    @Id
    @GeneratedValue
    private Long id;


//    @ManyToMany
//    @JoinTable(
//            name="order_products",
//            joinColumns=@JoinColumn(name="order_table_id"),
//            inverseJoinColumns=@JoinColumn(name="product_id"))
//    private Map<Product, Integer> m;

    @Column(name = "status")
    private String status;

    @ManyToOne
    @JoinColumn(name = "deal_id")
    private Deal deal;

    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category;

    public Order() {
    }
}
