package com.pokupaka.PokupakaWeb.domain;


import javax.persistence.*;
import java.util.HashMap;
import java.util.Map;

@Entity
@Table(name = "ORDER")
public class Order {
    @Id
    @GeneratedValue
    private Long id;

//    @OneToMany
//    @MapKeyJoinColumn(name = "ID")
//    private Map<Product, Integer> m;

    private String status;

    @ManyToOne
    @JoinColumn(name = "ORDER_IDd")
    private Deal deal;

    @ManyToOne
    @JoinColumn(name = "ORDER_IDc")
    private Category category;
}
