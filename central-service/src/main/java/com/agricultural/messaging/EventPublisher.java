package edu.ec.com.central.messaging;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class EventPublisher {

    private final RabbitTemplate rabbitTemplate;
    private final ObjectMapper mapper = new ObjectMapper();

    @Value("${app.rabbit.exchange}") String exchange;
    @Value("${app.rabbit.routingNueva}") String routing;

    public void publishNuevaCosecha(UUID cosechaId, String producto, double toneladas) {
        try {
            Map<String, Object> payload = new HashMap<>();
            payload.put("cosecha_id", cosechaId.toString());
            payload.put("producto", producto);
            payload.put("toneladas", toneladas);
            payload.put("requiere_insumos", new String[]{"Semilla Arroz L-23", "Fertilizante N-PK"});

            Map<String, Object> event = new HashMap<>();
            event.put("event_id", UUID.randomUUID().toString());
            event.put("event_type", "nueva_cosecha");
            event.put("timestamp", Instant.now().toString());
            event.put("payload", payload);

            rabbitTemplate.convertAndSend(exchange, routing, mapper.writeValueAsString(event));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
