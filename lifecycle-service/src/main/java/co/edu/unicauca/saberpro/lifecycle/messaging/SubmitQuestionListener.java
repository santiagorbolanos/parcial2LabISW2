package co.edu.unicauca.saberpro.lifecycle.messaging;

import co.edu.unicauca.saberpro.lifecycle.service.LifecycleService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
public class SubmitQuestionListener {

    private static final Logger log = LoggerFactory.getLogger(SubmitQuestionListener.class);

    private final LifecycleService lifecycleService;

    public SubmitQuestionListener(LifecycleService lifecycleService) {
        this.lifecycleService = lifecycleService;
    }

    /** Consumo en segundo plano del comando publicado por el Question Service. */
    @RabbitListener(queues = "${saberpro.rabbit.queue.submit}")
    public void onSubmitQuestionForReview(SubmitQuestionForReview command) {
        log.info("Comando recibido: operationId={}, questionId={}", command.operationId(), command.questionId());
        lifecycleService.submitForReview(command);
    }
}
