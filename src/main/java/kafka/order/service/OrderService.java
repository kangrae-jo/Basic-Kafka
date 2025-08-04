package kafka.order.service;

import java.time.LocalDateTime;
import kafka.member.entity.Member;
import kafka.member.repository.MemberRepository;
import kafka.menu.entity.Menu;
import kafka.menu.repository.MenuRepository;
import kafka.order.dto.OrderDTO;
import kafka.order.dto.OrderItemDTO;
import kafka.order.entity.Order;
import kafka.order.entity.OrderItem;
import kafka.order.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;
    private final MemberRepository memberRepository;
    private final MenuRepository menuRepository;

    public void saveOrder(OrderDTO request) {
        log.info("주문 접수: id={}", request.memberId());
        log.info("주문 내역: order={}", request.items());

        Member member = memberRepository.findById(request.memberId())
                .orElseThrow(() -> new IllegalArgumentException("[ERROR] 사용자를 찾을 수 없습니다."));

        // Order 생성
        Order order = makeOrder(member, request);

        // 계산 및 차감
        orderProcedure(member, request);

    }

    private Order makeOrder(Member member, OrderDTO request) {
        // Order 객체 생성 및 저장
        Order order = Order.builder()
                .orderTime(LocalDateTime.now())
                .member(member)
                .build();

        // orderItem 객체 생성 및 저장
        for (OrderItemDTO itemDTO : request.items()) {
            OrderItem item = OrderItem.builder()
                    .menu(menuRepository.findById(itemDTO.menuId())
                            .orElseThrow(() -> new IllegalArgumentException("[ERROR] 메뉴를 찾을 수 없습니다.")))
                    .quantity(itemDTO.quantity())
                    .build();
            order.addItem(item);
        }

        return orderRepository.save(order);
    }

    private void orderProcedure(Member member, OrderDTO request) {
        // 총 주문 금액 계산
        int amount = request.items().stream()
                .mapToInt(item -> {
                    Menu menu = menuRepository.findById(item.menuId())
                            .orElseThrow(() -> new IllegalArgumentException("[ERROR] 메뉴를 찾을 수 없습니다."));
                    return menu.getPrice() * item.quantity();
                })
                .sum();

        if (member.getPoints() < amount) {
            throw new IllegalArgumentException("[ERROR] 금액이 부족합니다.");
        }

        member.decreasePoints(amount);
        log.info("주문 완료: amount={}, id={}", amount, request.memberId());
    }

}
