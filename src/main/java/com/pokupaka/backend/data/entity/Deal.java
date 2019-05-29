package com.pokupaka.backend.data.entity;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.util.Set;

@Entity
@Table(name = "deal")
public class Deal extends AbstractEntity {

    @Column(name = "name")
    @Size(max = 255)
    private String name;

    //@NotEmpty
    private Status status;

    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category;

    @Column(name = "total_value")
    private double totalValue;

    @Column(name = "min_value")
    private double minValue;


    public Deal() {
    }

    public Deal(@Size(max = 255) String name, Status status, Category category) {
        this.name = name;
        this.status = status;
        this.category = category;
        this.minValue = 10000;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

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
}
