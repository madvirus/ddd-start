package com.myshop.board.domain;

public interface ArticleRepository {
    void save(Article article);
    Article findById(Long id);
}
