package kafka.order.dto;

public record OrderItemDTO(
        Long menuId,
        int quantity
) {
}
