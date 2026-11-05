package co.edu.unicauca.saberpro.question.infrastructure.messaging;

import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitConfig {

    @Bean
    public DirectExchange saberproDirectExchange(@Value("${saberpro.rabbit.exchange}") String name) {
        return new DirectExchange(name, true, false);
    }

    /** Serializa los comandos como JSON (Spring Boot lo asocia automaticamente al RabbitTemplate). */
    @Bean
    public MessageConverter jsonMessageConverter() {
        return new Jackson2JsonMessageConverter();
    }
}
