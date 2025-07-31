package kafka.order.service;

import kafka.member.entity.Member;
import kafka.member.repository.MemberRepository;
import kafka.menu.entity.Menu;
import kafka.menu.repository.MenuRepository;
import kafka.order.dto.OrderDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class OrderService {

    private final MemberRepository memberRepository;
    private final MenuRepository menuRepository;

    public void saveOrder(OrderDTO request) {
        log.info("주문 접수: id={}", request.memberId());
        log.info("주문 내역: order={}", request.items());

        // TODO: 실제 로직
        Member member = memberRepository.findById(request.memberId())
                .orElseThrow(() -> new IllegalArgumentException("[ERROR] 사용자를 찾을 수 없습니다."));

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
