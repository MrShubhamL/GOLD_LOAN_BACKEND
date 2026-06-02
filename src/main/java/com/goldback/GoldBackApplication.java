package com.goldback;

import io.swagger.v3.oas.annotations.ExternalDocumentation;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@OpenAPIDefinition(
        info = @Info(
                title = "Gold Backend project REST API Documentation 2026",
                description = "Gold Backend application REST API Documentation and developed by Pruthviraj Patil",
                version="v1",
                contact = @Contact(
                        name = "Code-crafter",
                        email = "info@code-crafter.in",
                        url = "https://code-crafter.in"
                )
        ),
        externalDocs = @ExternalDocumentation(
                description = "UMBRELLA application REST API Documentation",
                url = "https://code-crafter.in"
        )
)
@SpringBootApplication
public class GoldBackApplication {

    public static void main(String[] args) {
        SpringApplication.run(GoldBackApplication.class, args);
    }

}
