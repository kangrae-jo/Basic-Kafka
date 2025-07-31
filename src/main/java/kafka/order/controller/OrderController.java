package kafka.order.controller;

import kafka.member.dto.MemberDTO;
import kafka.order.dto.OrderDTO;
import kafka.order.dto.OrderRequest;
import kafka.order.service.OrderProducer;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/order")
@RequiredArgsConstructor
public class OrderController {

    private final OrderProducer orderProducer;

    @PostMapping
    public ResponseEntity<?> createOrder(@RequestBody OrderRequest request,
                                         @AuthenticationPrincipal MemberDTO member) {
        try {
            OrderDTO orderDTO = OrderDTO.from(request, member);
            orderProducer.sendOrder(orderDTO);

            return ResponseEntity.ok("[INFO] 주문이 접수되었습니다.");
        } catch (Exception e) {
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("[ERROR] 주문 처리 중 오류가 발생했습니다.");
        }
    }

}
