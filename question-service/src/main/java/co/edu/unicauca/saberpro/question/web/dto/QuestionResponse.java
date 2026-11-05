package co.edu.unicauca.saberpro.question.web.dto;

import co.edu.unicauca.saberpro.question.domain.model.Question;

import java.util.List;

public record QuestionResponse(Long id, String statement, List<OptionDto> options,
                               String correctKey, String justification, String status) {

    public static QuestionResponse from(Question q) {
        return new QuestionResponse(q.getId(), q.getStatement(),
                q.getOptions().stream().map(o -> new OptionDto(o.key(), o.text())).toList(),
                q.getCorrectKey(), q.getJustification(), q.getStatus().name());
    }
}
