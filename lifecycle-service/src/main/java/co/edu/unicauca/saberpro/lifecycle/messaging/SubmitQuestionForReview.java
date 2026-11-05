package co.edu.unicauca.saberpro.lifecycle.messaging;

/** Contrato del comando (mismo JSON que publica el Question Service). */
public record SubmitQuestionForReview(String operationId, Long questionId, String requestedAt) {
}
