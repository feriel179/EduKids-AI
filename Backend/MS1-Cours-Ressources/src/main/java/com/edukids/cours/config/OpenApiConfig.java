
package com.edukids.cours.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("EduKids AI - MS1 Cours & Ressources")
                        .version("1.0.0")
                        .description(
                                "API REST du microservice MS1 " +
                                        "pour la gestion des cours, " +
                                        "des niveaux, des matières " +
                                        "et des ressources pédagogiques."
                        ));
    }
}
