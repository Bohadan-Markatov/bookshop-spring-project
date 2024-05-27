package mate.academy.bookshop.repository;

import java.util.Optional;
import java.util.Set;
import mate.academy.bookshop.model.OrderItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderItemRepository extends JpaRepository<OrderItem, Long> {
    Set<OrderItem> findAllByOrderId(Long orderId);

    Optional<OrderItem> findByIdAndOrderId(Long id, Long orderId);
}
