package co.edu.unicauca.saberpro.question.application;

import co.edu.unicauca.saberpro.question.application.command.SubmitQuestionForReview;
import co.edu.unicauca.saberpro.question.domain.model.Question;
import co.edu.unicauca.saberpro.question.domain.port.QuestionValidationPort;
import co.edu.unicauca.saberpro.question.domain.port.ReviewCommandPublisher;
import co.edu.unicauca.saberpro.question.domain.repository.QuestionRepository;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

@Service
public class QuestionService {

    private final QuestionRepository repository;
    private final QuestionValidationPort validationPort;
    private final ReviewCommandPublisher publisher;

    public QuestionService(QuestionRepository repository,
                           QuestionValidationPort validationPort,
                           ReviewCommandPublisher publisher) {
        this.repository = repository;
        this.validationPort = validationPort;
        this.publisher = publisher;
    }

    /** Punto 1: valida de forma SINCRONA (REST) antes de guardar. */
    public Question create(Question question) {
        QuestionValidationPort.ValidationResult result = validationPort.validate(question);
        if (!result.valid()) {
            throw new QuestionValidationException(result.errors());
        }
        question.assignDraft();
        return repository.save(question);
    }

    public Question get(Long id) {
        return repository.findById(id).orElseThrow(() -> new QuestionNotFoundException(id));
    }

    public List<Question> list() {
        return repository.findAll();
    }

    /** Punto 3: publica el comando de forma ASINCRONA y devuelve el operationId. */
    public String submitForReview(Long id) {
        Question question = get(id);
        String operationId = UUID.randomUUID().toString();
        publisher.publish(new SubmitQuestionForReview(operationId, question.getId(), Instant.now().toString()));
        return operationId;
    }
}
