package co.edu.unicauca.saberpro.question.infrastructure.jpa.mapper;

import co.edu.unicauca.saberpro.question.domain.model.AnswerOption;
import co.edu.unicauca.saberpro.question.domain.model.Question;
import co.edu.unicauca.saberpro.question.infrastructure.jpa.record.AnswerOptionRecord;
import co.edu.unicauca.saberpro.question.infrastructure.jpa.record.QuestionRecord;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/** Mapeador bidireccional Question <-> QuestionRecord. */
@Component
public class QuestionDomainMapper {

    public QuestionRecord toRecord(Question question) {
        QuestionRecord record = new QuestionRecord();
        record.setId(question.getId());
        record.setStatement(question.getStatement());
        record.setCorrectKey(question.getCorrectKey());
        record.setJustification(question.getJustification());
        record.setStatus(question.getStatus());
        if (question.getOptions() != null) {
            for (AnswerOption option : question.getOptions()) {
                AnswerOptionRecord optionRecord = new AnswerOptionRecord();
                optionRecord.setOptionKey(option.key());
                optionRecord.setText(option.text());
                record.addOption(optionRecord);
            }
        }
        return record;
    }

    public Question toDomain(QuestionRecord record) {
        List<AnswerOption> options = new ArrayList<>();
        for (AnswerOptionRecord optionRecord : record.getOptions()) {
            options.add(new AnswerOption(optionRecord.getOptionKey(), optionRecord.getText()));
        }
        return new Question(record.getId(), record.getStatement(), options,
                record.getCorrectKey(), record.getJustification(), record.getStatus());
    }
}
