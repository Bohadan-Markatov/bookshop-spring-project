package mate.academy.bookshop.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import java.util.Set;
import lombok.RequiredArgsConstructor;
import mate.academy.bookshop.dto.order.OrderItemResponseDto;
import mate.academy.bookshop.dto.order.OrderRequestDto;
import mate.academy.bookshop.dto.order.OrderResponseDto;
import mate.academy.bookshop.dto.status.StatusDto;
import mate.academy.bookshop.model.User;
import mate.academy.bookshop.service.OrderService;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "Order management", description = "Endpoints for managing orders")
@RestController
@RequestMapping("/api/orders")
@RequiredArgsConstructor
public class OrderController {
    private final OrderService orderService;

    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_USER')")
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    @Operation(summary = "Create a new order", description = "Create a new order")
    public OrderResponseDto createOrder(
            Authentication authentication,
            @RequestBody @Valid OrderRequestDto createBookRequestDto) {
        User user = (User) authentication.getPrincipal();
        return orderService.addOrder(user.getId(), createBookRequestDto);
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_USER')")
    @GetMapping
    @Operation(summary = "Get all user orders", description = "Get all user orders")
    public Set<OrderResponseDto> getAllUserOrders(
            Authentication authentication,
            Pageable pageable) {
        User user = (User) authentication.getPrincipal();
        return orderService.getAllOrders(user.getId());
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_USER')")
    @GetMapping("/{id}/items")
    @Operation(summary = "Get all items by order id", description = "Get all items by order id")
    public Set<OrderItemResponseDto> getAllItemsByOrderId(
            @PathVariable Long id,
            Authentication authentication,
            Pageable pageable) {
        User user = (User) authentication.getPrincipal();
        return orderService.getItemsByOrderId(id, user.getId());
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_USER')")
    @GetMapping("/{itemId}/items/{orderId}")
    @Operation(summary = "Get all items by order id", description = "Get all items by order id")
    public OrderItemResponseDto getAllItemByItemIdAndOrderId(
            @PathVariable Long itemId,
            @PathVariable Long orderId,
            Authentication authentication,
            Pageable pageable) {
        User user = (User) authentication.getPrincipal();
        return orderService.getItemByOrderAndItemId(itemId, orderId, user.getId());
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PatchMapping("/{id}")
    @Operation(summary = "Update a order status", description = "Update a order status")
    public OrderResponseDto updateOrderStatus(@PathVariable Long id,
                                              @RequestBody @Valid StatusDto statusDto) {
        return orderService.updateOrderStatus(statusDto, id);
    }
}
