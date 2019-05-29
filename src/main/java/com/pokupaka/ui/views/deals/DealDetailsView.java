package com.pokupaka.ui.views.deals;

import com.pokupaka.app.security.CurrentUser;
import com.pokupaka.backend.data.entity.*;
import com.pokupaka.backend.repositories.DealRepository;
import com.pokupaka.backend.repositories.OrderRepository;
import com.pokupaka.backend.service.ProductService;
import com.pokupaka.ui.views.MainLayout;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
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
    private final ProductService productService;

    private Deal deal;
    private Label dealDetailsLabel = new Label("");
    private Grid<Order> grid = new Grid<>();

    private Button participateButton = new Button("Participate", VaadinIcon.PLUS.create());
    private Dialog participateDialog;
    private CurrentUser currentUser;


    @Autowired
    public DealDetailsView(DealRepository dealRepository,
                           OrderRepository orderRepository,
                           ProductService productService,
                           CurrentUser currentUser){
        this.dealRepository = dealRepository;
        this.orderRepository = orderRepository;
        this.productService = productService;
        this.currentUser = currentUser;


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
        Label label = new Label("To participate in this deal please fill some info");

        ComboBox<Product> product = new ComboBox<>(PRODUCT);
        product.setItems(productService.getProductsByCategory(deal.getCategory()));

        TextField quantity = new TextField(QUANTITY);;
        quantity.setPattern("[1-9]*");
        quantity.setPreventInvalidInput(true);

        Button confirm = new Button(CONFIRM);

        confirm.addClickListener(e -> startNewOrderAndClose(product, quantity,dialog));

        VerticalLayout verticalLayout = new VerticalLayout();
        verticalLayout.setDefaultHorizontalComponentAlignment(Alignment.CENTER);
        verticalLayout.add(label);
        verticalLayout.add(product);
        verticalLayout.add(quantity);
        verticalLayout.add(confirm);

        dialog.add(verticalLayout);


        //dialog.add(formLayout);

      /*  label.getElement().setAttribute("colspan", "2");
        product.getElement().setAttribute("colspan", "2");
        numberField.getElement().setAttribute("colspan", "2");
        confirm.getElement().setAttribute("colspan", "2");

        FormLayout formLayout = new FormLayout(label, product, numberField, confirm);*/
        return dialog;
    }

    private void startNewOrderAndClose(ComboBox<Product> product, TextField quantity,Dialog dialog) {
        orderRepository.save(new Order(Status.IN_PROGRESS, deal, currentUser.getUser(), product.getValue(),Integer.valueOf(quantity.getValue())));
        dialog.close();
        grid.setItems(orderRepository.findByDealId(deal.getId()));
    }

    @Override
    public void setParameter(BeforeEvent event, @OptionalParameter Long id) {
        if (id != null) {

            Optional<Deal> deal = dealRepository.findById(id);
            if (deal.isPresent()) {
                this.deal = deal.get();
                dealDetailsLabel.setText(String.format(" %s details : ",deal.get().getName()));
                grid.setItems(orderRepository.findByDealId(id));

                participateDialog = createDialog();
                participateButton.addClickListener(e -> participateDialog.open());


            }else {
                participateButton.setEnabled(false);
                dealDetailsLabel.setText("Some problems with loading deal info ...");
            }
            //entityPresenter.loadEntity(id, this::edit);
        }
    }
}
