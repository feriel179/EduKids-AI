package tn.esprit.autoloc.webdistribue.entities;

public enum TypeQuestion {
    /** Une seule bonne réponse parmi plusieurs choix. */
    QCM_UNIQUE,
    /** Plusieurs bonnes réponses : l'élève doit toutes les cocher (et seulement elles). */
    QCM_MULTIPLE,
    /** Deux choix (Vrai / Faux), un seul correct. */
    VRAI_FAUX,
    /** L'élève écrit sa réponse ; les choix marqués corrects sont les réponses acceptées. */
    TEXTE_LIBRE
}
