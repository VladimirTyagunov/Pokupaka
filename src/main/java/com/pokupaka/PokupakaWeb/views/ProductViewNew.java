//package com.pokupaka.PokupakaWeb.views;
//
//import com.pokupaka.PokupakaWeb.components.ProductEditor;
//import com.pokupaka.backend.data.entity.Product;
//import com.pokupaka.backend.repositories.ProductRepository;
//import com.vaadin.flow.component.crud.Crud;
//import com.vaadin.flow.component.grid.Grid;
//import org.springframework.beans.factory.annotation.Autowired;
//
//public class ProductViewNew extends Crud<Product> {
//
//
//    private ProductRepository productRepository;
//    private final ProductEditor editor;
//
//    @Autowired
//    public ProductViewNew(ProductRepository productRepository, ProductEditor editor) {
//        super(Product.class, new Grid<>(), new ProductEditor());
//    }
//}
