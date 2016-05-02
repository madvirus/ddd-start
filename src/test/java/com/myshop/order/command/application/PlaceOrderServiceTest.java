package com.myshop.order.command.application;

import com.myshop.catalog.domain.product.Product;
import com.myshop.catalog.domain.product.ProductId;
import com.myshop.catalog.domain.product.ProductRepository;
import com.myshop.common.model.Address;
import com.myshop.common.model.Money;
import com.myshop.member.domain.MemberId;
import com.myshop.order.command.domain.*;
import de.bechte.junit.runners.context.HierarchicalContextRunner;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;

import java.util.Arrays;
import java.util.function.Consumer;
import java.util.function.Supplier;

import static java.util.Collections.emptyList;
import static java.util.Collections.singletonList;
import static org.hamcrest.Matchers.instanceOf;
import static org.hamcrest.Matchers.notNullValue;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.fail;
import static org.mockito.Mockito.*;

@RunWith(HierarchicalContextRunner.class)
public class PlaceOrderServiceTest {

    private PlaceOrderService svc;
    private ProductRepository mockProductRepository;
    private OrderRepository mockOrderRepository;

    @Before
    public void setUp() throws Exception {
        svc = new PlaceOrderService();
        mockProductRepository = mock(ProductRepository.class);
        svc.setProductRepository(mockProductRepository);
        mockOrderRepository = mock(OrderRepository.class);
        svc.setOrderRepository(mockOrderRepository);
    }

    private OrderRequest createRequest() {
        OrderRequest orderRequest = new OrderRequest();
        orderRequest.setOrderer(new Orderer(new MemberId("orderer"), "주문자"));
        orderRequest.setOrderProducts(singletonList(new OrderProduct("P001", 2)));
        orderRequest.setShippingInfo(new ShippingInfo(new Address("12345", "addr1", "addr2"), "message", new Receiver("수취인", "010-1111-2222")));
        return orderRequest;
    }

    public class BadOrderRequestDataContext {
        @Test
        public void should_be_ExceptionThrown() throws Exception {
            assertExceptionOccurredInPlaceOrder(IllegalArgumentException.class, () -> null);
            assertExceptionOccurredInPlaceOrder(IllegalArgumentException.class, request -> request.setOrderer(null));
            assertExceptionOccurredInPlaceOrder(IllegalArgumentException.class, request -> request.setOrderProducts(null));
            assertExceptionOccurredInPlaceOrder(IllegalArgumentException.class, request -> request.setOrderProducts(emptyList()));
        }
    }

    public class NormalOrderRequestContext {

        private ProductId productId = new ProductId("P001");

        @Before
        public void setUp() throws Exception {
            when(mockProductRepository.findById(productId)).thenReturn(new Product(productId, "Product 001", new Money(1000), "detail", emptyList()));
            when(mockOrderRepository.nextOrderNo()).thenReturn(new OrderNo("mock.number"));
        }

        public class SomeOrderProductHasNoProductContext {
            private ProductId noProductId = new ProductId("NO_P001");

            @Before
            public void setUp() throws Exception {
                when(mockProductRepository.findById(noProductId)).thenReturn(null);
            }

            @Test
            public void noProductForOrderProducts() throws Exception {
                assertExceptionOccurredInPlaceOrder(NoOrderProductException.class,
                        request -> request.setOrderProducts(singletonList(new OrderProduct(noProductId.getId(), 2)))
                );
                assertExceptionOccurredInPlaceOrder(NoOrderProductException.class,
                        request ->
                            request.setOrderProducts(Arrays.asList(
                                    new OrderProduct(productId.getId(), 2),
                                    new OrderProduct(noProductId.getId(), 2)
                            ))
                );
            }
        }

        public class NormalContext {
            @Test
            public void orderCreate() throws Exception {
                OrderRequest request = createRequest();
                svc.placeOrder(request);

                ArgumentCaptor<Order> orderCaptor = ArgumentCaptor.forClass(Order.class);
                verify(mockOrderRepository).save(orderCaptor.capture());

                Order createdOrder = orderCaptor.getValue();
                assertThat(createdOrder, notNullValue());
            }
        }

    }

    private void assertExceptionOccurredInPlaceOrder(Class<? extends Exception> exceptionType, Supplier<OrderRequest> requestCreator) {
        try {
            svc.placeOrder(requestCreator.get());
            fail();
        } catch (Exception ex) {
            assertThat(ex, instanceOf(exceptionType));
        }
    }

    private void assertExceptionOccurredInPlaceOrder(Class<? extends Exception> exceptionType, Consumer<OrderRequest> requestSetter) {
        try {
            OrderRequest request = createRequest();
            requestSetter.accept(request);
            svc.placeOrder(request);
            fail();
        } catch (Exception ex) {
            assertThat(ex, instanceOf(exceptionType));
        }
    }

}
