package com.pokupaka.ui.dataproviders;

import com.pokupaka.backend.data.entity.Deal;
import com.pokupaka.backend.service.DealService;
import com.vaadin.flow.spring.annotation.SpringComponent;
import com.vaadin.flow.spring.annotation.UIScope;


import com.vaadin.flow.data.provider.AbstractBackEndDataProvider;
import com.vaadin.flow.data.provider.Query;
import org.springframework.data.domain.PageRequest;

import java.util.stream.Stream;

@SpringComponent
@UIScope
public class DealDataProvider extends AbstractBackEndDataProvider<Deal, String> {

    private final DealService dealService;

    public DealDataProvider(DealService dealService) {
        this.dealService = dealService;
    }

    @Override
    protected int sizeInBackEnd(Query<Deal, String> query) {
        return (int) dealService.countAnyMatching(query.getFilter());
    }

    @Override
    public Stream<Deal> fetchFromBackEnd(Query<Deal, String> query) {
        return dealService.findAnyMatching(query.getFilter(), PageRequest.of(query.getOffset(), query.getLimit()))
                .stream();
    }

}