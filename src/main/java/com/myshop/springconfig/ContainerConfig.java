package com.myshop.springconfig;

import org.springframework.boot.context.embedded.EmbeddedServletContainerCustomizer;
import org.springframework.boot.context.embedded.ErrorPage;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;

@Configuration
public class ContainerConfig {
    @Bean
    public EmbeddedServletContainerCustomizer containerCustomizer() {
        return container -> container.addErrorPages(
                new ErrorPage(HttpStatus.FORBIDDEN, "/error/forbidden"),
                new ErrorPage(HttpStatus.NOT_FOUND, "/error/notFound")
        );
    }
}
