package co.edu.unicauca.saberpro.question.domain.repository;

import co.edu.unicauca.saberpro.question.domain.model.Question;

import java.util.List;
import java.util.Optional;

/** Repositorio de dominio (interfaz abstracta). */
public interface QuestionRepository {
    Question save(Question question);

    Optional<Question> findById(Long id);

    List<Question> findAll();
}
