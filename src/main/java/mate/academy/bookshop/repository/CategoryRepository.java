package mate.academy.bookshop.repository;

import java.util.Set;
import mate.academy.bookshop.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category, Long> {
    boolean existsByName(String name);

    Set<Category> findByIdIn(Set<Long> ids);
}
