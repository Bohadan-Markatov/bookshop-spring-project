package mate.academy.bookshop.dto.order;

import lombok.Data;

@Data
public class OrderItemResponseDto {
    private Long id;
    private Long bookId;
    private Long bookPrice;
    private int quantity;
}
