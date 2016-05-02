package com.myshop.catalog.infra.repository;

import com.myshop.SpringIntTestConfig;
import com.myshop.catalog.domain.category.CategoryId;
import com.myshop.catalog.domain.product.*;
import com.myshop.common.model.Money;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import static org.hamcrest.Matchers.*;
import static org.junit.Assert.assertThat;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringIntTestConfig
@Transactional
@Rollback(false)
public class JpaProductRepositoryIntTest {
    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private EntityManager entityManager;

    @Test
    public void find() throws Exception {
        Product product = productRepository.findById(new ProductId("prod-001"));
        assertThat(product, notNullValue());
        assertThat(product.getImages(), hasSize(2));
        assertThat(product.getImages().get(0), instanceOf(InternalImage.class));
        assertThat(product.getImages().get(1), instanceOf(ExternalImage.class));
    }

    @Test
    public void findAll() throws Exception {
        List<Product> producs = productRepository.findAll();
        assertThat(producs, hasSize(3));
    }

    @Test
    public void save() throws Exception {
        Product product = new Product(new ProductId("prod-999"), "999", new Money(999), "detail",
                Arrays.asList(new ExternalImage("externalPath"),
                        new InternalImage("internal1"),
                        new InternalImage("internal2")));
        productRepository.save(product);

        flush();

        List<Map<String, Object>> rows = jdbcTemplate.queryForList(
                "select * from image where product_id = ? order by list_idx asc", "prod-999");
        assertThat(rows, hasSize(3));
        assertThat(rows.get(0).get("image_type"), equalTo("EI"));
        assertThat(rows.get(0).get("image_path"), equalTo("externalPath"));
        assertThat(rows.get(1).get("image_type"), equalTo("II"));
        assertThat(rows.get(1).get("image_path"), equalTo("internal1"));
        assertThat(rows.get(2).get("image_type"), equalTo("II"));
        assertThat(rows.get(2).get("image_path"), equalTo("internal2"));
    }

    private void flush() {
        entityManager.flush();
    }

    @Test
    public void deleteOrphan() throws Exception {
        Product product = productRepository.findById(new ProductId("prod-002"));
        List<Image> newImages = Collections.emptyList();
        product.changeImages(newImages);

        flush();

        List<Map<String, Object>> rows = jdbcTemplate.queryForList(
                "select * from image where product_id = ? order by list_idx asc", "prod-002");
        assertThat(rows, hasSize(0));
    }

    @Test
    public void remove() throws Exception {
        Product product = productRepository.findById(new ProductId("prod-001"));
        productRepository.remove(product);

        flush();


        Number productCnt = jdbcTemplate.queryForObject(
                "select count(*) from product where product_id = ?",
                Number.class,
                "prod-001"
        );
        assertThat(productCnt.intValue(), equalTo(0));

        Number imageCount = jdbcTemplate.queryForObject(
                "select count(*) from image where product_id = ?",
                Number.class,
                "prod-001"
        );
        assertThat(imageCount.intValue(), equalTo(0));
    }

    @Test
    public void findByCategoryId() throws Exception {
        List<Product> productsInCat2001 = productRepository.findByCategoryId(new CategoryId(2001L), 1, 5);
        assertThat(productsInCat2001, hasSize(2));
        assertThat(productsInCat2001.get(0).getId(), equalTo(new ProductId("prod-003")));
        assertThat(productsInCat2001.get(1).getId(), equalTo(new ProductId("prod-002")));

        List<Product> productsInCat1001 = productRepository.findByCategoryId(new CategoryId(1001L), 1, 5);
        assertThat(productsInCat1001, hasSize(2));
    }

    @Test
    public void countsByCategoryId() throws Exception {
        assertThat(productRepository.countsByCategoryId(new CategoryId(2001L)), equalTo(2L));
        assertThat(productRepository.countsByCategoryId(new CategoryId(1001L)), equalTo(2L));
    }
}
