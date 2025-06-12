package com.org.kodvix.redbooks.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.servers.Server;
import io.swagger.v3.oas.models.tags.Tag;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI apiInfo() {
        return new OpenAPI()
                .info(new Info()
                        .title("RedBooks API")
                        .description("API documentation for RedBooks system")
                        .version("1.0")
                )
                .servers(List.of(
                        new Server().url("http://localhost:8080").description("Local Dev Server"),
                        new Server().url("https://api.yourdomain.com").description("Production Server")
                ))
                .tags(List.of(
                        new Tag().name("Authentication API").description("Endpoints for user registration and login"),
                        new Tag().name("Customer API").description("Endpoints for customers (parents/students)"),
                        new Tag().name("School API").description("Endpoints for schools"),
                        new Tag().name("Publisher API").description("Endpoints for publishers")
                ));
    }
}
