package co.edu.unicauca.saberpro.question.domain.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Modelo de dominio puro: SIN @Entity, @Table ni @Id.
 * La persistencia se resuelve en infrastructure.jpa.record.QuestionRecord.
 */
public class Question {

    private Long id;
    private String statement;
    private List<AnswerOption> options = new ArrayList<>();
    private String correctKey;
    private String justification;
    private QuestionStatus status;

    public Question() {
    }

    public Question(Long id, String statement, List<AnswerOption> options,
                    String correctKey, String justification, QuestionStatus status) {
        this.id = id;
        this.statement = statement;
        this.options = options == null ? new ArrayList<>() : new ArrayList<>(options);
        this.correctKey = correctKey;
        this.justification = justification;
        this.status = status;
    }

    /** Regla de negocio: toda pregunta que pasa la validacion nace en DRAFT. */
    public void assignDraft() {
        this.status = QuestionStatus.DRAFT;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getStatement() { return statement; }
    public void setStatement(String statement) { this.statement = statement; }

    public List<AnswerOption> getOptions() { return options; }
    public void setOptions(List<AnswerOption> options) { this.options = options; }

    public String getCorrectKey() { return correctKey; }
    public void setCorrectKey(String correctKey) { this.correctKey = correctKey; }

    public String getJustification() { return justification; }
    public void setJustification(String justification) { this.justification = justification; }

    public QuestionStatus getStatus() { return status; }
    public void setStatus(QuestionStatus status) { this.status = status; }
}
