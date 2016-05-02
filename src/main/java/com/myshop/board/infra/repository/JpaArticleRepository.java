package com.myshop.board.infra.repository;

import org.springframework.stereotype.Repository;
import com.myshop.board.domain.Article;
import com.myshop.board.domain.ArticleRepository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Repository
public class JpaArticleRepository implements ArticleRepository {
    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public void save(Article article) {
        entityManager.persist(article);
    }

    @Override
    public Article findById(Long id) {
        return entityManager.find(Article.class, id);
    }
}
