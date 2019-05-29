package com.pokupaka.ui.views.login;


import com.pokupaka.app.security.SecurityUtils;
import com.pokupaka.ui.utils.PokupakaAppConst;
import com.pokupaka.ui.views.orders.OrdersView;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.dependency.HtmlImport;
import com.vaadin.flow.component.login.LoginI18n;
import com.vaadin.flow.component.login.LoginOverlay;
import com.vaadin.flow.component.page.Viewport;
import com.vaadin.flow.router.*;

@Route()
@PageTitle("PokupakaApp")
@HtmlImport("styles/shared-styles.html")
@Viewport(PokupakaAppConst.VIEWPORT)
public class LoginView extends LoginOverlay implements AfterNavigationObserver, BeforeEnterObserver {

	public LoginView() {
		LoginI18n i18n = LoginI18n.createDefault();
		i18n.setHeader(new LoginI18n.Header());
		i18n.getHeader().setTitle("PokupakaApp");
		// TODO
		i18n.getHeader().setDescription("admin@vaadin.com + admin\n" + "user@pokupaka.com + user");
		i18n.setAdditionalInformation(null);
		i18n.setForm(new LoginI18n.Form());
		i18n.getForm().setSubmit("Sign in");
		i18n.getForm().setTitle("Sign in");
		i18n.getForm().setUsername("Email");
		i18n.getForm().setPassword("Password");
		setI18n(i18n);
		setForgotPasswordButtonVisible(false);
		setAction("login");

		setOpened(true);
	}

	@Override
	public void beforeEnter(BeforeEnterEvent event) {
		if (SecurityUtils.isUserLoggedIn()) {
			// Needed manually to change the URL because of https://github.com/vaadin/flow/issues/4189
			UI.getCurrent().getPage().getHistory().replaceState(null, PokupakaAppConst.PAGE_ORDERS);
			event.rerouteTo(OrdersView.class);
		}
	}

	@Override
	public void afterNavigation(AfterNavigationEvent event) {
		setError(event.getLocation().getQueryParameters().getParameters().containsKey("error"));
	}

}
