package mate.academy.bookshop.repository;

import java.util.Optional;
import mate.academy.bookshop.model.Status;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderStatusRepository extends JpaRepository<Status, Long> {
    Optional<Status> findByName(Status.StatusName name);
}
