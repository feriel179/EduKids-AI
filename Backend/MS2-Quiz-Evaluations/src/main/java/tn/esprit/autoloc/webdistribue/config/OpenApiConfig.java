package tn.esprit.autoloc.webdistribue.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI quizOpenApi() {
        return new OpenAPI().info(new Info()
                .title("MS2 — Quiz & Évaluations")
                .version("1.0.0")
                .description("Création de quiz par chapitre, correction automatique, calcul des notes "
                        + "et historique des résultats des élèves (plateforme éducative primaire).")
                .contact(new Contact().name("Mohamed Aziz Latrache")));
    }
}
