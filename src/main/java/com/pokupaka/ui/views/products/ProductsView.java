package com.pokupaka.ui.views.products;

import com.pokupaka.app.security.CurrentUser;
import com.pokupaka.backend.data.Role;
import com.pokupaka.backend.data.entity.Category;
import com.pokupaka.backend.data.entity.Product;
import com.pokupaka.backend.service.ProductService;
import com.pokupaka.ui.crud.AbstractPokupakaCrudView;
import com.pokupaka.ui.dataproviders.CategoryDataProvider;
import com.pokupaka.ui.utils.PokupakaAppConst;
import com.pokupaka.ui.views.MainLayout;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.crud.BinderCrudEditor;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.BeanValidationBinder;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;

import java.util.Currency;

import static com.pokupaka.ui.utils.PokupakaAppConst.Labels.CATEGORY;
import static com.pokupaka.ui.utils.PokupakaAppConst.Labels.PRICE;
import static com.pokupaka.ui.utils.PokupakaAppConst.PAGE_PRODUCTS;

@Route(value = PAGE_PRODUCTS, layout = MainLayout.class)
@PageTitle(PokupakaAppConst.TITLE_PRODUCTS)
@Secured(Role.ADMIN)
public class ProductsView extends AbstractPokupakaCrudView<Product> {

	private static String currencySymbol = Currency.getInstance(PokupakaAppConst.APP_LOCALE).getSymbol();

	@Autowired
	public ProductsView(ProductService service, CategoryDataProvider categoryDataProvider, CurrentUser currentUser) {
		super(Product.class, service, new Grid<>(), createForm(categoryDataProvider), currentUser);
	}

	@Override
	protected void setupGrid(Grid<Product> grid) {
		grid.addColumn(Product::getName).setHeader("Product Name").setFlexGrow(10);
		grid.addColumn(deal -> deal.getCategory().getName()).setHeader(CATEGORY).setFlexGrow(10);
		grid.addColumn(p -> currencySymbol + " " + p.getPrice()).setHeader(PRICE).setFlexGrow(10);
	}

	@Override
	protected String getBasePage() {
		return PAGE_PRODUCTS;
	}

	private static BinderCrudEditor<Product> createForm(CategoryDataProvider categoryDataProvider) {
		TextField name = new TextField("Product name");

		ComboBox<Category> category = new ComboBox<>(CATEGORY);
		category.setDataProvider(categoryDataProvider);

		TextField price = new TextField("Price");


		name.getElement().setAttribute("colspan", "2");
		category.getElement().setAttribute("colspan", "2");
		price.getElement().setAttribute("colspan", "2");

		FormLayout form = new FormLayout(name, category, price);

		BeanValidationBinder<Product> binder = new BeanValidationBinder<>(Product.class);

		binder.bind(name, "name");
		binder.bind(category, Product::getCategory, Product::setCategory);

		binder.bind(price,"price");
		price.setPattern("\\d+(\\.\\d?\\d?)?$");
		price.setPreventInvalidInput(true);

		price.setPrefixComponent(new Span(currencySymbol));

		return new BinderCrudEditor<Product>(binder, form) {
			@Override
			public boolean isValid() {
				return binder.validate().isOk();
			}
		};
	}

}
