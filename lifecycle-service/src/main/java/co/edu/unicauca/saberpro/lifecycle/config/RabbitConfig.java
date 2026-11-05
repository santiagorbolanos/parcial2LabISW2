package co.edu.unicauca.saberpro.lifecycle.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/** Declara exchange, cola y binding (idempotente: tambien los declara el Question Service). */
@Configuration
public class RabbitConfig {

    @Bean
    public DirectExchange saberproDirectExchange(@Value("${saberpro.rabbit.exchange}") String name) {
        return new DirectExchange(name, true, false);
    }

    @Bean
    public Queue submitReviewQueue(@Value("${saberpro.rabbit.queue.submit}") String name) {
        return new Queue(name, true);
    }

    @Bean
    public Binding submitReviewBinding(Queue submitReviewQueue, DirectExchange saberproDirectExchange,
                                       @Value("${saberpro.rabbit.routing-key.submit}") String routingKey) {
        return BindingBuilder.bind(submitReviewQueue).to(saberproDirectExchange).with(routingKey);
    }

    @Bean
    public MessageConverter jsonMessageConverter() {
        return new Jackson2JsonMessageConverter();
    }
}
