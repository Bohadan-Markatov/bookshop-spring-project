package mate.academy.bookshop.service;

import mate.academy.bookshop.dto.cart.ShoppingCartResponseDto;
import mate.academy.bookshop.dto.item.CartItemCreateDto;
import mate.academy.bookshop.dto.item.CartItemResponseDto;
import mate.academy.bookshop.dto.item.CartItemUpdateDto;
import mate.academy.bookshop.model.User;

public interface ShoppingCartService {
    ShoppingCartResponseDto save(User user);

    ShoppingCartResponseDto addBook(Long userId, CartItemCreateDto createDto);

    ShoppingCartResponseDto getByUserId(Long userId);

    CartItemResponseDto updateQuantity(Long id, Long userId, CartItemUpdateDto cartItemUpdateDto);

    void deleteItemById(Long id, Long userId);
}
