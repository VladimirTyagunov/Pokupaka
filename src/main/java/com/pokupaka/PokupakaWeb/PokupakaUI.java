package com.pokupaka.PokupakaWeb;

import com.pokupaka.PokupakaWeb.repository.ProductRepository;
import com.pokupaka.PokupakaWeb.views.CategoriesView;
import com.pokupaka.PokupakaWeb.views.MainView;
import com.pokupaka.PokupakaWeb.views.ProductsView;
import com.vaadin.annotations.VaadinServletConfiguration;
import com.vaadin.navigator.Navigator;
import com.vaadin.server.VaadinRequest;
import com.vaadin.server.VaadinServlet;
import com.vaadin.spring.annotation.SpringUI;
import com.vaadin.ui.*;
import com.vaadin.ui.themes.ValoTheme;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.annotation.WebServlet;

//@Theme("mytheme")
@SpringUI
public class PokupakaUI extends UI {

    Navigator navigator;

    @Autowired
    ProductRepository productRepository;



    @Override
    protected void init(VaadinRequest vaadinRequest) {

        Label title = new Label("Menu");
        title.addStyleName(ValoTheme.MENU_TITLE);

        Button view1 = new Button("Products",e -> getNavigator().navigateTo(ProductsView.VIEW_NAME));
        view1.addStyleNames(ValoTheme.BUTTON_LINK, ValoTheme.MENU_ITEM);

        Button view2 = new Button("Categories", e -> getNavigator().navigateTo(CategoriesView.VIEW_NAME));
        view2.addStyleNames(ValoTheme.BUTTON_LINK, ValoTheme.MENU_ITEM);

        CssLayout menu = new CssLayout(title, view1, view2);
        menu.addStyleName(ValoTheme.MENU_ROOT);

        HorizontalLayout viewContainer = new HorizontalLayout();
        //viewContainer.addComponentsAndExpand();

        HorizontalLayout mainLayout = new HorizontalLayout(menu);
        mainLayout.addComponentsAndExpand(viewContainer);
        mainLayout.setSizeFull();
        setContent(mainLayout);

        navigator = new Navigator(this, viewContainer);
        navigator.addView("", MainView.class);
        navigator.addView(ProductsView.VIEW_NAME,new ProductsView(productRepository));
        navigator.addView(CategoriesView.VIEW_NAME, CategoriesView.class);
    }


    @WebServlet(urlPatterns = "/*", name = "MyUIServlet", asyncSupported = true)
    @VaadinServletConfiguration(ui = PokupakaUI.class, productionMode = false)
    public static class MyUIServlet extends VaadinServlet {
    }
}
