package mate.academy.bookshop.dto.order;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;
import lombok.Data;
import mate.academy.bookshop.dto.user.UserResponseDto;
import mate.academy.bookshop.model.Status;

@Data
public class OrderResponseDto implements Comparable<OrderResponseDto> {
    private Long id;
    private UserResponseDto user;
    private Set<OrderItemResponseDto> orderItems = new HashSet<>();
    private Status status;
    private BigDecimal total;
    private LocalDateTime orderDate;
    private String shippingAddress;

    @Override
    public int compareTo(OrderResponseDto dto) {
        return orderDate.isBefore(dto.orderDate) ? -1 :
        orderDate.isAfter(dto.orderDate) ? 1 : 0;
    }
}
