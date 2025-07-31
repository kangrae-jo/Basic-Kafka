package kafka.order.controller;

import kafka.member.dto.MemberDTO;
import kafka.order.dto.OrderRequest;
import kafka.order.service.OrderProducer;
import lombok.RequiredArgsConstructor;
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
        orderProducer.sendOrder(request);
        return ResponseEntity.ok("[ INFO] 주문이 접수되었습니다.");
    }

}
