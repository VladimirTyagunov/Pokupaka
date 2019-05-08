package com.pokupaka.PokupakaWeb.views;

import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.router.HighlightConditions;
import com.vaadin.flow.router.RouterLayout;
import com.vaadin.flow.router.RouterLink;
import com.vaadin.flow.server.InitialPageSettings;
import com.vaadin.flow.server.PageConfigurator;

//@HtmlImport("frontend://styles/shared-styles.html")
//@PWA(name = "Beverage Buddy", shortName = "BevBuddy")
//@Viewport("width=device-width, minimum-scale=1.0, initial-scale=1.0, user-scalable=yes")
public class MainLayout extends Div implements RouterLayout, PageConfigurator {

    public MainLayout() {
        H2 title = new H2("Pokupaka Store");
//        title.addClassName("main-layout__title");

        RouterLink mainPage = new RouterLink(null, MainView.class);
        mainPage.add(new Icon(VaadinIcon.HOME), new Text("mainPage"));
        mainPage.addClassName("main-layout__nav-item");

        // Only show as active for the exact URL, but not for sub paths
        mainPage.setHighlightCondition(HighlightConditions.sameLocation());

        RouterLink categoriesLink = new RouterLink("", CategoriesView.class);
        categoriesLink.add(new Icon(VaadinIcon.ARCHIVES), new Text("Categories"));
        categoriesLink.addClassName("main-layout__nav-item");

        RouterLink productsLink = new RouterLink("", ProductsView.class);
        productsLink.add(new Icon(VaadinIcon.SHOP), new Text("Products"));
        productsLink.addClassName("main-layout__nav-item");



        HorizontalLayout navigation = new HorizontalLayout();
//        navigation.addClassName("main-layout__nav");

        navigation.add(mainPage, categoriesLink, productsLink);
        navigation.setMargin(true);
        navigation.setSpacing(true);
        navigation.setPadding(true);

        navigation.getElement().getThemeList().add("dark");
        navigation.setWidth("100%");
        navigation.setAlignItems(FlexComponent.Alignment.CENTER);

        Div header = new Div(title, navigation);
//        header.addClassName("main-layout__header");
        add(header);

        addClassName("main-layout");
    }

    @Override
    public void configurePage(InitialPageSettings settings) {
        settings.addMetaTag("apple-mobile-web-app-capable", "yes");
        settings.addMetaTag("apple-mobile-web-app-status-bar-style", "black");
    }
}