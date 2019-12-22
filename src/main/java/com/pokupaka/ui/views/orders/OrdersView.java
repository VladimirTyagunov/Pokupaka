package com.pokupaka.ui.views.orders;


import com.pokupaka.app.security.CurrentUser;
import com.pokupaka.backend.data.entity.Deal;
import com.pokupaka.backend.data.entity.Order;
import com.pokupaka.backend.data.entity.Product;
import com.pokupaka.backend.data.entity.Status;
import com.pokupaka.backend.service.OrderService;
import com.pokupaka.ui.components.AmountField;
import com.pokupaka.ui.crud.AbstractPokupakaCrudView;
import com.pokupaka.ui.dataproviders.DealDataProvider;
import com.pokupaka.ui.dataproviders.ProductDataProvider;
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
import java.util.Currency;

import static com.pokupaka.ui.utils.PokupakaAppConst.Labels.*;
import static com.pokupaka.ui.utils.PokupakaAppConst.PAGE_ORDERS;

@Route(value = PAGE_ORDERS, layout = MainLayout.class)
@PageTitle(PokupakaAppConst.TITLE_MY_ORDERS)
public class OrdersView extends AbstractPokupakaCrudView<Order> {

    private static String currencySymbol = Currency.getInstance(PokupakaAppConst.APP_LOCALE).getSymbol();

    @Autowired
    public OrdersView(OrderService service,
                      ProductDataProvider productDataProvider,
                      DealDataProvider dealDataProvider,
                      CurrentUser currentUser) {
        super(Order.class, service, new Grid<>(), createForm(productDataProvider,dealDataProvider), currentUser);
    }

    @Override
    protected void setupGrid(Grid<Order> grid) {
        grid.addColumn(Order::getId).setHeader("№").setFlexGrow(3);
        grid.addColumn(order-> order.getStatus().getValue()).setHeader(STATUS).setFlexGrow(10);
        //grid.addColumn(Order::getCategory).setHeader("Category").setFlexGrow(10);
        grid.addColumn(order -> order.getProduct().getName()).setHeader(PRODUCT).setFlexGrow(10);
        grid.addColumn(Order::getQuantity).setHeader(QUANTITY).setFlexGrow(10);
//        grid.addColumn(order -> currencySymbol + " " + order.getTotalPrice()).setHeader(TOTAL_PRICE).setFlexGrow(10);
        grid.addColumn(order -> currencySymbol + " " + Double.valueOf(order.getProduct().getPrice()) * order.getQuantity())
                .setHeader(TOTAL_PRICE).setFlexGrow(10);

    }

    @Override
    protected String getBasePage() {
        return PAGE_ORDERS;
    }

    private static BinderCrudEditor<Order> createForm(ProductDataProvider productDataProvider, DealDataProvider dealDataProvider) {

        /*ComboBox<Deal> deals = new ComboBox<>(DEALS);
        deals.setDataProvider(dealDataProvider);*/

        ComboBox<String> status = new ComboBox<>(STATUS);
        status.setItems(Arrays.stream(Status.values()).map(Status::getValue));

        ComboBox<Product> product = new ComboBox<>(PRODUCT);
        product.setDataProvider(productDataProvider);

        TextField quantity = new TextField(QUANTITY);;
        quantity.setPattern("[0-9.,]*");
        quantity.setPreventInvalidInput(true);

        product.getElement().setAttribute("colspan", "2");
        quantity.getElement().setAttribute("colspan", "2");
        status.getElement().setAttribute("colspan", "2");


        FormLayout form = new FormLayout(status, product, quantity);

        BeanValidationBinder<Order> binder = new BeanValidationBinder<>(Order.class);

        binder.bind(product, Order::getProduct, Order::setProduct);

        binder.bind(quantity, order -> String.valueOf(order.getQuantity()),
                (order, qValue) -> {
                    order.setQuantity(Integer.parseInt(qValue));
                    order.setTotalPrice(Double.parseDouble(order.getProduct().getPrice()) * order.getQuantity());
                });

        binder.bind(status, deal -> getStatusStrIfNotNull(deal.getStatus()),
                (deal, stVal) -> deal.setStatus(Status.findByStrValue(stVal)));


        return new BinderCrudEditor<Order>(binder, form) {
            @Override
            public boolean isValid() {
                return binder.validate().isOk();
            }
        };
    }

    private static String getStatusStrIfNotNull(Status status) {
        return status == null ? "" : status.getValue();
    }

}
