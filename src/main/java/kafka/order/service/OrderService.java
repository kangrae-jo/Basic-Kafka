package kafka.order.service;

import kafka.menu.repository.MenuRepository;
import kafka.order.dto.OrderRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final MenuRepository menuRepository;

    public void saveOrder(OrderRequest request) {
        // TODO: 실제 로직
    }

}
