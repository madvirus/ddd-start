package com.myshop.catalog.ui;

import com.myshop.catalog.application.CategoryProduct;
import com.myshop.catalog.application.ProductService;
import com.myshop.catalog.domain.category.Category;
import com.myshop.catalog.domain.category.CategoryRepository;
import com.myshop.catalog.domain.product.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Controller
public class ProductController {
    private CategoryRepository categoryRepository;
    private ProductService productService;

    @RequestMapping("/categories")
    public String categories(ModelMap model) {
        List<Category> categories = categoryRepository.findAll();
        model.addAttribute("categories", categories);
        return "category/categoryList";
    }

    @RequestMapping("/categories/{categoryId}")
    public String list(@PathVariable("categoryId") Long categoryId,
                       @RequestParam(name = "page", required = false, defaultValue = "1") int page,
                       ModelMap model) {
        CategoryProduct productInCategory = productService.getProductInCategory(categoryId, page, 10);
        model.addAttribute("productInCategory", productInCategory);
        return "category/productList";
    }

    @RequestMapping("/products/{productId}")
    public String detail(@PathVariable("productId") String productId,
                         ModelMap model,
                         HttpServletResponse response) throws IOException {
        Optional<Product> product = productService.getProduct(productId);
        if (product.isPresent()) {
            model.addAttribute("product", product.get());
            return "category/productDetail";
        } else {
            response.sendError(HttpServletResponse.SC_NOT_FOUND);
            return null;
        }
    }

    @Autowired
    public void setProductService(ProductService productService) {
        this.productService = productService;
    }

    @Autowired
    public void setCategoryRepository(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }
}
