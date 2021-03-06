package com.pokupaka.backend.data.entity;


import javax.persistence.*;

@Entity
@Table(name = "category")
public class Category extends AbstractEntity{

    @Column(name = "name")
    private String name;

    @Column(name = "description")
    private String description;

    public Category() {
    }

    public Category(String name, String description) {
        this.name = name;
        this.description = description;
    }

    @Override
    public String toString() {
        //return String.format("'%s' category (id = %s). Description : %s",name,id,description);
        return String.format("%s",name);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
