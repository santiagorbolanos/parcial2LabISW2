package co.edu.unicauca.saberpro.question.infrastructure.jpa.record;

import co.edu.unicauca.saberpro.question.domain.model.QuestionStatus;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OrderBy;
import jakarta.persistence.Table;

import java.util.ArrayList;
import java.util.List;

/** Entidad relacional JPA: vive solo en la capa de infraestructura. */
@Entity
@Table(name = "questions")
public class QuestionRecord {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 2000)
    private String statement;

    @Column(name = "correct_key", nullable = false, length = 10)
    private String correctKey;

    @Column(nullable = false, length = 2000)
    private String justification;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 30)
    private QuestionStatus status;

    @OneToMany(mappedBy = "question", cascade = CascadeType.ALL, orphanRemoval = true)
    @OrderBy("id ASC")
    private List<AnswerOptionRecord> options = new ArrayList<>();

    public QuestionRecord() {
    }

    /** Mantiene sincronizados ambos lados de la relacion bidireccional. */
    public void addOption(AnswerOptionRecord option) {
        option.setQuestion(this);
        this.options.add(option);
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getStatement() { return statement; }
    public void setStatement(String statement) { this.statement = statement; }

    public String getCorrectKey() { return correctKey; }
    public void setCorrectKey(String correctKey) { this.correctKey = correctKey; }

    public String getJustification() { return justification; }
    public void setJustification(String justification) { this.justification = justification; }

    public QuestionStatus getStatus() { return status; }
    public void setStatus(QuestionStatus status) { this.status = status; }

    public List<AnswerOptionRecord> getOptions() { return options; }
    public void setOptions(List<AnswerOptionRecord> options) { this.options = options; }
}
