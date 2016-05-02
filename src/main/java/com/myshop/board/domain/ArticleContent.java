package com.myshop.board.domain;

import javax.persistence.Embeddable;

@Embeddable
public class ArticleContent {
    private String content;
    private String contentType;

    private ArticleContent() {
    }

    public ArticleContent(String content, String contentType) {
        this.content = content;
        this.contentType = contentType;
    }

    public String getContent() {
        return content;
    }

    public String getContentType() {
        return contentType;
    }
}
