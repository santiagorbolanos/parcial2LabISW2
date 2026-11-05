package co.edu.unicauca.saberpro.validation;

import co.edu.unicauca.saberpro.validation.dto.OptionDto;
import co.edu.unicauca.saberpro.validation.dto.ValidationRequest;
import co.edu.unicauca.saberpro.validation.service.QuestionValidator;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class QuestionValidatorTest {

    private final QuestionValidator validator = new QuestionValidator();

    private static List<OptionDto> four() {
        return List.of(new OptionDto("A", "uno"), new OptionDto("B", "dos"),
                new OptionDto("C", "tres"), new OptionDto("D", "cuatro"));
    }

    @Test
    void validQuestionHasNoErrors() {
        var req = new ValidationRequest("Enunciado", four(), "B",
                "La opcion B es correcta porque cumple el criterio.");
        assertTrue(validator.validate(req).isEmpty());
    }

    @Test
    void threeOptionsFails() {
        var req = new ValidationRequest("Enunciado", four().subList(0, 3), "A",
                "La opcion A es correcta porque cumple el criterio.");
        assertEquals(1, validator.validate(req).size());
    }

    @Test
    void missingKeyAndShortJustificationFail() {
        var req = new ValidationRequest("Enunciado", four(), "Z", "corta");
        assertEquals(2, validator.validate(req).size());
    }
}
