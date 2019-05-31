package com.pokupaka.testbench.elements.ui;

import com.pokupaka.testbench.elements.components.AppNavigationElement;
import com.vaadin.flow.component.applayout.testbench.AppLayoutElement;

public class MainViewElement extends AppLayoutElement {

	public AppNavigationElement getMenu() {
		return $(AppNavigationElement.class).first();
	}

}
