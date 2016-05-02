package com.myshop.board.infra.repository;

import com.myshop.SpringIntTestConfig;
import com.myshop.board.domain.Article;
import com.myshop.board.domain.ArticleContent;
import com.myshop.board.domain.ArticleRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;
import static org.junit.Assert.assertThat;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringIntTestConfig
@Transactional
@Rollback(false)
public class JpaArticleRepositoryIntTest {
    @Autowired
    private ArticleRepository articleRepository;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Test
    public void saveArticle() throws Exception {
        Article article = new Article("제목", new ArticleContent("content", "type"));
        articleRepository.save(article);

        Long savedArticleId = article.getId();

        List<Map<String, Object>> rows = jdbcTemplate.queryForList(
                "select * from article_content where id = ?", savedArticleId);
        assertThat(
                ((Number) rows.get(0).get("id")).longValue(),
                equalTo(savedArticleId.longValue()));
    }

    @Test
    public void find() {
        Article article = articleRepository.findById(1L);
        assertThat(article, notNullValue());
    }
}
