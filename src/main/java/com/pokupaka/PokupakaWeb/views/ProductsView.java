package com.pokupaka.PokupakaWeb.views;

import com.pokupaka.PokupakaWeb.domain.Product;
import com.pokupaka.PokupakaWeb.repository.ProductRepository;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.shared.ui.ValueChangeMode;
import com.vaadin.spring.annotation.SpringView;
import com.vaadin.spring.annotation.VaadinSessionScope;
import com.vaadin.ui.*;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@SpringView
@VaadinSessionScope
public class ProductsView extends HorizontalLayout implements View {

    public static String VIEW_NAME = "productView";
    private Grid<Product> productsGrid = new Grid<>(Product.class);


   ProductRepository productRepository;

    @Override
    public void enter(ViewChangeEvent viewChangeEvent) {
        System.out.println("entering product view  " + this.toString());
    }

    public ProductsView(ProductRepository productRepository) {
        this.productRepository = productRepository;

        final VerticalLayout vLayout = new VerticalLayout();


        Label productLabel = new Label(" Products ");
        vLayout.addComponent(productLabel);
        vLayout.setComponentAlignment(productLabel,Alignment.MIDDLE_CENTER);

        // Filter
        TextField filter = new TextField("", "");
        filter.setValueChangeMode(ValueChangeMode.EAGER);
        filter.addValueChangeListener(e -> showFilteredProducts(e.getValue()));
        vLayout.addComponent(filter);
        vLayout.setComponentAlignment(filter,Alignment.MIDDLE_LEFT);


        // Grid
        productsGrid.setColumns("id","name","price","description","category");
        vLayout.addComponent(productsGrid);
        productsGrid.setWidth("80%");

        //productsGrid.asSingleSelect().addValueChangeListener()

        setDefaultComponentAlignment(Alignment.MIDDLE_CENTER);
        addComponentsAndExpand(vLayout);
        fillGrid();
    }

    private void showFilteredProducts(String name) {
        if (name.isEmpty()) {
            productsGrid.setItems(productRepository.findAll());
        } else {
            productsGrid.setItems(productRepository.findByNameContaining(name));
        }
    }

    public void fillGrid() {
        List<Product> productList = new ArrayList<>();
        Iterator<Product> iterator = productRepository.findAll().iterator();
        while(iterator.hasNext()) {
            iterator.forEachRemaining(productList::add);
        }
        productsGrid.setItems(productList);
    }

    //         grid.getDataProvider().refreshAll();
}