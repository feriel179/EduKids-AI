package tn.esprit.autoloc.webdistribue.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import java.util.List;

/**
 * Copie rendue par l'élève. Les questionId / choixIds se récupèrent avec GET /api/quiz/{id}/passer.
 */
@Schema(description = "Les questionId / choixIds se récupèrent avec GET /api/quiz/{id}/passer. "
        + "L'exemple correspond au quiz de démo (id 1) sur une base neuve : il donne 20/20.",
        example = """
        {
          "eleveId": 1,
          "reponses": [
            { "questionId": 1, "choixIds": [2] },
            { "questionId": 2, "choixIds": [4] },
            { "questionId": 3, "choixIds": [6, 8] },
            { "questionId": 4, "texteLibre": "sommes" }
          ]
        }
        """)
public record SoumissionRequest(
        @Schema(description = "Identifiant de l'élève (Keycloak)")
        @NotNull Long eleveId,
        @NotEmpty @Valid List<ReponseRequest> reponses
) {
    /**
     * @param choixIds   choix cochés (1 pour QCM_UNIQUE / VRAI_FAUX, 1..n pour QCM_MULTIPLE)
     * @param texteLibre réponse écrite pour TEXTE_LIBRE
     */
    public record ReponseRequest(
            @NotNull Long questionId,
            @Schema(description = "Choix cochés : 1 pour QCM_UNIQUE / VRAI_FAUX, plusieurs pour QCM_MULTIPLE")
            List<Long> choixIds,
            @Schema(description = "Réponse écrite, uniquement pour TEXTE_LIBRE")
            String texteLibre
    ) {}
}
