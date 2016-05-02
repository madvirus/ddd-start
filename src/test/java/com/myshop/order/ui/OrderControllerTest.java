package com.myshop.order.ui;

import com.myshop.SpringIntTestConfig;
import com.myshop.catalog.domain.product.Product;
import com.myshop.member.domain.MemberId;
import com.myshop.order.command.application.OrderRequest;
import com.myshop.order.command.domain.Orderer;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.Arrays;
import java.util.List;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;
import static org.junit.Assert.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringIntTestConfig
@WebAppConfiguration
public class OrderControllerTest {
    @Autowired
    private WebApplicationContext context;

    private MockMvc mockMvc;

    @Before
    public void init() {
        mockMvc = MockMvcBuilders.webAppContextSetup(context).build();
    }

    @Test
    public void confirm() throws Exception {
        SecurityContextHolder.getContext().setAuthentication(
                new UsernamePasswordAuthenticationToken(new User("user1", "", Arrays.asList(new SimpleGrantedAuthority("ROLE_USER"))), ""));

        MvcResult result = mockMvc.perform(post("/orders/orderConfirm")
                .param("orderProducts[0].productId", "prod-001").param("orderProducts[0].quantity", "1"))
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn();
        OrderRequest orderReq = (OrderRequest) result.getModelAndView().getModel().get("orderReq");
        assertThat(orderReq.getOrderProducts().get(0).getProductId(), equalTo("prod-001"));
        assertThat(orderReq.getOrderProducts().get(0).getQuantity(), equalTo(1));
        assertThat(orderReq.getOrderer(), equalTo(new Orderer(new MemberId("user1"), "사용자1")));

        List<Product> products = (List<Product>) result.getModelAndView().getModel().get("products");
        assertThat(products, notNullValue());
        assertThat(products.get(0).getId().getId(), equalTo("prod-001"));
        SecurityContextHolder.clearContext();
    }

    @Test
    public void order() throws Exception {
        SecurityContextHolder.getContext().setAuthentication(
                new UsernamePasswordAuthenticationToken(new User("user1", "", Arrays.asList(new SimpleGrantedAuthority("ROLE_USER"))), ""));

        mockMvc.perform(post("/orders/order")
                .param("orderProducts[0].productId", "prod-001").param("orderProducts[0].quantity", "1")
                .param("shippingInfo.receiver.name", "수취인").param("shippingInfo.receiver.phone", "00011112222")
                .param("shippingInfo.address.zipCode", "55777")
                .param("shippingInfo.address.address1", "서울 어디구 어디동")
                .param("shippingInfo.address.address2", "자세한 주소"))
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn();

        SecurityContextHolder.clearContext();
    }

    @Test
    public void noData_go_confirm() throws Exception {
        SecurityContextHolder.getContext().setAuthentication(
                new UsernamePasswordAuthenticationToken(new User("user1", "", Arrays.asList(new SimpleGrantedAuthority("ROLE_USER"))), ""));

        mockMvc.perform(post("/orders/order")
                .param("orderProducts[0].productId", "prod-001").param("orderProducts[0].quantity", "1")
                .param("shippingInfo.receiver.name", "수취인").param("shippingInfo.receiver.phone", "00011112222")
                .param("shippingInfo.address.zipCode", "")
                .param("shippingInfo.address.address1", "")
                .param("shippingInfo.address.address2", ""))
                .andExpect(status().isOk())
                .andExpect(view().name("order/confirm"))
                .andReturn();

        SecurityContextHolder.clearContext();
    }

}
