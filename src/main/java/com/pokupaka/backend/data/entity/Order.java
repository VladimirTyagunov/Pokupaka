package com.pokupaka.backend.data.entity;


import javax.persistence.*;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "orders")
public class Order extends AbstractEntity {

    @Column(name = "status")
    private Status status;

    @Column(name = "name")
    @Size(max = 255)
    private String name;

    @ManyToOne
    @JoinColumn(name = "deal_id")
    private Deal deal;

    //@OneToMany(mappedBy = "order", cascade=CascadeType.ALL, fetch = FetchType.LAZY)
    //private List<OrderItem> orderItems = new ArrayList<>();

    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category;

    @Column(name = "quantity")
    private int quantity;

    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;

    public Order() {
    }

    public Order(Status status, Deal deal, Product product, int quantity) {
        this.status = status;
        this.deal = deal;
        this.product = product;
        this.quantity = quantity;
    }

    /*
    public Order(Status status, Deal deal, Category category) {
        this.status = status;
        this.deal = deal;
        this.category = category;
    }
    */


   /* public Order(Status status, Deal deal, Category category,List<OrderItem> orderItems) {
        this.status = status;
        this.deal = deal;
        this.category = category;
        this.orderItems.addAll(orderItems);
    }*/

  /*  public void addOrderItem(OrderItem orderItem) {
        orderItem.setOrder(this);
        orderItems.add(orderItem);
    }*/

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }
}
