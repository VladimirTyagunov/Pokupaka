package com.pokupaka.PokupakaWeb.components;

import com.pokupaka.backend.data.entity.Product;
import com.pokupaka.backend.repositories.ProductRepository;
import com.vaadin.flow.component.Key;
import com.vaadin.flow.component.KeyNotifier;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.NumberField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.spring.annotation.SpringComponent;
import com.vaadin.flow.spring.annotation.UIScope;
import org.springframework.beans.factory.annotation.Autowired;


@SpringComponent
@UIScope
public class ProductEditor extends VerticalLayout implements KeyNotifier {
    private final ProductRepository productRepository;

    private Product product;

    private TextField name = new TextField("Product Name");
    //private TextField category = new TextField("Product Category");
    private TextField description = new TextField("Product Description");
    private NumberField price = new NumberField("Product Price");

//    price.setPrefixComponent(new Span("$"));

    private Button save = new Button("Save", VaadinIcon.CHECK.create());
    private Button cancel = new Button("Cancel");
    private Button delete = new Button("Delete", VaadinIcon.TRASH.create());
    private HorizontalLayout actions = new HorizontalLayout(save, cancel, delete);

    private Binder<Product> binder = new Binder<>(Product.class);

    private ChangeHandler changeHandler;

    public void setChangeHandler(ChangeHandler changeHandler) {
        this.changeHandler = changeHandler;
    }

    public interface ChangeHandler {
        void onChange();
    }


    @Autowired
    public ProductEditor(ProductRepository productRepository){
        this.productRepository = productRepository;

        add(name,/* category,*/ description, price, actions);

        binder.bindInstanceFields(this);

        setSpacing(true);

        save.getElement().getThemeList().add("primary");
        delete.getElement().getThemeList().add("error");

        addKeyPressListener(Key.ENTER, e -> save());

        save.addClickListener(e -> save());
        delete.addClickListener(e -> delete());
        cancel.addClickListener(e -> setVisible(false));

        setVisible(false);
    }

    public void editProduct(Product newProduct) {

        if (newProduct == null) {
            setVisible(false);
            return;
        }

        if (newProduct.getId() != null) {
            this.product = productRepository.findById(newProduct.getId()).orElse(newProduct);
        } else {
            this.product = newProduct;
        }

        binder.setBean(product);

        setVisible(true);

        name.focus();
    }

    private void delete() {
        productRepository.delete(product);
        changeHandler.onChange();
    }

    private void save() {
        productRepository.save(product);
        changeHandler.onChange();
    }
}