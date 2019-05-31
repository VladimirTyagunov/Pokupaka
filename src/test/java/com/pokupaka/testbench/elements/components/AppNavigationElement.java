package com.pokupaka.testbench.elements.components;

import com.pokupaka.testbench.elements.ui.*;
import com.vaadin.flow.component.tabs.testbench.TabElement;
import com.vaadin.flow.component.tabs.testbench.TabsElement;
import com.vaadin.testbench.TestBenchElement;

public class AppNavigationElement extends TabsElement {

	public ProductsViewUiElements navigateToStorefront() {
		return navigateTo(0, ProductsViewUiElements.class);
	}

	public DashboardViewElement navigateToDashboard() {
		return navigateTo(1, DashboardViewElement.class);
	}

	public UsersViewElement navigateToUsers() {
		return navigateTo(2, UsersViewElement.class);
	}

	public ProductsViewElement navigateToProducts() {
		return navigateTo(3, ProductsViewElement.class);
	}

	public LoginViewElement logout() {
		$(TabElement.class).last().click();
		return $(LoginViewElement.class).onPage().waitForFirst();
	}

	private <T extends TestBenchElement> T navigateTo(int index, Class<T> landingPage) {
		$(TabElement.class).get(index).click();
		return $(landingPage).onPage().waitForFirst();
	}
}
