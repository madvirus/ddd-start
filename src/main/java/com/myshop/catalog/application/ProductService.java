package com.myshop.catalog.application;

import com.myshop.catalog.domain.category.Category;
import com.myshop.catalog.domain.category.CategoryId;
import com.myshop.catalog.domain.category.CategoryRepository;
import com.myshop.catalog.domain.product.Product;
import com.myshop.catalog.domain.product.ProductId;
import com.myshop.catalog.domain.product.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

import static java.util.stream.Collectors.toList;

@Service
public class ProductService {
    private ProductRepository productRepository;
    private CategoryRepository categoryRepository;

    @Transactional
    public CategoryProduct getProductInCategory(Long categoryId, int page, int size) {
        Category category = categoryRepository.findById(new CategoryId(categoryId));
        if (category == null) throw new NoCategoryException();

        List<Product> products = productRepository.findByCategoryId(category.getId(), page, size);
        return new CategoryProduct(category, toSummary(products), page, size, productRepository.countsByCategoryId(category.getId()));
    }

    private List<ProductSummary> toSummary(List<Product> products) {
        return products.stream().map(
                prod -> new ProductSummary(
                        prod.getId().getId(),
                        prod.getName(),
                        prod.getPrice().getValue(),
                        prod.getFirstIamgeThumbnailPath())).collect(toList());
    }

    public Optional<Product> getProduct(String productId) {
        Product product = productRepository.findById(new ProductId(productId));
        return Optional.ofNullable(product);
    }

    @Autowired
    public void setProductRepository(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Autowired
    public void setCategoryRepository(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }
}
