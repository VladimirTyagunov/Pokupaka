package com.pokupaka.ui.views.orders;


import com.pokupaka.app.security.CurrentUser;
import com.pokupaka.backend.data.entity.Order;
import com.pokupaka.backend.data.entity.Status;
import com.pokupaka.backend.service.OrderService;
import com.pokupaka.ui.components.AmountField;
import com.pokupaka.ui.crud.AbstractPokupakaCrudView;
import com.pokupaka.ui.utils.PokupakaAppConst;
import com.pokupaka.ui.views.MainLayout;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.crud.BinderCrudEditor;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.textfield.NumberField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.BeanValidationBinder;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Arrays;

import static com.pokupaka.ui.utils.PokupakaAppConst.PAGE_ORDERS;

@Route(value = PAGE_ORDERS, layout = MainLayout.class)
@PageTitle(PokupakaAppConst.TITLE_MY_ORDERS)
public class OrdersView extends AbstractPokupakaCrudView<Order> {

    @Autowired
    public OrdersView(OrderService service, CurrentUser currentUser) {
        super(Order.class, service, new Grid<>(), createForm(), currentUser);
    }

    @Override
    protected void setupGrid(Grid<Order> grid) {
        grid.addColumn(Order::getId).setHeader("№").setFlexGrow(3);
        grid.addColumn(order-> order.getStatus().getValue()).setHeader("Status").setFlexGrow(10);
        //grid.addColumn(Order::getCategory).setHeader("Category").setFlexGrow(10);
        grid.addColumn(order -> order.getProduct().getName()).setHeader("Product").setFlexGrow(10);
        grid.addColumn(Order::getQuantity).setHeader("Quantity").setFlexGrow(10);
    }

    @Override
    protected String getBasePage() {
        return PAGE_ORDERS;
    }

    private static BinderCrudEditor<Order> createForm() {

        ComboBox<String> status = new ComboBox<>("Status");
        status.setItems(Arrays.stream(Status.values()).map(val -> val.getValue()));

        TextField product = new TextField("Product");

        NumberField quantity = new NumberField("quantity");
        quantity.setValue(1d);
        quantity.setMin(0);
        quantity.setMax(100);
        quantity.setHasControls(true);

        product.getElement().setAttribute("colspan", "2");
        quantity.getElement().setAttribute("colspan", "2");
        status.getElement().setAttribute("colspan", "2");


        FormLayout form = new FormLayout(status, product, quantity);

        BeanValidationBinder<Order> binder = new BeanValidationBinder<>(Order.class);

        binder.bind(product,"product.name");
        binder.bind(quantity, "quantity");
        binder.bind(status, order -> order.getStatus().getValue(),
                (order, stVal) -> order.setStatus(Status.findByStrValue(stVal)));


        return new BinderCrudEditor<Order>(binder, form) {
            @Override
            public boolean isValid() {
                return binder.validate().isOk();
            }
        };
    }

}
