package tn.esprit.autoloc.webdistribue.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import tn.esprit.autoloc.webdistribue.dto.ChoixRequest;
import tn.esprit.autoloc.webdistribue.dto.ChoixResponse;
import tn.esprit.autoloc.webdistribue.dto.QuestionRequest;
import tn.esprit.autoloc.webdistribue.dto.QuestionResponse;
import tn.esprit.autoloc.webdistribue.services.QuestionService;

import java.util.List;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
@Tag(name = "2. Questions & choix", description = "Contenu d'un quiz (modifiable tant qu'aucun élève ne l'a passé)")
public class QuestionController {

    private final QuestionService questionService;

    @GetMapping("/quiz/{quizId}/questions")
    @Operation(summary = "Questions d'un quiz (triées par ordre)")
    public List<QuestionResponse> lister(@PathVariable Long quizId) {
        return questionService.listerParQuiz(quizId);
    }

    @PostMapping("/quiz/{quizId}/questions")
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Ajouter une question (avec ses choix) à un quiz")
    public QuestionResponse ajouter(@PathVariable Long quizId, @Valid @RequestBody QuestionRequest req) {
        return questionService.ajouter(quizId, req);
    }

    @GetMapping("/questions/{id}")
    @Operation(summary = "Détail d'une question")
    public QuestionResponse getById(@PathVariable Long id) {
        return questionService.getById(id);
    }

    @PutMapping("/questions/{id}")
    @Operation(summary = "Modifier une question", description = "Si 'choix' est fourni, il remplace tous les choix.")
    public QuestionResponse modifier(@PathVariable Long id, @Valid @RequestBody QuestionRequest req) {
        return questionService.modifier(id, req);
    }

    @DeleteMapping("/questions/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(summary = "Supprimer une question")
    public void supprimer(@PathVariable Long id) {
        questionService.supprimer(id);
    }

    @PostMapping("/questions/{questionId}/choix")
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Ajouter un choix à une question")
    public ChoixResponse ajouterChoix(@PathVariable Long questionId, @Valid @RequestBody ChoixRequest req) {
        return questionService.ajouterChoix(questionId, req);
    }

    @PutMapping("/choix/{id}")
    @Operation(summary = "Modifier un choix")
    public ChoixResponse modifierChoix(@PathVariable Long id, @Valid @RequestBody ChoixRequest req) {
        return questionService.modifierChoix(id, req);
    }

    @DeleteMapping("/choix/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(summary = "Supprimer un choix")
    public void supprimerChoix(@PathVariable Long id) {
        questionService.supprimerChoix(id);
    }
}
