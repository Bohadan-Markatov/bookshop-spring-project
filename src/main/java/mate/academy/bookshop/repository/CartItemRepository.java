package mate.academy.bookshop.repository;

import mate.academy.bookshop.model.CartItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CartItemRepository extends JpaRepository<CartItem, Long> {
    void deleteAllByShoppingCartId(Long cartId);
}
