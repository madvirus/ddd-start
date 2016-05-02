package com.myshop.order.command.domain;

import java.util.Date;
import java.util.List;

public class OrderPlacedEvent {
    private String number;
    private Orderer orderer;
    private List<OrderLine> orderLines;
    private Date orderDate;

    private OrderPlacedEvent() {}
    public OrderPlacedEvent(String number, Orderer orderer, List<OrderLine> orderLines, Date orderDate) {
        this.number = number;
        this.orderer = orderer;
        this.orderLines = orderLines;
        this.orderDate = orderDate;
    }

    public String getNumber() {
        return number;
    }

    public Orderer getOrderer() {
        return orderer;
    }

    public List<OrderLine> getOrderLines() {
        return orderLines;
    }

    public Date getOrderDate() {
        return orderDate;
    }
}
