package com.pokupaka.ui.views;

import com.pokupaka.app.security.SecurityUtils;
import com.pokupaka.ui.views.admin.UsersView;
import com.pokupaka.ui.views.products.ProductsView;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.applayout.AbstractAppRouterLayout;
import com.vaadin.flow.component.applayout.AppLayout;
import com.vaadin.flow.component.applayout.AppLayoutMenu;
import com.vaadin.flow.component.applayout.AppLayoutMenuItem;
import com.vaadin.flow.component.dependency.HtmlImport;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.server.InitialPageSettings;
import com.vaadin.flow.server.PageConfigurator;

import static com.pokupaka.ui.utils.PokupakaAppConst.*;
import static com.vaadin.flow.component.icon.VaadinIcon.*;

@HtmlImport("frontend://styles/shared-styles.html")
public class MainLayout  extends AbstractAppRouterLayout implements PageConfigurator {

    @Override
    protected void configure(AppLayout appLayout, AppLayoutMenu menu) {
        appLayout.setBranding(new Span("PokupakaApp"));

        if (SecurityUtils.isUserLoggedIn()) {
            if (SecurityUtils.isAccessGranted(ProductsView.class)) {
                setMenuItem(menu, RECORDS, TITLE_PRODUCTS, PAGE_PRODUCTS);
            }
            setMenuItem(menu, CART, TITLE_MY_ORDERS, PAGE_ORDERS);
            setMenuItem(menu, HANDSHAKE, TITLE_DEALS, PAGE_DEALS);
            if (SecurityUtils.isAccessGranted(UsersView.class)) {
                setMenuItem(menu, USERS, TITLE_USERS, PAGE_USERS);
            }

            AppLayoutMenuItem logoutMenuItem = new AppLayoutMenuItem(VaadinIcon.SIGN_OUT.create(),
                    TITLE_LOGOUT, e -> UI.getCurrent().getPage().executeJavaScript("location.assign('logout')"));
            setMenuItem(menu,logoutMenuItem);
        }
        getElement().addEventListener("search-focus", e -> {
            appLayout.getElement().getClassList().add("hide-navbar");
        });

        getElement().addEventListener("search-blur", e -> {
            appLayout.getElement().getClassList().remove("hide-navbar");
        });
    }

    private void setMenuItem(AppLayoutMenu menu, AppLayoutMenuItem menuItem) {
        menuItem.getElement().setAttribute("theme", "icon-on-top");
        menu.addMenuItem(menuItem);
    }

    private void setMenuItem(AppLayoutMenu menu, VaadinIcon icon, String title, String route) {
        AppLayoutMenuItem menuItem = createMenuItem(icon, title, route);
        menuItem.getElement().setAttribute("theme", "icon-on-top");
        menu.addMenuItem(menuItem);
    }

    private AppLayoutMenuItem createMenuItem(VaadinIcon icon, String title, String route) {
        return new AppLayoutMenuItem(icon.create(), title, route);
    }

    @Override
    public void configurePage(InitialPageSettings settings) {
        settings.addMetaTag("apple-mobile-web-app-capable", "yes");
        settings.addMetaTag("apple-mobile-web-app-status-bar-style", "black");
    }
}