package kafka.order.constant;

public enum OrderStatus {

    CREATED,    // 주문 생성
    PAID,       // 결제 완료
    COOKING,    // 조리 중
    DELIVERING, // 배달 중
    DELIVERED   // 배달 완료

}
