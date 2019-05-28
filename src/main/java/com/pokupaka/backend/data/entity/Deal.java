package com.pokupaka.backend.data.entity;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.util.Set;

@Entity
@Table(name = "deal")
public class Deal extends AbstractEntity {

    @OneToMany(cascade = CascadeType.ALL,
            mappedBy = "deal", orphanRemoval = true)
    private Set<Order> list;

    @Column(name = "name")
    @Size(max = 255)
    private String name;

    private String status;

    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category;

    public Deal() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getStatus() {
        return status;
    }

}
