package mate.academy.bookshop.mapper;

import mate.academy.bookshop.config.MapperConfig;
import mate.academy.bookshop.dto.item.CartItemResponseDto;
import mate.academy.bookshop.model.CartItem;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(config = MapperConfig.class)
public interface CartItemMapper {
    @Mapping(target = "bookId", expression = "java(cartItem.getBook().getId())")
    @Mapping(target = "bookTitle", expression = "java(cartItem.getBook().getTitle())")
    CartItemResponseDto toDto(CartItem cartItem);
}
