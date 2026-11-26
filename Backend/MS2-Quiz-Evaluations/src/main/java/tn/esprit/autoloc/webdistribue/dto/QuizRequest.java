package tn.esprit.autoloc.webdistribue.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.util.List;

/** Création / modification d'un quiz. Les questions sont optionnelles (on peut les ajouter ensuite). */
@Schema(example = """
        {
          "titre": "Maths — Additions (3ème primaire)",
          "chapitreId": 2,
          "dureeMin": 10,
          "questions": [
            {
              "enonce": "3 + 4 = ?",
              "type": "QCM_UNIQUE",
              "points": 5,
              "choix": [
                { "texte": "6", "estCorrect": false },
                { "texte": "7", "estCorrect": true },
                { "texte": "8", "estCorrect": false }
              ]
            },
            {
              "enonce": "10 + 10 = 20",
              "type": "VRAI_FAUX",
              "points": 5,
              "choix": [
                { "texte": "Vrai", "estCorrect": true },
                { "texte": "Faux", "estCorrect": false }
              ]
            },
            {
              "enonce": "Quels nombres sont pairs ?",
              "type": "QCM_MULTIPLE",
              "points": 5,
              "choix": [
                { "texte": "2", "estCorrect": true },
                { "texte": "3", "estCorrect": false },
                { "texte": "8", "estCorrect": true }
              ]
            },
            {
              "enonce": "Écris en lettres : 10",
              "type": "TEXTE_LIBRE",
              "points": 5,
              "choix": [ { "texte": "dix", "estCorrect": true } ]
            }
          ]
        }
        """)
public record QuizRequest(
        @NotBlank String titre,
        @Schema(description = "Identifiant du chapitre dans MS1 (nombre)")
        @NotNull Long chapitreId,
        @Positive @Max(180) Integer dureeMin,
        @Schema(description = "Optionnel. En modification (PUT) : si fourni, remplace toutes les questions ; si absent, elles sont gardées")
        @Valid List<QuestionRequest> questions
) {}
