package kafka.order.service;

import com.fasterxml.jackson.core.JsonProcessingException;
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

    @KafkaListener(topics = "order-topic", groupId = "order-group")
    public void consume(String message) throws JsonProcessingException {
        // TODO: 빈으로 뺴기
        ObjectMapper mapper = new ObjectMapper();
        OrderDTO request = mapper.readValue(message, OrderDTO.class);

        try {
            orderService.saveOrder(request);
        } catch (Exception e) {
            log.error("[ERROR] Kafka 메시지 처리 중 예외 발생", e);
        }
    }

}