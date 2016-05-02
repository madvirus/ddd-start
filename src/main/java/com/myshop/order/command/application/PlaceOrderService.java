package com.myshop.order.command.application;

import com.myshop.catalog.domain.product.Product;
import com.myshop.catalog.domain.product.ProductId;
import com.myshop.catalog.domain.product.ProductRepository;
import com.myshop.order.command.domain.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
public class PlaceOrderService {
    private ProductRepository productRepository;
    private OrderRepository orderRepository;

    @Transactional
    public OrderNo placeOrder(OrderRequest orderRequest) {
        if (orderRequest == null) throw new IllegalArgumentException();
        if (orderRequest.getOrderer() == null) throw new IllegalArgumentException();
        if (orderRequest.getOrderProducts() == null) throw new IllegalArgumentException();
        if (orderRequest.getOrderProducts().isEmpty()) throw new IllegalArgumentException();

        List<OrderLine> orderLines = new ArrayList<>();
        for (OrderProduct op : orderRequest.getOrderProducts()) {
            Product product = productRepository.findById(new ProductId(op.getProductId()));
            if (product == null) {
                throw new NoOrderProductException(op.getProductId());
            }
            orderLines.add(new OrderLine(product.getId(), product.getPrice(), op.getQuantity()));
        }
        OrderNo orderNo = orderRepository.nextOrderNo();
        Order order = new Order(orderNo, orderRequest.getOrderer(), orderLines, orderRequest.getShippingInfo(), OrderState.PAYMENT_WAITING);
        orderRepository.save(order);
        return orderNo;
    }

    @Autowired
    public void setProductRepository(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Autowired
    public void setOrderRepository(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }
}
