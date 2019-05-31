package com.pokupaka.testbench;

import com.pokupaka.testbench.elements.ui.DashboardViewElement;
import com.pokupaka.testbench.elements.ui.LoginViewElement;
import com.pokupaka.testbench.elements.ui.ProductsViewUiElements;
import org.junit.Assert;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class LoginIT extends AbstractIT<LoginViewElement> {

	/*@Test
	public void loginWorks() {
		LoginViewElement loginView = openLoginView();
		assertEquals("Email", loginView.getUsernameLabel());
		loginView.login("admin@vaadin.com", "admin");
	}*/

	@Test
	public void logout() {
		LoginViewElement loginView = openLoginView();
		ProductsViewUiElements productViewUiElements = loginView.login("admin@vaadin.com", "admin");
		System.out.println(" -------------------- 112312312 31 23 12 312 31 3 ------------");
		productViewUiElements.getMenu().logout();
		Assert.assertTrue(getDriver().getCurrentUrl().endsWith("login"));
	}

	/*@Test
	public void loginToNotDefaultUrl() {
		LoginViewElement loginView = openLoginView(getDriver(), APP_URL + "dashboard");
		DashboardViewElement dashboard = loginView.login("admin@vaadin.com", "admin", DashboardViewElement.class);
		Assert.assertNotNull(dashboard);
	}*/
	
	@Override
	protected LoginViewElement openView() {
		return openLoginView();
	}

}