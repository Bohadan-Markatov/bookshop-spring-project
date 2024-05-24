package mate.academy.bookshop.service.impl;

import java.util.Optional;
import java.util.Set;
import lombok.RequiredArgsConstructor;
import mate.academy.bookshop.dto.cart.ShoppingCartResponseDto;
import mate.academy.bookshop.dto.item.CartItemCreateDto;
import mate.academy.bookshop.dto.item.CartItemResponseDto;
import mate.academy.bookshop.dto.item.CartItemUpdateDto;
import mate.academy.bookshop.exception.EntityNotFoundException;
import mate.academy.bookshop.exception.NotUniqueValueException;
import mate.academy.bookshop.mapper.CartItemMapper;
import mate.academy.bookshop.mapper.ShoppingChartMapper;
import mate.academy.bookshop.model.Book;
import mate.academy.bookshop.model.CartItem;
import mate.academy.bookshop.model.ShoppingCart;
import mate.academy.bookshop.model.User;
import mate.academy.bookshop.repository.BookRepository;
import mate.academy.bookshop.repository.CartItemRepository;
import mate.academy.bookshop.repository.ShoppingCartRepository;
import mate.academy.bookshop.service.ShoppingCartService;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ShoppingCartServiceImpl implements ShoppingCartService {
    private final ShoppingCartRepository shoppingCartRepository;
    private final ShoppingChartMapper shoppingChartMapper;
    private final CartItemRepository cartItemRepository;
    private final CartItemMapper cartItemMapper;
    private final BookRepository bookRepository;

    @Override
    public ShoppingCartResponseDto save(User user) {
        ShoppingCart shoppingCart = new ShoppingCart();
        shoppingCart.setUser(user);
        return shoppingChartMapper.toDto(shoppingCartRepository.save(shoppingCart));
    }

    @Override
    public ShoppingCartResponseDto getByUserId(Long userId) {
        return shoppingChartMapper.toDto(getCartByUserId(userId));
    }

    @Override
    public ShoppingCartResponseDto addBook(Long userId, CartItemCreateDto createDto) {
        ShoppingCart shoppingCart = getCartByUserId(userId);
        Book book = bookRepository.findById(createDto.getBookId()).orElseThrow(()
                -> new EntityNotFoundException("There is no book with this id: "
                + createDto.getBookId()));
        CartItem cartItem = saveNewCartItem(shoppingCart, book, createDto.getQuantity());
        Set<CartItem> cartItems = shoppingCart.getCartItems();
        cartItems.add(cartItem);
        shoppingCart.setCartItems(cartItems);
        return shoppingChartMapper.toDto(shoppingCartRepository.save(shoppingCart));
    }

    @Override
    public CartItemResponseDto updateQuantity(Long itemId,
                                              Long userId,
                                              CartItemUpdateDto cartItemUpdateDto) {
        CartItem cartItem = getItemAndCheckOwner(itemId, userId);
        cartItem.setQuantity(cartItemUpdateDto.getQuantity());
        return cartItemMapper.toDto(cartItemRepository.save(cartItem));
    }

    @Override
    public void deleteItemById(Long itemId, Long userId) {
        getItemAndCheckOwner(itemId, userId);
        cartItemRepository.deleteById(itemId);
    }

    private CartItem getItemAndCheckOwner(Long itemId, Long ownerId) {
        Optional<CartItem> cartItem = cartItemRepository.findById(itemId);
        if (cartItem.isEmpty() || !cartItem.get().getShoppingCart().getId().equals(ownerId)) {
            throw new EntityNotFoundException("Can't find cart item by id: " + itemId);
        }
        return cartItem.get();
    }

    private CartItem saveNewCartItem(ShoppingCart shoppingCart, Book book, int quantity) {
        CartItem cartItem = new CartItem();
        cartItem.setShoppingCart(shoppingCart);
        cartItem.setBook(book);
        cartItem.setQuantity(quantity);
        if (shoppingCart.getCartItems().contains(cartItem)) {
            throw new NotUniqueValueException("This cart item already in cart");
        }
        return cartItemRepository.save(cartItem);
    }

    private ShoppingCart getCartByUserId(Long userId) {
        return shoppingCartRepository.findByUserId(userId)
                .orElseThrow(() -> new EntityNotFoundException(
                "Can't find a shopping cart by user id: " + userId));
    }
}
