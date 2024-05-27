package mate.academy.bookshop.mapper;

import mate.academy.bookshop.config.MapperConfig;
import mate.academy.bookshop.dto.order.OrderItemResponseDto;
import mate.academy.bookshop.model.OrderItem;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(config = MapperConfig.class)
public interface OrderItemMapper {

    @Mapping(target = "bookId", source = "book.id")
    @Mapping(target = "bookPrice", source = "book.price")
    OrderItemResponseDto toDto(OrderItem orderItem);
}
