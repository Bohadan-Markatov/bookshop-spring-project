package mate.academy.bookshop.service;

import java.util.Set;
import mate.academy.bookshop.dto.order.OrderItemResponseDto;
import mate.academy.bookshop.dto.order.OrderRequestDto;
import mate.academy.bookshop.dto.order.OrderResponseDto;
import mate.academy.bookshop.dto.status.StatusDto;

public interface OrderService {
    OrderResponseDto addOrder(Long userId, OrderRequestDto requestDto);

    Set<OrderResponseDto> getAllOrders(Long userId);

    Set<OrderItemResponseDto> getItemsByOrderId(Long orderId, Long userId);

    OrderItemResponseDto getItemByOrderAndItemId(Long itemId, Long orderId, Long userId);

    OrderResponseDto updateOrderStatus(StatusDto statusDto, Long orderId);
}
