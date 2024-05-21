package mate.academy.bookshop.mapper;

import mate.academy.bookshop.config.MapperConfig;
import mate.academy.bookshop.dto.cart.ShoppingCartResponseDto;
import mate.academy.bookshop.model.ShoppingCart;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(config = MapperConfig.class, uses = CartItemMapper.class)
public interface ShoppingChartMapper {
    @Mapping(target = "userId", expression = "java(shoppingCart.getUser().getId())")
    @Mapping(target = "cartItems", source = "cartItems")
    ShoppingCartResponseDto toDto(ShoppingCart shoppingCart);
}
