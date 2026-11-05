package co.edu.unicauca.saberpro.question.domain.port;

import co.edu.unicauca.saberpro.question.domain.model.Question;

import java.util.List;

/** Puerto de salida hacia el Validation Service. */
public interface QuestionValidationPort {

    ValidationResult validate(Question question);

    record ValidationResult(boolean valid, List<String> errors) {
    }
}
