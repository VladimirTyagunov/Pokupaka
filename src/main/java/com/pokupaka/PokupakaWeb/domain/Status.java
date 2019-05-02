package com.pokupaka.PokupakaWeb.domain;


public enum Status {

    // Note that length of the status is limited by 20 symbols in the com.pokupaka.PokupakaWeb.domain.OrderItem.status

    NOT_STARTED(0),
    IN_PROGRESS(1),
    COMPLETED(2);

    private int id;

    Status(int id) {
        this.id = id;
    }

}
