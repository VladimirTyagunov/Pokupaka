package com.pokupaka.backend.data.entity;


import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "orders")
public class Order extends AbstractEntity {

    @Column(name = "status")
    private Status status;

    @ManyToOne()
    @JoinColumn(name = "deal_id")
    private Deal deal;

    @ManyToOne()
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne()
    @JoinColumn(name = "product_id")
    private Product product;

    @Min(1)
    @Column(name = "quantity")
    private int quantity = 1;

    @Column(name = "total_price")
    private double totalPrice;

    public Order() {
    }

    public Order(Status status, Deal deal, User user, Product product, int quantity) {
        this.status = status;
        this.deal = deal;
        this.user = user;
        this.product = product;
        this.quantity = quantity;
//        this.totalPrice = this.quantity * Double.valueOf(this.product.getPrice());

        this.totalPrice = quantity * Double.valueOf(product.getPrice());
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
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

    public double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(double totalPrice) {
        this.totalPrice = totalPrice;
    }
}
