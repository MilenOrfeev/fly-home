package com.flysafe.config;

import com.google.common.collect.Sets;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.HashSet;

@Configuration
@EnableSwagger2
public class SwaggerConfig {
    private static final HashSet<String> mimeTypes = Sets.newHashSet(
            MediaType.APPLICATION_JSON_VALUE
    );

    @Bean
    public Docket api() {
        return new Docket(DocumentationType.SWAGGER_2)
                .consumes(mimeTypes)
                .produces(mimeTypes)
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.flysafe.controller"))
                .paths(PathSelectors.ant("/api/v1/*"))
                .build();
    }

    @Controller
    public class SwaggerController {

        @RequestMapping(value = "/")
        public String redirectToSwagger() {
            return "redirect:swagger-ui.html";
        }
    }

}