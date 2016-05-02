package com.myshop.order.command.application;

import com.myshop.order.command.domain.Orderer;
import com.myshop.order.command.domain.ShippingInfo;

import java.util.List;

public class OrderRequest {
    private List<OrderProduct> orderProducts;
    private Orderer orderer;
    private ShippingInfo shippingInfo;

    public Orderer getOrderer() {
        return orderer;
    }

    public void setOrderer(Orderer orderer) {
        this.orderer = orderer;
    }

    public List<OrderProduct> getOrderProducts() {
        return orderProducts;
    }

    public void setOrderProducts(List<OrderProduct> orderProducts) {
        this.orderProducts = orderProducts;
    }

    public ShippingInfo getShippingInfo() {
        return shippingInfo;
    }

    public void setShippingInfo(ShippingInfo shippingInfo) {
        this.shippingInfo = shippingInfo;
    }
}
