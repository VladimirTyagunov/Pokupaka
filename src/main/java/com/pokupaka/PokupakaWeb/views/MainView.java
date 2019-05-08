package com.pokupaka.PokupakaWeb.views;


import com.pokupaka.PokupakaWeb.domain.Product;
import com.vaadin.navigator.View;
import com.vaadin.spring.annotation.SpringView;
import com.vaadin.spring.annotation.UIScope;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Grid;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;

import javax.annotation.PostConstruct;

//@StyleSheet("frontend://styles/styles.css")
@UIScope
@SpringView(name = MainView.VIEW_NAME)
public class MainView extends HorizontalLayout implements View {

    public static final String VIEW_NAME = "main";

    private Grid<Product> grid = new Grid<>(Product.class);

    public MainView() {
        setSizeFull();
        setDefaultComponentAlignment(Alignment.MIDDLE_CENTER);
        addComponent(new Label(" This is main view. Please select option from menu"));
    }


    @PostConstruct
    void init() {
        System.out.println("init in the MainView");
    }
}
