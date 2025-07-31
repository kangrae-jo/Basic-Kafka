package kafka.order.service;

import kafka.menu.repository.MenuRepository;
import kafka.order.dto.OrderDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class OrderService {

    private final MenuRepository menuRepository;

    public void saveOrder(OrderDTO request) {
        // TODO: 실제 로직
        log.info("주문 접수: {}", request.memberId());
        log.info("주문 내역: {}", request.items());
    }

}
