package tn.esprit.autoloc.webdistribue.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import tn.esprit.autoloc.webdistribue.dto.ResultatResponse;
import tn.esprit.autoloc.webdistribue.dto.SoumissionRequest;
import tn.esprit.autoloc.webdistribue.dto.StatistiquesQuizResponse;
import tn.esprit.autoloc.webdistribue.dto.TentativeResponse;
import tn.esprit.autoloc.webdistribue.services.TentativeService;

import java.util.List;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
@Tag(name = "3. Tentatives & notes", description = "Passage du quiz, correction automatique, notes et historique")
public class TentativeController {

    private final TentativeService tentativeService;

    @PostMapping("/quiz/{quizId}/tentatives")
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Soumettre les réponses d'un élève",
            description = "Corrige automatiquement, calcule la note sur 20 et renvoie la correction détaillée.")
    public ResultatResponse soumettre(@PathVariable Long quizId, @Valid @RequestBody SoumissionRequest req) {
        return tentativeService.soumettre(quizId, req);
    }

    @GetMapping("/tentatives/{id}")
    @Operation(summary = "Résultat détaillé d'une tentative (note + correction)")
    public ResultatResponse resultat(@PathVariable Long id) {
        return tentativeService.getResultat(id);
    }

    @GetMapping("/eleves/{eleveId}/tentatives")
    @Operation(summary = "Historique des résultats d'un élève", description = "Filtre optionnel par quiz. Plus récent en premier.")
    public List<TentativeResponse> historiqueEleve(@PathVariable Long eleveId,
                                                  @RequestParam(required = false) Long quizId) {
        return tentativeService.historiqueEleve(eleveId, quizId);
    }

    @GetMapping("/quiz/{quizId}/tentatives")
    @Operation(summary = "Toutes les tentatives d'un quiz (vue enseignant)")
    public List<TentativeResponse> tentativesDuQuiz(@PathVariable Long quizId) {
        return tentativeService.tentativesDuQuiz(quizId);
    }

    @GetMapping("/quiz/{quizId}/statistiques")
    @Operation(summary = "Statistiques d'un quiz : moyenne, min, max, taux de réussite (note >= 10/20)")
    public StatistiquesQuizResponse statistiques(@PathVariable Long quizId) {
        return tentativeService.statistiques(quizId);
    }

    @DeleteMapping("/tentatives/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(summary = "Supprimer une tentative")
    public void supprimer(@PathVariable Long id) {
        tentativeService.supprimer(id);
    }
}
