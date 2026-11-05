package co.edu.unicauca.saberpro.question.web.dto;

import java.util.List;

/** Sin Bean Validation: la validacion estructural es responsabilidad del Validation Service. */
public record CreateQuestionRequest(String statement,
                                    List<OptionDto> options,
                                    String correctKey,
                                    String justification) {
}
