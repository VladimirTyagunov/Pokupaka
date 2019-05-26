package com.pokupaka.backend.data.entity;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "deal")
public class Deal extends AbstractEntity {

    @OneToMany(cascade = CascadeType.ALL,
            mappedBy = "deal", orphanRemoval = true)
    private Set<Order> list;

    private String status;

    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category;

    public Deal() {
    }
}
