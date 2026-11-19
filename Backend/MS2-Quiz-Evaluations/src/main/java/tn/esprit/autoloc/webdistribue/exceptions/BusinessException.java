package tn.esprit.autoloc.webdistribue.exceptions;

/** Règle métier violée (ex : quiz non publié, choix n'appartenant pas à la question...). */
public class BusinessException extends RuntimeException {
    public BusinessException(String message) {
        super(message);
    }
}
