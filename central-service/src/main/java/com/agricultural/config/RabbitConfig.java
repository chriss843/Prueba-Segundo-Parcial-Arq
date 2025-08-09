package edu.ec.com.central.config;

import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitConfig {

    @Value("${app.rabbit.url}") String rabbitUrl;
    @Value("${app.rabbit.exchange}") String exchangeName;
    @Value("${app.rabbit.queueInventario}") String queueInventario;
    @Value("${app.rabbit.queueFacturacion}") String queueFacturacion;
    @Value("${app.rabbit.routingNueva}") String routingNueva;

    @Bean
    ConnectionFactory connectionFactory() {
        CachingConnectionFactory cf = new CachingConnectionFactory();
        cf.setUri(rabbitUrl);
        return cf;
    }

    @Bean
    DirectExchange cosechasExchange() {
        return new DirectExchange(exchangeName, true, false);
    }

    @Bean
    Queue inventarioQueue() {
        return QueueBuilder.durable(queueInventario).build();
    }

    @Bean
    Queue facturacionQueue() {
        return QueueBuilder.durable(queueFacturacion).build();
    }

    @Bean
    Binding bindInventario() {
        return BindingBuilder.bind(inventarioQueue()).to(cosechasExchange()).with(routingNueva);
    }

    @Bean
    Binding bindFacturacion() {
        return BindingBuilder.bind(facturacionQueue()).to(cosechasExchange()).with(routingNueva);
    }
}
