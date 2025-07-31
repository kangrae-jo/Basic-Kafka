package kafka.order.dto;

import java.util.List;
import kafka.member.dto.MemberDTO;

public record OrderDTO(
        Long memberId,
        List<OrderItemDTO> items
) {

    public static OrderDTO from(OrderRequest request, MemberDTO member) {
        List<OrderItemDTO> items = request.items().stream()
                .map(i -> new OrderItemDTO(i.menuId(), i.quantity()))
                .toList();

        return new OrderDTO(member.id(), items);
    }

}