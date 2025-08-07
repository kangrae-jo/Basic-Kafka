package kafka.order.dto;

import java.util.List;

public record OrderRequest(
        List<OrderItemDTO> items
) {
}
