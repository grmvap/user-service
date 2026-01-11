package com.example.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;
import io.swagger.v3.oas.annotations.servers.Server;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.media.Schema;
import org.springdoc.core.utils.SpringDocUtils;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Configuration
@OpenAPIDefinition(
        info = @Info(
                title = "User Service API",
                version = "1.0.0",
                description = "Microservice for user management with Kafka integration",
                contact = @Contact(
                        name = "User Service Team",
                        email = "support@userservice.com",
                        url = "https://github.com/grmvap/user-service"
                ),
                license = @License(
                        name = "Apache 2.0",
                        url = "https://www.apache.org/licenses/LICENSE-2.0"
                ),
                termsOfService = "https://github.com/grmvap/user-service/terms"
        ),
        servers = {
                @Server(
                        url = "http://localhost:8080",
                        description = "Local Development Server"
                ),
                @Server(
                        url = "https://api.userservice.com",
                        description = "Production Server"
                )
        }
)
public class OpenApiConfig {

    static {
        SpringDocUtils.getConfig().replaceWithSchema(LocalDateTime.class,
                new Schema<LocalDateTime>()
                        .example(LocalDateTime.now().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME))
                        .type("string")
                        .format("date-time")
        );
    }

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new io.swagger.v3.oas.models.info.Info()
                        .title("User Service API")
                        .version("1.0.0")
                        .description("REST API for user management with HATEOAS support")
                        .contact(new io.swagger.v3.oas.models.info.Contact()
                                .name("API Support")
                                .email("support@userservice.com")
                                .url("https://github.com/grmvap"))
                        .license(new io.swagger.v3.oas.models.info.License()
                                .name("Apache 2.0")
                                .url("http://springdoc.org")));
    }
}