package com.pokupaka.ui.views.deals;

import com.pokupaka.app.security.CurrentUser;
import com.pokupaka.backend.data.entity.Deal;
import com.pokupaka.backend.service.DealsService;
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

@Route(value = PAGE_DEALS, layout = MainLayout.class)
@PageTitle(PokupakaAppConst.TITLE_DEALS)
public class DealsView extends AbstractPokupakaCrudView<Deal> {

    @Autowired
    public DealsView(DealsService service, CurrentUser currentUser) {
        super(Deal.class, service, new Grid<>(), createForm(), currentUser);
    }

    @Override
    protected void setupGrid(Grid<Deal> grid) {
        grid.addColumn(Deal::getId).setHeader("Deal Id").setFlexGrow(10);
        grid.addColumn(Deal::getStatus).setHeader("Status").setFlexGrow(10);
    }

    @Override
    protected String getBasePage() {
        return PAGE_DEALS;
    }

    private static BinderCrudEditor<Deal> createForm() {
        TextField dealId = new TextField("Deal ID");
        dealId .getElement().setAttribute("colspan", "2");
        TextField status = new TextField("Status");
        status.getElement().setAttribute("colspan", "2");

        FormLayout form = new FormLayout(dealId , status);

        BeanValidationBinder<Deal> binder = new BeanValidationBinder<>(Deal.class);

        binder.bind(dealId, "id");
        binder.bind(status,"status");

        return new BinderCrudEditor<Deal>(binder, form) {
            @Override
            public boolean isValid() {
                return binder.validate().isOk();
            }
        };
    }

}
