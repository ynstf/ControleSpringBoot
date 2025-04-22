package ma.enset.reservation.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.servers.Server;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import java.util.List;

@Configuration
public class SwaggerConfig {
    @Value("${api.version}")
    private String apiVersion;

    @Bean
    public OpenAPI openAPI() {
        Server server = new Server();
        // Set the correct protocol (https://) and host

        return new OpenAPI()
                .servers(List.of(server))
                .info(new Info()
                        .title("M3allem API")
                        .version(apiVersion)
                        .description("Freelancer API for M3allem"))
                .addSecurityItem(new SecurityRequirement().addList("bearerAuth"))
                .components(new io.swagger.v3.oas.models.Components()
                        .addSecuritySchemes("bearerAuth",
                                new SecurityScheme()
                                        .name("bearerAuth")
                                        .type(SecurityScheme.Type.HTTP)
                                        .scheme("bearer")
                                        .bearerFormat("JWT")));
    }
}
