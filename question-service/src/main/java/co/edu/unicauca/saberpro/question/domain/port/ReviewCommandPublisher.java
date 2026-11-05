package co.edu.unicauca.saberpro.question.domain.port;

import co.edu.unicauca.saberpro.question.application.command.SubmitQuestionForReview;

/** Puerto de salida hacia la mensajeria asincrona. */
public interface ReviewCommandPublisher {
    void publish(SubmitQuestionForReview command);
}
