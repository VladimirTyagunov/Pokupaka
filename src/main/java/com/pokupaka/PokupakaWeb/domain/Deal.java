package com.pokupaka.PokupakaWeb.domain;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Set;

@Entity
@Table(name = "DEAL")
public class Deal {
    @Id
    @GeneratedValue
    private Long id;

    @OneToMany(mappedBy = "deal")
    private Set<Order> list;

    private String status;

    @ManyToOne
    @JoinColumn(name = "DEAL_ID")
    private Category category;

}
