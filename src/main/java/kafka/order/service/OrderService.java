package kafka.order.service;

import java.time.LocalDateTime;
import kafka.member.entity.Member;
import kafka.member.repository.MemberRepository;
import kafka.menu.entity.Menu;
import kafka.menu.repository.MenuRepository;
import kafka.order.constant.OrderStatus;
import kafka.order.dto.OrderDTO;
import kafka.order.dto.OrderItemDTO;
import kafka.order.entity.Order;
import kafka.order.entity.OrderItem;
import kafka.order.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;
    private final MemberRepository memberRepository;
    private final MenuRepository menuRepository;

    @Transactional
    public void saveOrder(OrderDTO request) {
        log.info("주문 접수: id={}", request.memberId());
        log.info("주문 내역: order={}", request.items());

        Member member = memberRepository.findById(request.memberId())
                .orElseThrow(() -> new IllegalArgumentException("[ERROR] 사용자를 찾을 수 없습니다."));

        // Order 생성
        Order order = makeOrder(member, request);
        // 계산 및 차감
        order.payBy(member);
    }

    private Order makeOrder(Member member, OrderDTO request) {
        // Order 객체 생성 및 저장
        Order order = Order.builder()
                .orderTime(LocalDateTime.now())
                .member(member)
                .status(OrderStatus.CREATED)
                .build();

        // orderItem 객체 생성 및 저장
        for (OrderItemDTO itemDTO : request.items()) {
            Menu menu = menuRepository.findById(itemDTO.menuId())
                    .orElseThrow(() -> new IllegalArgumentException("[ERROR] 메뉴를 찾을 수 없습니다."));

            OrderItem item = OrderItem.builder()
                    .menu(menu)
                    .quantity(itemDTO.quantity())
                    .priceAtOrder(menu.getPrice())
                    .build();
            order.addItem(item);
        }

        return orderRepository.save(order);
    }

}
