package com.pokupaka.ui.views;


import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;

@Route(value = "", layout = MainLayout.class)
public class MainView extends VerticalLayout {

    private final Label mainViewLabel = new Label("Main View");

    public MainView() {
        add(mainViewLabel);
    }
}
