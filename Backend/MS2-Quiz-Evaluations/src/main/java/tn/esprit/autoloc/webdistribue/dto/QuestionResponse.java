package tn.esprit.autoloc.webdistribue.dto;

import tn.esprit.autoloc.webdistribue.entities.TypeQuestion;

import java.util.List;

public record QuestionResponse(
        Long id, String enonce, TypeQuestion type, Float points, Integer ordre,
        List<ChoixResponse> choix
) {}
