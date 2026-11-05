package co.edu.unicauca.saberpro.question.application;

public class ValidationServiceUnavailableException extends RuntimeException {
    public ValidationServiceUnavailableException(String message, Throwable cause) {
        super(message, cause);
    }
}
