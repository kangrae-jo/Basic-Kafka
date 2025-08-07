package kafka.order.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import kafka.order.dto.OrderDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OrderProducer {

    private final KafkaTemplate<String, String> kafkaTemplate;
    private final ObjectMapper objectMapper;

    public void sendOrder(OrderDTO request) {
        try {
            String json = objectMapper.writeValueAsString(request);
            kafkaTemplate.send("order-topic", json);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("[ERROR] 주문 직렬화 실패", e);
        }
    }

}