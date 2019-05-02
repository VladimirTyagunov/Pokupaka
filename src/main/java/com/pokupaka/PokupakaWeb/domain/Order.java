package com.pokupaka.PokupakaWeb.domain;


import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "orders")
public class Order {
    @Id
    @GeneratedValue
    private Long id;

    @Column(name = "status")
    private Status status;

    @ManyToOne
    @JoinColumn(name = "deal_id")
    private Deal deal;

    @OneToMany(mappedBy = "order", cascade=CascadeType.ALL, fetch = FetchType.LAZY)
    private List<OrderItem> orderItems = new ArrayList<>();

    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category;

    public Order() {
    }

    public Order(Status status, Deal deal, Category category) {
        this.status = status;
        this.deal = deal;
        this.category = category;
    }

    public Order(Status status, Deal deal, Category category,List<OrderItem> orderItems) {
        this.status = status;
        this.deal = deal;
        this.category = category;
        this.orderItems.addAll(orderItems);
    }

    public void addOrderItem(OrderItem orderItem) {
        orderItem.setOrder(this);
        orderItems.add(orderItem);
    }
}
