package org.example.spanishbanksquarterinfoapi.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;
import org.springframework.context.annotation.Configuration;

@OpenAPIDefinition(
        info = @Info(
                title = "Spanish Banks Public Quarter Info API",
                description = "API for accessing the quarterly financial information that Spanish banks are legally required to publish",
                version = "1.0.0",
                contact = @Contact(
                        name = "Saul Ortega", url = "https://saulortega.dev"
                ),
                license = @License(
                        name = "MIT License", url = "https://opensource.org/licenses/MIT"
                )
        )
)
@Configuration
public class OpenAPIConfig {
}
