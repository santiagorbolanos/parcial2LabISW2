package co.edu.unicauca.saberpro.question.application.command;

/** Comando publicado a RabbitMQ para solicitar el paso a PENDING_REVIEW. */
public record SubmitQuestionForReview(String operationId, Long questionId, String requestedAt) {
}
