package mate.academy.bookshop.service.impl;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;
import java.util.TreeSet;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import mate.academy.bookshop.dto.order.OrderItemResponseDto;
import mate.academy.bookshop.dto.order.OrderRequestDto;
import mate.academy.bookshop.dto.order.OrderResponseDto;
import mate.academy.bookshop.dto.status.StatusDto;
import mate.academy.bookshop.exception.EntityNotFoundException;
import mate.academy.bookshop.mapper.OrderItemMapper;
import mate.academy.bookshop.mapper.OrderMapper;
import mate.academy.bookshop.model.CartItem;
import mate.academy.bookshop.model.Order;
import mate.academy.bookshop.model.OrderItem;
import mate.academy.bookshop.model.ShoppingCart;
import mate.academy.bookshop.model.Status;
import mate.academy.bookshop.repository.OrderRepository;
import mate.academy.bookshop.repository.OrderStatusRepository;
import mate.academy.bookshop.service.OrderService;
import mate.academy.bookshop.service.ShoppingCartService;
import mate.academy.bookshop.service.UserService;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {
    private static final Status.StatusName DEFAULT_STATUS_NAME
            = Status.StatusName.PENDING;
    private final ShoppingCartService shoppingCartService;
    private final UserService userService;
    private final OrderStatusRepository statusRepository;
    private final OrderRepository orderRepository;
    private final OrderItemMapper orderItemMapper;
    private final OrderMapper orderMapper;

    @Override
    public OrderResponseDto addOrder(Long userId, OrderRequestDto requestDto) {
        ShoppingCart shoppingCart = shoppingCartService.getNonEmptyCartByUserId(userId);
        Order newOrder = createOrder(userId, shoppingCart, requestDto);
        orderRepository.save(newOrder);
        shoppingCartService.clearShoppingCart(shoppingCart.getId());
        return orderMapper.toDto(newOrder);
    }

    @Override
    public Set<OrderResponseDto> getAllOrders(Long userId) {
        Set<OrderResponseDto> set = new TreeSet<>();
        orderRepository.findAllByUserId(userId)
                .forEach(order -> set.add(orderMapper.toDto(order)));
        return set;
    }

    @Override
    public Set<OrderItemResponseDto> getItemsByOrderId(Long orderId, Long userId) {
        Order order = getByIdAndUserId(orderId, userId);
        return order.getOrderItems().stream()
                .map(orderItemMapper::toDto)
                .collect(Collectors.toSet());
    }

    @Override
    public OrderItemResponseDto getItemByOrderAndItemId(Long itemId, Long orderId, Long userId) {
        return orderItemMapper.toDto(getByIdAndUserId(orderId, userId).getOrderItems().stream()
                .filter(item -> item.getId().equals(itemId))
                .findAny().orElseThrow(()
                        -> new EntityNotFoundException("There is no item by these "
                        + "item id and order id: " + itemId + " and " + orderId)));
    }

    @Override
    public OrderResponseDto updateOrderStatus(StatusDto statusDto, Long orderId) {
        Status status = getByName(statusDto.getName());
        Order order = orderRepository.findById(orderId).orElseThrow(()
                -> new EntityNotFoundException("No order by id " + orderId));
        order.setStatus(status);
        return orderMapper.toDto(orderRepository.save(order));
    }

    private Set<OrderItem> mapCartItemToOrderItem(Set<CartItem> cartItems, Order order) {
        Set<OrderItem> set = new HashSet<>();
        cartItems.stream()
                .map(item -> createOrderItem(item, order))
                .forEach(set::add);
        return set;
    }

    private BigDecimal getTotal(Set<CartItem> items) {
        return items.stream()
                .map(item -> item.getBook().getPrice()
                        .multiply(BigDecimal.valueOf(item.getQuantity())))
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    private Status getByName(Status.StatusName name) {
        return statusRepository.findByName(name).orElseThrow(()
                -> new EntityNotFoundException("No status with name: " + name));
    }

    private Order getByIdAndUserId(Long orderId, Long userId) {
        return orderRepository.findByIdAndUserId(orderId, userId).orElseThrow(()
                -> new EntityNotFoundException("There is no order by id: " + orderId));
    }

    private Order createOrder(Long userId, ShoppingCart cart, OrderRequestDto dto) {
        Order newOrder = new Order();
        newOrder.setUser(userService.getById(userId));
        newOrder.setTotal(getTotal(cart.getCartItems()));
        newOrder.setShippingAddress(dto.getShippingAddress());
        newOrder.setTotal(getTotal(cart.getCartItems()));
        newOrder.setOrderDate(LocalDateTime.now());
        newOrder.setStatus(getByName(DEFAULT_STATUS_NAME));
        newOrder.setOrderItems(mapCartItemToOrderItem(cart.getCartItems(), newOrder));
        return newOrder;
    }

    private OrderItem createOrderItem(CartItem cartItem, Order order) {
        OrderItem orderItem = new OrderItem();
        orderItem.setOrder(order);
        orderItem.setBook(cartItem.getBook());
        orderItem.setQuantity(cartItem.getQuantity());
        orderItem.setPrice(cartItem.getBook().getPrice());
        return orderItem;
    }
}
