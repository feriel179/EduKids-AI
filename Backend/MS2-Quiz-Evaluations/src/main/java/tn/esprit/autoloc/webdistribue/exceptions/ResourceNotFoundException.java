package tn.esprit.autoloc.webdistribue.exceptions;

public class ResourceNotFoundException extends RuntimeException {
    public ResourceNotFoundException(String ressource, Object id) {
        super(ressource + " introuvable : " + id);
    }
}
