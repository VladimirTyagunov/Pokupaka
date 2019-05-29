package com.pokupaka.ui.views.deals;

import com.pokupaka.backend.data.entity.Deal;
import com.pokupaka.backend.data.entity.Order;
import com.pokupaka.backend.data.entity.Product;
import com.pokupaka.backend.repositories.DealRepository;
import com.pokupaka.backend.repositories.OrderRepository;
import com.pokupaka.ui.views.MainLayout;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.NumberField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.router.BeforeEvent;
import com.vaadin.flow.router.HasUrlParameter;
import com.vaadin.flow.router.OptionalParameter;
import com.vaadin.flow.router.Route;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Optional;

import static com.pokupaka.ui.utils.PokupakaAppConst.Labels.*;
import static com.pokupaka.ui.utils.PokupakaAppConst.PAGE_DEAL_DETAILS;

@Route(value = PAGE_DEAL_DETAILS, layout = MainLayout.class)
public class DealDetailsView extends HorizontalLayout implements HasUrlParameter<Long> {

    private final DealRepository dealRepository;
    private final OrderRepository orderRepository;

    private Deal deal;
    private Label dealDetailsLabel = new Label("");
    private Grid<Order> grid = new Grid<>();

    private Button participateButton = new Button("Participate", VaadinIcon.PLUS.create());
    private final Dialog participateDialog;


    @Autowired
    public DealDetailsView(DealRepository dealRepository, OrderRepository orderRepository){
        this.dealRepository = dealRepository;
        this.orderRepository = orderRepository;
        participateDialog = createDialog();

        setWidth("100%");
        setAlignItems(FlexComponent.Alignment.CENTER);

        VerticalLayout leftPanel = new VerticalLayout();
        VerticalLayout rightPanel = new VerticalLayout();
        VerticalLayout ordersLIst = new VerticalLayout(dealDetailsLabel,grid);
        ordersLIst.setWidth("80%");
        ordersLIst.setAlignItems(FlexComponent.Alignment.CENTER);

        HorizontalLayout bottomPanel = new HorizontalLayout();
        bottomPanel.setAlignItems(Alignment.END);
        bottomPanel.add(participateButton);
        ordersLIst.add(bottomPanel);

        grid.addColumn(Order::getId).setHeader(ORDER_NUM).setFlexGrow(10);
        grid.addColumn(order -> order.getProduct().getName()).setHeader(PRODUCT).setFlexGrow(20);
        grid.addColumn(Order::getQuantity).setHeader(QUANTITY).setFlexGrow(10);
        grid.addColumn(order -> order.getStatus().getValue()).setHeader(STATUS).setFlexGrow(20);

        add(leftPanel);
        add(ordersLIst);
        add(rightPanel);

        participateButton.addClickListener(event -> participateDialog.open());

        //add(name, tag, description, price, actions);
        //add(name, dealDetailsLabel);



        setSpacing(true);

        //save.getElement().getThemeList().add("primary");
        //delete.getElement().getThemeList().add("error");

        //addKeyPressListener(Key.ENTER, e -> save());

        /*save.addClickListener(e -> save());
        delete.addClickListener(e -> delete());
        cancel.addClickListener(e -> editProduct(null));
*/
        //setVisible(false);
    }


    public Dialog createDialog() {

        Dialog dialog = new Dialog();
        //dialog.setWidth("400px");
        //dialog.setHeight("400px");

        Label label = new Label("To participate in this deal please fill some info");

        VerticalLayout verticalLayout = new VerticalLayout();

        TextField productName = new TextField(PRODUCT);
        NumberField numberField = new NumberField(QUANTITY);
        numberField.setMin(1);
        numberField.setMax(10);
        Button confirm = new Button(CONFIRM);

        verticalLayout.add(label);
        verticalLayout.add(productName);
        verticalLayout.add(numberField);
        verticalLayout.add(confirm);

        dialog.add(verticalLayout);


        //dialog.setCloseOnOutsideClick(true);

        return dialog;
    }


    @Override
    public void setParameter(BeforeEvent event, @OptionalParameter Long id) {
        if (id != null) {

            Optional<Deal> deal = dealRepository.findById(id);
            if (deal.isPresent()) {
                dealDetailsLabel.setText(String.format(" %s details : ",deal.get().getName()));
                grid.setItems(orderRepository.findByDealId(id));
            }else {
                dealDetailsLabel.setText("Some problems with loading deal info ...");
            }
            //entityPresenter.loadEntity(id, this::edit);
        }
    }
}
