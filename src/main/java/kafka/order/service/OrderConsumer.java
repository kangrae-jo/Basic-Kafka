package kafka.order.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import kafka.order.dto.OrderDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class OrderConsumer {

    private final OrderService orderService;
    private final ObjectMapper objectMapper;

    @KafkaListener(topics = "order-topic", groupId = "order-group")
    public void consume(String message) {
        try {
            OrderDTO request = objectMapper.readValue(message, OrderDTO.class);
            orderService.saveOrder(request);
        } catch (Exception e) {
            log.error("[ERROR] Kafka 메시지 처리 중 예외 발생", e);
        }
    }

}