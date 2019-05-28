package com.pokupaka.ui.views.orders;


import com.pokupaka.app.security.CurrentUser;
import com.pokupaka.backend.data.entity.Deal;
import com.pokupaka.backend.data.entity.Order;
import com.pokupaka.backend.service.DealsService;
import com.pokupaka.backend.service.OrderService;
import com.pokupaka.ui.crud.AbstractPokupakaCrudView;
import com.pokupaka.ui.utils.PokupakaAppConst;
import com.pokupaka.ui.views.MainLayout;
import com.vaadin.flow.component.crud.BinderCrudEditor;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.BeanValidationBinder;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import org.springframework.beans.factory.annotation.Autowired;

import static com.pokupaka.ui.utils.PokupakaAppConst.PAGE_DEALS;
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
        grid.addColumn(Order::getId).setHeader("Deal Id").setFlexGrow(10);
        grid.addColumn(Order::getStatus).setHeader("Status").setFlexGrow(10);
        grid.addColumn(Order::getCategory).setHeader("Category").setFlexGrow(10);
        grid.addColumn(order -> order.getProduct().getName()).setHeader("Product").setFlexGrow(10);
        grid.addColumn(Order::getQuantity).setHeader("Quantity").setFlexGrow(10);
    }

    @Override
    protected String getBasePage() {
        return PAGE_DEALS;
    }

    private static BinderCrudEditor<Order> createForm() {
        TextField orderId = new TextField("Order ID");
        orderId .getElement().setAttribute("colspan", "2");
        TextField status = new TextField("Status");
        status.getElement().setAttribute("colspan", "2");
        TextField product = new TextField("Product");
        product.getElement().setAttribute("colspan", "2");
        TextField quantity = new TextField("Quantity");
        quantity.getElement().setAttribute("colspan", "2");

        FormLayout form = new FormLayout(orderId , status, product, quantity);

        BeanValidationBinder<Order> binder = new BeanValidationBinder<>(Order.class);

        binder.bind(orderId, "id");
        binder.bind(status,"status");
        //binder.bind(product,"status");
        binder.bind(quantity,"quantity");

        return new BinderCrudEditor<Order>(binder, form) {
            @Override
            public boolean isValid() {
                return binder.validate().isOk();
            }
        };
    }

}
