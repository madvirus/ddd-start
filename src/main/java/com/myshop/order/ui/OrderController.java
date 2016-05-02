package com.myshop.order.ui;

import com.myshop.catalog.application.ProductService;
import com.myshop.catalog.domain.product.Product;
import com.myshop.member.domain.Member;
import com.myshop.member.domain.MemberId;
import com.myshop.member.domain.MemberRepository;
import com.myshop.order.command.application.NoOrderProductException;
import com.myshop.order.command.application.OrderProduct;
import com.myshop.order.command.application.OrderRequest;
import com.myshop.order.command.application.PlaceOrderService;
import com.myshop.order.command.domain.OrderNo;
import com.myshop.order.command.domain.Orderer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Controller
public class OrderController {
    private MemberRepository memberRepository;
    private ProductService productService;
    private PlaceOrderService placeOrderService;

    @RequestMapping(value = "/orders/orderConfirm", method = RequestMethod.POST)
    public String orderConfirm(@ModelAttribute("orderReq") OrderRequest orderRequest,
                               ModelMap modelMap) {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        orderRequest.setOrderer(createOrderer(user));
        populateProductsModel(orderRequest, modelMap);
        return "order/confirm";
    }

    private Orderer createOrderer(User user) {
        Member member = memberRepository.findById(new MemberId(user.getUsername()));
        return new Orderer(member.getId(), member.getName());
    }

    private void populateProductsModel(@ModelAttribute("orderReq") OrderRequest orderRequest, ModelMap modelMap) {
        modelMap.addAttribute("products", getProducts(orderRequest.getOrderProducts()));
    }

    private List<Product> getProducts(List<OrderProduct> orderProducts) {
        List<Product> results = new ArrayList<>();
        for (OrderProduct op : orderProducts) {
            Optional<Product> productOpt = productService.getProduct(op.getProductId());
            Product product = productOpt.orElseThrow(() -> new NoOrderProductException(op.getProductId()));
            results.add(product);
        }
        return results;
    }

    @RequestMapping(value = "/orders/order", method = RequestMethod.POST)
    public String order(@ModelAttribute("orderReq") OrderRequest orderRequest,
                        BindingResult bindingResult,
                        ModelMap modelMap) {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        new OrderRequestValidator().validate(orderRequest, bindingResult);
        orderRequest.setOrderer(createOrderer(user));
        if (bindingResult.hasErrors()) {
            populateProductsModel(orderRequest, modelMap);
            return "order/confirm";
        } else {
            OrderNo orderNo = placeOrderService.placeOrder(orderRequest);
            modelMap.addAttribute("orderNo", orderNo.getNumber());
            return "order/orderComplete";
        }
    }

    @ExceptionHandler(NoOrderProductException.class)
    public String handleNoOrderProduct() {
        return "order/noProduct";
    }

    @InitBinder
    public void init(WebDataBinder binder) {
        binder.initDirectFieldAccess();
    }

    @Autowired
    public void setMemberRepository(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    @Autowired
    public void setProductService(ProductService productService) {
        this.productService = productService;
    }

    @Autowired
    public void setPlaceOrderService(PlaceOrderService placeOrderService) {
        this.placeOrderService = placeOrderService;
    }
}
