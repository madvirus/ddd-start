package com.myshop.catalog.infra.repository;

import com.myshop.catalog.domain.category.Category;
import com.myshop.catalog.domain.category.CategoryId;
import com.myshop.catalog.domain.category.CategoryRepository;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.List;

@Repository
public class JpaCategoryRepository implements CategoryRepository {
    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Category findById(CategoryId id) {
        return entityManager.find(Category.class, id);
    }

    @Override
    public List<Category> findAll() {
        TypedQuery<Category> query = entityManager.createQuery("select c from Category c order by c.id.value asc", Category.class);
        return query.getResultList();
    }
}
