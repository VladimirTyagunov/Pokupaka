package com.pokupaka.PokupakaWeb.domain;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Set;

@Entity
@Table(name = "deal")
public class Deal {
    @Id
    @GeneratedValue
    private Long id;

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
