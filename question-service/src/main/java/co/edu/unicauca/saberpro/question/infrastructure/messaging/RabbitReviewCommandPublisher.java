package co.edu.unicauca.saberpro.question.infrastructure.messaging;

import co.edu.unicauca.saberpro.question.application.command.SubmitQuestionForReview;
import co.edu.unicauca.saberpro.question.domain.port.ReviewCommandPublisher;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/** Publica SubmitQuestionForReview en saberpro.direct.exchange con routing key question.submit. */
@Component
public class RabbitReviewCommandPublisher implements ReviewCommandPublisher {

    private final RabbitTemplate rabbitTemplate;
    private final String exchange;
    private final String routingKey;

    public RabbitReviewCommandPublisher(RabbitTemplate rabbitTemplate,
                                        @Value("${saberpro.rabbit.exchange}") String exchange,
                                        @Value("${saberpro.rabbit.routing-key.submit}") String routingKey) {
        this.rabbitTemplate = rabbitTemplate;
        this.exchange = exchange;
        this.routingKey = routingKey;
    }

    @Override
    public void publish(SubmitQuestionForReview command) {
        rabbitTemplate.convertAndSend(exchange, routingKey, command);
    }
}
