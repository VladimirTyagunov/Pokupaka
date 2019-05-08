package com.pokupaka.PokupakaWeb.views;

import com.vaadin.navigator.View;
import com.vaadin.ui.Composite;
import com.vaadin.ui.Label;

public class CategoriesView extends Composite implements View {

    public static String VIEW_NAME = "categoriesView";

    public CategoriesView() {
        setCompositionRoot(new Label("Categories will be displayed here "));
    }
}