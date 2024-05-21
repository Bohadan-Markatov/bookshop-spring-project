package mate.academy.bookshop.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import mate.academy.bookshop.dto.cart.ShoppingCartResponseDto;
import mate.academy.bookshop.dto.item.CartItemCreateDto;
import mate.academy.bookshop.dto.item.CartItemResponseDto;
import mate.academy.bookshop.dto.item.CartItemUpdateDto;
import mate.academy.bookshop.model.User;
import mate.academy.bookshop.service.ShoppingCartService;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "Shopping cart management", description = "Endpoints for managing shopping carts")
@RestController
@RequestMapping("/api/cart")
@RequiredArgsConstructor
public class ShoppingCartController {
    private final ShoppingCartService shoppingCartService;

    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_USER')")
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    @Operation(summary = "Add book to cart", description = "Add book to cart")
    public ShoppingCartResponseDto addBookToCart(
            Authentication authentication,
            @RequestBody @Valid CartItemCreateDto cartItemCreateDto
    ) {
        User user = (User) authentication.getPrincipal();
        return shoppingCartService.addBook(user.getId(), cartItemCreateDto);
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_USER')")
    @GetMapping
    @Operation(summary = "Get a cart by user id", description = "Get a cart by user id")
    public ShoppingCartResponseDto getCart(Authentication authentication) {
        User user = (User) authentication.getPrincipal();
        return shoppingCartService.getByUserId(user.getId());
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_USER')")
    @PutMapping("/cart-items/{id}")
    @Operation(summary = "Update quantity", description = "Update a quantity")
    public CartItemResponseDto updateQuantity(@PathVariable Long id,
                                          @RequestBody @Valid CartItemUpdateDto cartItemUpdateDto,
                                              Authentication authentication) {
        User user = (User) authentication.getPrincipal();
        return shoppingCartService.updateQuantity(id, user.getId(), cartItemUpdateDto);
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_USER')")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/cart-items/{id}")
    @Operation(summary = "Delete a cart item by id", description = "Delete a cart item by id")
    public void deleteBookById(@PathVariable Long id, Authentication authentication) {
        User user = (User) authentication.getPrincipal();
        shoppingCartService.deleteItemById(id, user.getId());
    }
}
