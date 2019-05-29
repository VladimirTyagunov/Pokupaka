package com.pokupaka.ui.dataproviders;

import com.pokupaka.backend.data.entity.Category;
import com.pokupaka.backend.service.CategoryService;
import com.vaadin.flow.data.provider.AbstractBackEndDataProvider;
import com.vaadin.flow.data.provider.Query;
import com.vaadin.flow.spring.annotation.SpringComponent;
import com.vaadin.flow.spring.annotation.UIScope;
import org.springframework.data.domain.PageRequest;

import java.util.stream.Stream;

@SpringComponent
@UIScope
public class CategoryDataProvider extends AbstractBackEndDataProvider<Category, String> {

    private final CategoryService categoryService;

    public CategoryDataProvider(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @Override
    protected int sizeInBackEnd(Query<Category, String> query) {
        return (int) categoryService.countAnyMatching(query.getFilter());
    }

    @Override
    public Stream<Category> fetchFromBackEnd(Query<Category, String> query) {
        return categoryService.findAnyMatching(query.getFilter(), PageRequest.of(query.getOffset(), query.getLimit()))
                .stream();
    }

}