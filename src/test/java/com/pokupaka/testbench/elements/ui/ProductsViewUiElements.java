package com.pokupaka.testbench.elements.ui;

import com.pokupaka.backend.data.entity.Product;
import com.pokupaka.testbench.elements.components.OrderCardElement;
import com.pokupaka.testbench.elements.components.OrderDetailsElement;
import com.pokupaka.testbench.elements.components.ProductElement;
import com.pokupaka.testbench.elements.components.SearchBarElement;
import com.vaadin.flow.component.button.testbench.ButtonElement;
import com.vaadin.flow.component.dialog.testbench.DialogElement;
import com.vaadin.flow.component.grid.testbench.GridElement;
import com.vaadin.flow.component.textfield.testbench.TextFieldElement;
import com.vaadin.testbench.TestBenchElement;
import com.vaadin.testbench.elementsbase.Element;

import java.util.Optional;

@Element("products-view")
public class ProductsViewUiElements extends TestBenchElement implements HasApp, HasCrudView {

	@Override
	public GridElement getGrid() {
		return $(GridElement.class).waitForFirst();
	}

	public OrderCardElement getOrderCard(int index) {
		// First visible grid cell has has 4th id
		return getGrid().$(OrderCardElement.class).get(index + 4);
	}

	@SuppressWarnings("unchecked")
	@Override
	public Class<Product> getFormClass() {
		return Product.class;
	}

	public ProductElement getOrderEditor() {
		return getDialog().get().$(ProductElement.class).first();
	}

	public OrderDetailsElement getOrderDetails() {
		return getDialog().get().$(OrderDetailsElement.class).first();
	}

	public SearchBarElement getSearchBar() {
		return $(SearchBarElement.class).first();
	}

	@Override
	public Optional<DialogElement> getDialog() {
		return Optional.of($(DialogElement.class).waitForFirst());
	}
}
