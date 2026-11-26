package tn.esprit.autoloc.webdistribue.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import tn.esprit.autoloc.webdistribue.dto.QuizEleveResponse;
import tn.esprit.autoloc.webdistribue.dto.QuizRequest;
import tn.esprit.autoloc.webdistribue.dto.QuizResponse;
import tn.esprit.autoloc.webdistribue.entities.StatutQuiz;
import tn.esprit.autoloc.webdistribue.services.QuizService;

import java.util.List;

@RestController
@RequestMapping("/api/quiz")
@RequiredArgsConstructor
@Tag(name = "1. Quiz", description = "Création et gestion des quiz (enseignant)")
public class QuizController {

    private final QuizService quizService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Créer un quiz (en BROUILLON), éventuellement avec ses questions et choix")
    public QuizResponse creer(@Valid @RequestBody QuizRequest req) {
        return quizService.creer(req);
    }

    @GetMapping
    @Operation(summary = "Lister les quiz", description = "Filtres optionnels : chapitre (MS1) et statut.")
    public List<QuizResponse> lister(@RequestParam(required = false) Long chapitreId,
                                     @RequestParam(required = false) StatutQuiz statut) {
        return quizService.lister(chapitreId, statut);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Détail d'un quiz (vue enseignant, avec les bonnes réponses)")
    public QuizResponse getById(@PathVariable Long id) {
        return quizService.getById(id);
    }

    @GetMapping("/{id}/passer")
    @Operation(summary = "Récupérer un quiz publié pour le passer (vue élève, sans les bonnes réponses)")
    public QuizEleveResponse passer(@PathVariable Long id) {
        return quizService.getPourEleve(id);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Modifier un quiz (tant qu'aucun élève ne l'a passé)",
            description = "Si 'questions' est fourni, il remplace toutes les questions ; s'il est absent (null), elles sont conservées.")
    public QuizResponse modifier(@PathVariable Long id, @Valid @RequestBody QuizRequest req) {
        return quizService.modifier(id, req);
    }

    @PatchMapping("/{id}/statut")
    @Operation(summary = "Changer le statut (BROUILLON / PUBLIE / ARCHIVE)",
            description = "PUBLIE vérifie que chaque question est cohérente. Retour en BROUILLON interdit si des élèves ont déjà passé le quiz.")
    public QuizResponse changerStatut(@PathVariable Long id,
                                      @Parameter(description = "Nouveau statut") @RequestParam StatutQuiz valeur) {
        return quizService.changerStatut(id, valeur);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(summary = "Supprimer un quiz (avec ses questions et tentatives)")
    public void supprimer(@PathVariable Long id) {
        quizService.supprimer(id);
    }
}
