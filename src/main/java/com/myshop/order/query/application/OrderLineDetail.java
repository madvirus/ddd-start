package com.myshop.order.query.application;

import com.myshop.catalog.domain.product.Product;
import com.myshop.order.command.domain.OrderLine;

import java.util.Optional;

public class OrderLineDetail {

    private final String productId;
    private final int price;
    private final int quantity;
    private final int amounts;
    private String productName;
    private String productImagePath;

    public OrderLineDetail(OrderLine orderLine, Product product) {
        productId = orderLine.getProductId().getId();
        price = orderLine.getPrice().getValue();
        quantity = orderLine.getQuantity();
        amounts = orderLine.getAmounts().getValue();
        productName = product.getName();
        productImagePath = product.getFirstIamgeThumbnailPath();
    }

    public String getProductId() {
        return productId;
    }

    public int getPrice() {
        return price;
    }

    public int getQuantity() {
        return quantity;
    }

    public int getAmounts() {
        return amounts;
    }

    public String getProductName() {
        return productName;
    }

    public String getProductImagePath() {
        return productImagePath;
    }
}
