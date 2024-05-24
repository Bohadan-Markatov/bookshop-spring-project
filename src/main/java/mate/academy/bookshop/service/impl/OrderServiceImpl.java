package mate.academy.bookshop.service.impl;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.TreeSet;
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
import mate.academy.bookshop.model.User;
import mate.academy.bookshop.repository.OrderItemRepository;
import mate.academy.bookshop.repository.OrderRepository;
import mate.academy.bookshop.repository.OrderStatusRepository;
import mate.academy.bookshop.repository.ShoppingCartRepository;
import mate.academy.bookshop.repository.UserRepository;
import mate.academy.bookshop.service.OrderService;
import mate.academy.bookshop.service.ShoppingCartService;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {
    private static final Status.StatusName DEFAULT_STATUS_NAME
            = Status.StatusName.PENDING;
    private final ShoppingCartService shoppingCartService;
    private final UserRepository userRepository;
    private final ShoppingCartRepository shoppingCartRepository;
    private final OrderItemRepository orderItemRepository;
    private final OrderStatusRepository statusRepository;
    private final OrderRepository orderRepository;
    private final OrderItemMapper orderItemMapper;
    private final OrderMapper orderMapper;

    @Override
    public OrderResponseDto createOrder(Long userId, OrderRequestDto requestDto) {
        ShoppingCart shoppingCart = getCartById(userId);
        Order newOrder = new Order();
        newOrder.setUser(getUserById(userId));
        newOrder.setTotal(getTotal(shoppingCart.getCartItems()));
        newOrder.setShippingAddress(requestDto.getShippingAddress());
        newOrder.setTotal(getTotal(shoppingCart.getCartItems()));
        newOrder.setOrderDate(LocalDateTime.now());
        newOrder.setStatus(getByName(DEFAULT_STATUS_NAME));
        orderRepository.save(newOrder);
        Set<OrderItem> orderItems
                = mapAndSaveOrderItems(shoppingCart.getCartItems(), newOrder);
        newOrder.setOrderItems(orderItems);
        shoppingCartService.clearShoppingCart(shoppingCart.getId());
        return orderMapper.toDto(newOrder);
    }

    @Override
    public Set<OrderResponseDto> getAllOrders(Long userId) {
        Set<OrderResponseDto> set = new TreeSet<>();
        orderRepository.findAllByUserId(userId).stream()
                .map(orderMapper::toDto)
                .forEach(set::add);
        return set;
    }

    @Override
    public Set<OrderItemResponseDto> getItemsByOrderId(Long orderId, Long userId) {
        Optional<Order> order = orderRepository.findById(orderId);
        if (order.isEmpty() || !order.get().getUser().getId().equals(userId)) {
            throw new EntityNotFoundException("There is no order by id: " + orderId);
        }
        Set<OrderItemResponseDto> orderItems = new HashSet<>();
        orderItemRepository.findAllByOrderId(orderId).stream()
                .map(orderItemMapper::toDto)
                .forEach(orderItems::add);
        return orderItems;
    }

    @Override
    public OrderItemResponseDto getItemByOrderAndItemId(Long itemId, Long orderId, Long userId) {
        Optional<OrderItem> item = orderItemRepository.findByIdAndOrderId(itemId, orderId);
        if (item.isEmpty() || !item.get().getOrder().getUser().getId().equals(userId)) {
            throw new EntityNotFoundException("There is no item by these item id and order id: "
                    + itemId + " " + orderId);
        }
        return orderItemMapper.toDto(item.get());
    }

    @Override
    public OrderResponseDto updateOrderStatus(StatusDto statusDto, Long orderId) {
        Status status = getByName(statusDto.getName());
        Order order = orderRepository.findById(orderId).orElseThrow(()
                -> new EntityNotFoundException("No order by id " + orderId));
        order.setStatus(status);
        return orderMapper.toDto(orderRepository.save(order));
    }

    private ShoppingCart getCartById(Long userId) {
        ShoppingCart shoppingCart =
                shoppingCartRepository.findByUserId(userId).orElseThrow(()
                        -> new EntityNotFoundException(
                        "There is no cart with this user id: " + userId));
        if (!shoppingCart.getCartItems().isEmpty()) {
            return shoppingCart;
        }
        throw new EntityNotFoundException("The cart is empty");
    }

    private User getUserById(Long userId) {
        return userRepository.findById(userId)
                .orElseThrow(()
                        -> new EntityNotFoundException(
                        "There is no user with this id: " + userId));
    }

    private Set<OrderItem> mapAndSaveOrderItems(Set<CartItem> cartItems, Order order) {
        Set<OrderItem> set = new HashSet<>();
        cartItems.stream()
                .map(item -> OrderItem.of(item, order))
                .peek(orderItemRepository::save)
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
}
