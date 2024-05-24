package mate.academy.bookshop.repository;

import java.util.Set;
import mate.academy.bookshop.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order, Long> {
    Set<Order> findAllByUserId(Long userId);
}
