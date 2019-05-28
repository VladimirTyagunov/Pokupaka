package com.pokupaka.ui.views.deals;

import com.pokupaka.app.security.CurrentUser;
import com.pokupaka.backend.data.entity.Deal;
import com.pokupaka.backend.data.entity.Status;
import com.pokupaka.backend.service.DealsService;
import com.pokupaka.ui.crud.AbstractPokupakaCrudView;
import com.pokupaka.ui.utils.PokupakaAppConst;
import com.pokupaka.ui.views.MainLayout;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.charts.model.Navigator;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.crud.BinderCrudEditor;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.BeanValidationBinder;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Arrays;

import static com.pokupaka.ui.utils.PokupakaAppConst.PAGE_DEALS;
import static com.pokupaka.ui.utils.PokupakaAppConst.PAGE_DEAL_DETAILS;

@Route(value = PAGE_DEALS, layout = MainLayout.class)
@PageTitle(PokupakaAppConst.TITLE_DEALS)
public class DealsView extends AbstractPokupakaCrudView<Deal> {

    @Autowired
    public DealsView(DealsService service, CurrentUser currentUser) {
        super(Deal.class, service, new Grid<>(), createForm(), currentUser);
    }

    @Override
    protected void setupGrid(Grid<Deal> grid) {

        grid.setSelectionMode(Grid.SelectionMode.SINGLE);
        grid.asSingleSelect().addValueChangeListener(e -> {
            getUI().ifPresent(ui -> ui.navigate(PAGE_DEAL_DETAILS + "/" + e.getValue().getId()));
            //UI.getCurrent().getRouter().getUrl(DealDetailsView.class,e.getValue().getId());
        });

        grid.addColumn(Deal::getName).setHeader("Deal Name").setFlexGrow(10);
        grid.addColumn(deal -> deal.getStatus().getValue()).setHeader("Status").setFlexGrow(10);
        grid.addColumn(deal -> deal.getCategory().getName()).setHeader("Category").setFlexGrow(10);
    }

    @Override
    protected String getBasePage() {
        return PAGE_DEALS;
    }

    private static BinderCrudEditor<Deal> createForm() {

        TextField name = new TextField("Deal Name");
        ComboBox<String> status = new ComboBox<>("Status");
        status.setItems(Arrays.stream(Status.values()).map(val -> val.getValue()));
        TextField category = new TextField("Category");

        name.getElement().setAttribute("colspan", "2");
        status.getElement().setAttribute("colspan", "2");
        category.getElement().setAttribute("colspan", "2");

        FormLayout form = new FormLayout(name, status, category);

        BeanValidationBinder<Deal> binder = new BeanValidationBinder<>(Deal.class);

        binder.bind(name, "name");

        binder.bind(status, deal -> deal.getStatus().getValue(),
                           (deal, stVal) -> deal.setStatus(Status.findByStrValue(stVal)));


        binder.bind(category,"category.name");

        return new BinderCrudEditor<Deal>(binder, form) {
            @Override
            public boolean isValid() {
                return binder.validate().isOk();
            }
        };
    }

}
