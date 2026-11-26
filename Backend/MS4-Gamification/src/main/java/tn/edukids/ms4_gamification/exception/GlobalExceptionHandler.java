package tn.edukids.ms4_gamification.exception;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(ResourceNotFoundException.class)
    ResponseEntity<ProblemDetail> notFound(ResourceNotFoundException ex) {
        return problem(HttpStatus.NOT_FOUND, ex.getMessage());
    }

    @ExceptionHandler({ConflictException.class, DataIntegrityViolationException.class})
    ResponseEntity<ProblemDetail> conflict(Exception ex) {
        String detail = ex instanceof DataIntegrityViolationException
                ? "Une contrainte de la base de données empêche cette opération" : ex.getMessage();
        return problem(HttpStatus.CONFLICT, detail);
    }

    @ExceptionHandler({InvalidRequestException.class, HttpMessageNotReadableException.class})
    ResponseEntity<ProblemDetail> invalid(Exception ex) {
        String detail = ex instanceof HttpMessageNotReadableException
                ? "Corps JSON invalide ou valeur d'énumération inconnue" : ex.getMessage();
        return problem(HttpStatus.BAD_REQUEST, detail);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    ResponseEntity<ProblemDetail> validation(MethodArgumentNotValidException ex) {
        String detail = ex.getBindingResult().getFieldErrors().stream()
                .map(error -> error.getField() + " : " + error.getDefaultMessage())
                .findFirst().orElse("Requête invalide");
        return problem(HttpStatus.BAD_REQUEST, detail);
    }

    private ResponseEntity<ProblemDetail> problem(HttpStatus status, String detail) {
        ProblemDetail problem = ProblemDetail.forStatusAndDetail(status, detail);
        return ResponseEntity.status(status).body(problem);
    }
}
