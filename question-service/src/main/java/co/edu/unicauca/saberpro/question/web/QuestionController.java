package co.edu.unicauca.saberpro.question.web;

import co.edu.unicauca.saberpro.question.application.QuestionService;
import co.edu.unicauca.saberpro.question.domain.model.AnswerOption;
import co.edu.unicauca.saberpro.question.domain.model.Question;
import co.edu.unicauca.saberpro.question.web.dto.CreateQuestionRequest;
import co.edu.unicauca.saberpro.question.web.dto.QuestionResponse;
import co.edu.unicauca.saberpro.question.web.dto.SubmitResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/questions")
public class QuestionController {

    private final QuestionService service;

    public QuestionController(QuestionService service) {
        this.service = service;
    }

    /** 201 Created si el Validation Service aprueba; 400 Bad Request con la lista de errores si no. */
    @PostMapping
    public ResponseEntity<QuestionResponse> create(@RequestBody CreateQuestionRequest request) {
        List<AnswerOption> options = new ArrayList<>();
        if (request.options() != null) {
            request.options().forEach(o -> {
                if (o != null) {
                    options.add(new AnswerOption(o.key(), o.text()));
                }
            });
        }
        Question question = new Question(null, request.statement(), options,
                request.correctKey(), request.justification(), null);
        Question saved = service.create(question);
        return ResponseEntity.status(HttpStatus.CREATED).body(QuestionResponse.from(saved));
    }

    @GetMapping("/{id}")
    public QuestionResponse get(@PathVariable Long id) {
        return QuestionResponse.from(service.get(id));
    }

    @GetMapping
    public List<QuestionResponse> list() {
        return service.list().stream().map(QuestionResponse::from).toList();
    }

    /** 202 Accepted inmediato: el comando se procesa en segundo plano en el Lifecycle Service. */
    @PostMapping("/{id}/submit")
    public ResponseEntity<SubmitResponse> submit(@PathVariable Long id) {
        String operationId = service.submitForReview(id);
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(new SubmitResponse(operationId, "PROCESSING"));
    }
}
