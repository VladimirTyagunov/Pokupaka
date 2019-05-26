package com.pokupaka.backend.data.entity;

import javax.persistence.*;

@Entity
@Table(name = "order_items")
@SequenceGenerator(name="orderItemsIds", initialValue=1, allocationSize=100) //TODO implement sequences for all tables
public class OrderItem extends AbstractEntity{

    @Enumerated(EnumType.ORDINAL)
    @Column(name = "status",length = 20)
    private Status status;

    @ManyToOne
    @JoinColumn(name = "order_id")
    private Order order;

    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;

    @Column(name = "quantity")
    private int quantity;

    public OrderItem() {
    }

    public OrderItem(Status status, Order order,Product product,int quantity) {
        this.status = status;
        this.order = order;
        this.product = product;
        this.quantity = quantity;
    }

    public void setOrder(Order order) {
        this.order = order;
    }

    @Override
    public String toString() {
        return String.format("Order Item (id = %s). Status : %s",super.getId(),status);
    }
}
