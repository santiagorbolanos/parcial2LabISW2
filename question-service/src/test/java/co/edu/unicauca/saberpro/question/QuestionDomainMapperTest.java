package co.edu.unicauca.saberpro.question;

import co.edu.unicauca.saberpro.question.domain.model.AnswerOption;
import co.edu.unicauca.saberpro.question.domain.model.Question;
import co.edu.unicauca.saberpro.question.domain.model.QuestionStatus;
import co.edu.unicauca.saberpro.question.infrastructure.jpa.mapper.QuestionDomainMapper;
import co.edu.unicauca.saberpro.question.infrastructure.jpa.record.QuestionRecord;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;

class QuestionDomainMapperTest {

    private final QuestionDomainMapper mapper = new QuestionDomainMapper();

    @Test
    void roundTripKeepsData() {
        Question q = new Question(1L, "Enunciado",
                List.of(new AnswerOption("A", "uno"), new AnswerOption("B", "dos"),
                        new AnswerOption("C", "tres"), new AnswerOption("D", "cuatro")),
                "B", "Justificacion suficientemente larga.", QuestionStatus.DRAFT);

        QuestionRecord record = mapper.toRecord(q);
        assertEquals(4, record.getOptions().size());
        assertSame(record, record.getOptions().get(0).getQuestion());

        Question back = mapper.toDomain(record);
        assertEquals(q.getStatement(), back.getStatement());
        assertEquals(q.getOptions(), back.getOptions());
        assertEquals(QuestionStatus.DRAFT, back.getStatus());
    }
}
