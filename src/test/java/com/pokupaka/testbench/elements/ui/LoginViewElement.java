package com.pokupaka.testbench.elements.ui;

import com.vaadin.flow.component.login.testbench.LoginOverlayElement;
import com.vaadin.testbench.TestBenchElement;

import java.net.URI;

public class LoginViewElement extends LoginOverlayElement {

	public ProductsViewUiElements login(String username, String password) {
		return login(username, password, ProductsViewUiElements.class);
	}

	public <E extends TestBenchElement> E login(
		String username, String password, Class<E> target) {

		getUsernameField().setValue(username);
		getPasswordField().setValue(password);
		getSubmitButton().click();

		getDriver().get(getDriver().getCurrentUrl() + "products");

		return $(target).onPage().waitForFirst();
	}

	public String getUsernameLabel() {
		return getUsernameField().getLabel();
	}

}
