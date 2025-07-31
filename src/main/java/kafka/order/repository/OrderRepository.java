package kafka.order.repository;

import kafka.menu.entity.Menu;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Menu, Long> {

}
