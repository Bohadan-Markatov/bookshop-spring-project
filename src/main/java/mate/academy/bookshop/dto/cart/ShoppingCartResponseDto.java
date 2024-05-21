package mate.academy.bookshop.dto.cart;

import java.util.Set;
import lombok.Data;
import mate.academy.bookshop.dto.item.CartItemResponseDto;

@Data
public class ShoppingCartResponseDto {
    private Long id;
    private Long userId;
    private Set<CartItemResponseDto> cartItems;
}
