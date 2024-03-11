package kz.alabs.shopapi.core.openapi;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;

@OpenAPIDefinition(
        info = @Info(
                title = "Shop API",
                description = "Products delivery service"
        )
)
public class OpenApiConfig {
}
