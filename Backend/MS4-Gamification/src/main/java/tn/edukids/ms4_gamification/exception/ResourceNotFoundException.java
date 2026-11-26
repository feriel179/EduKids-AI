package tn.edukids.ms4_gamification.exception;

public class ResourceNotFoundException extends RuntimeException {
    public ResourceNotFoundException(String resource, Long id) {
        super(resource + " introuvable : " + id);
    }
}
