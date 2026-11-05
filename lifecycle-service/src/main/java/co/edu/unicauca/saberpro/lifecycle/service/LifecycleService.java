package co.edu.unicauca.saberpro.lifecycle.service;

import co.edu.unicauca.saberpro.lifecycle.messaging.SubmitQuestionForReview;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

/** Autoridad centralizada del estado de las preguntas (DRAFT -> PENDING_REVIEW). */
@Service
public class LifecycleService {

    public static final String DRAFT = "DRAFT";
    public static final String PENDING_REVIEW = "PENDING_REVIEW";
    public static final String COMPLETED = "COMPLETED";

    private static final Logger log = LoggerFactory.getLogger(LifecycleService.class);

    private final Map<Long, String> questionStates = new ConcurrentHashMap<>();
    private final Map<String, String> operations = new ConcurrentHashMap<>();

    public void submitForReview(SubmitQuestionForReview command) {
        // Idempotencia: un mismo operationId no se procesa dos veces
        if (operations.putIfAbsent(command.operationId(), COMPLETED) != null) {
            log.info("Operacion {} ya procesada, se ignora", command.operationId());
            return;
        }
        questionStates.put(command.questionId(), PENDING_REVIEW);
        log.info("Pregunta {} -> {}", command.questionId(), PENDING_REVIEW);
    }

    /** Si la pregunta nunca fue enviada a revision, su estado sigue siendo DRAFT. */
    public String stateOf(Long questionId) {
        return questionStates.getOrDefault(questionId, DRAFT);
    }

    public Optional<String> operationStatus(String operationId) {
        return Optional.ofNullable(operations.get(operationId));
    }
}
