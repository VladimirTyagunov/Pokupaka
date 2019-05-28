package com.pokupaka.ui.views.deals;

import com.pokupaka.backend.data.entity.Deal;
import com.pokupaka.backend.data.entity.Product;
import com.pokupaka.backend.repositories.DealRepository;
import com.pokupaka.ui.views.MainLayout;
import com.vaadin.flow.component.Key;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.textfield.NumberField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.router.BeforeEvent;
import com.vaadin.flow.router.HasUrlParameter;
import com.vaadin.flow.router.OptionalParameter;
import com.vaadin.flow.router.Route;
import org.springframework.beans.factory.annotation.Autowired;

import static com.pokupaka.ui.utils.PokupakaAppConst.PAGE_DEAL_DETAILS;

@Route(value = PAGE_DEAL_DETAILS, layout = MainLayout.class)
public class DealDetailsView extends HorizontalLayout implements HasUrlParameter<Long> {

    private final DealRepository dealRepository;

    private Deal deal;
    private Label label = new Label("Nothing");

    private TextField name = new TextField("Deal Name");
    /*private TextField tag = new TextField("Tag");
    private TextField description = new TextField("Product description");
    private NumberField price = new NumberField("Product price");*/
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
    public DealDetailsView(DealRepository dealRepository){
        this.dealRepository = dealRepository;

        //add(name, tag, description, price, actions);
        add(name,label, actions);

        binder.bindInstanceFields(this);

        setSpacing(true);

        save.getElement().getThemeList().add("primary");
        delete.getElement().getThemeList().add("error");

        //addKeyPressListener(Key.ENTER, e -> save());

        /*save.addClickListener(e -> save());
        delete.addClickListener(e -> delete());
        cancel.addClickListener(e -> editProduct(null));
*/
        //setVisible(false);
    }



    @Override
    public void setParameter(BeforeEvent event, @OptionalParameter Long id) {
        if (id != null) {
            label.setText("Some deal " + id);
            //entityPresenter.loadEntity(id, this::edit);
        }
    }
}
