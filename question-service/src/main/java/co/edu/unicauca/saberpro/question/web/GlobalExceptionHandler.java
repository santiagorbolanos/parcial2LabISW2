package co.edu.unicauca.saberpro.question.web;

import co.edu.unicauca.saberpro.question.application.QuestionNotFoundException;
import co.edu.unicauca.saberpro.question.application.QuestionValidationException;
import co.edu.unicauca.saberpro.question.application.ValidationServiceUnavailableException;
import org.springframework.amqp.AmqpException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(QuestionValidationException.class)
    public ResponseEntity<Map<String, Object>> handleValidation(QuestionValidationException e) {
        return ResponseEntity.badRequest().body(Map.of(
                "status", 400,
                "message", e.getMessage(),
                "errors", e.getErrors()));
    }

    @ExceptionHandler(QuestionNotFoundException.class)
    public ResponseEntity<Map<String, Object>> handleNotFound(QuestionNotFoundException e) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of(
                "status", 404, "message", e.getMessage(), "errors", List.of()));
    }

    @ExceptionHandler({ValidationServiceUnavailableException.class, AmqpException.class})
    public ResponseEntity<Map<String, Object>> handleUnavailable(Exception e) {
        return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).body(Map.of(
                "status", 503, "message", e.getMessage() == null ? "Servicio no disponible" : e.getMessage(),
                "errors", List.of()));
    }
}
