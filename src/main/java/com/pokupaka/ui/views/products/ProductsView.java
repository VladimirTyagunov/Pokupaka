package com.pokupaka.ui.views.products;

import com.pokupaka.app.security.CurrentUser;
import com.pokupaka.backend.data.Role;
import com.pokupaka.backend.data.entity.Product;
import com.pokupaka.backend.service.ProductService;
import com.pokupaka.ui.views.MainLayout;
import com.pokupaka.ui.crud.AbstractPokupakaCrudView;
import com.pokupaka.ui.utils.PokupakaAppConst;
import com.pokupaka.ui.utils.converters.CurrencyFormatter;
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

import static com.pokupaka.ui.utils.PokupakaAppConst.PAGE_PRODUCTS;

@Route(value = PAGE_PRODUCTS, layout = MainLayout.class)
@PageTitle(PokupakaAppConst.TITLE_PRODUCTS)
@Secured(Role.ADMIN)
public class ProductsView extends AbstractPokupakaCrudView<Product> {

	private CurrencyFormatter currencyFormatter = new CurrencyFormatter();
	private static String currencySymbol = Currency.getInstance(PokupakaAppConst.APP_LOCALE).getSymbol();


	@Autowired
	public ProductsView(ProductService service, CurrentUser currentUser) {
		super(Product.class, service, new Grid<>(), createForm(), currentUser);
	}

	@Override
	protected void setupGrid(Grid<Product> grid) {
		grid.addColumn(Product::getName).setHeader("Product Name").setFlexGrow(10);
		grid.addColumn(Product::getCategory).setHeader("Category").setFlexGrow(10);
		//grid.addColumn(Product::getPrice).setHeader("Price").setFlexGrow(10);
		grid.addColumn(p -> String.valueOf(p.getPrice()) + currencySymbol).setHeader("Price").setFlexGrow(10);
		//grid.addColumn(p -> (p.getPrice() + "$")).setHeader("Price").setFlexGrow(10);
		//grid.addColumn(p -> currencyFormatter.encode((int)p.getPrice() * 100)).setHeader("Unit Price");
	}

	@Override
	protected String getBasePage() {
		return PAGE_PRODUCTS;
	}

	private static BinderCrudEditor<Product> createForm() {
		TextField name = new TextField("Product name");
		TextField category = new TextField("Category");
		TextField price = new TextField("Price");


		name.getElement().setAttribute("colspan", "2");
		category.getElement().setAttribute("colspan", "2");
		price.getElement().setAttribute("colspan", "2");

		FormLayout form = new FormLayout(name,category, price);

		BeanValidationBinder<Product> binder = new BeanValidationBinder<>(Product.class);

		binder.bind(name, "name");
		binder.bind(category,"category.name");

		//binder.forField(price).withConverter(new PriceConverter()).bind("price");
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
