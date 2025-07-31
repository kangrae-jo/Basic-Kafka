package kafka.order.dto;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class OrderRequest {

    private Long memberId;
    private List<OrderItemDTO> items;

}
