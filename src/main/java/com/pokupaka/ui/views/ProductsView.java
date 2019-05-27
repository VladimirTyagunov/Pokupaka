package com.pokupaka.ui.views;

import com.pokupaka.backend.data.Role;
import com.pokupaka.backend.data.entity.Product;
import com.pokupaka.backend.repositories.ProductRepository;
import com.pokupaka.ui.components.ProductEditor;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.value.ValueChangeMode;
import com.vaadin.flow.router.Route;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@Route(value = "products", layout = MainLayout.class)
@Secured(Role.ADMIN)
public class ProductsView extends HorizontalLayout {

    private ProductRepository productRepository;
    private final ProductEditor editor;

    public static String VIEW_NAME = "productView";
    private Grid<Product> productsGrid = new Grid<>(Product.class);

    private final TextField filter = new TextField("", "Type to filter");
    private final Button addNewBtn = new Button("Add new");
    private final HorizontalLayout toolbar = new HorizontalLayout(filter, addNewBtn);

    @Autowired
    public ProductsView(ProductRepository productRepository,ProductEditor editor) {
        this.productRepository = productRepository;
        this.editor = editor;

        final VerticalLayout vLayout = new VerticalLayout();


        Label productLabel = new Label(" Products ");
        vLayout.add(productLabel);

        filter.setValueChangeMode(ValueChangeMode.EAGER);
        filter.addValueChangeListener(e -> showFilteredProducts(e.getValue()));


        // Grid
        productsGrid.setColumns("id", "name", "price", "description", "category");
        productsGrid.asSingleSelect().addValueChangeListener(e -> { editor.editProduct(e.getValue()); });

        addNewBtn.addClickListener(e -> editor.editProduct(new Product()));

        editor.setChangeHandler(() -> {
            editor.setVisible(false);
            showFilteredProducts(filter.getValue());
        });

        vLayout.add(toolbar, productsGrid, editor);

        add(vLayout);
        showFilteredProducts("");
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