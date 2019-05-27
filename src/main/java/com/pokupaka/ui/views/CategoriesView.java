package com.pokupaka.ui.views;

import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;


@Route(value = "Category", layout = MainLayout.class)
public class CategoriesView extends VerticalLayout {

    public class Category extends VerticalLayout {

        Text text = new Text("Hello! This is Category List");

        {
            add(text);
        }
    }

}