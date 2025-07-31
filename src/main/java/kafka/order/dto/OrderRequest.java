package kafka.order.dto;

import java.util.List;

public record OrderRequest(
        Long memberId,
        List<OrderItemDTO> items
) {
}
