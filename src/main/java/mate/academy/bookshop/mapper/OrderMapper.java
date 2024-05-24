package mate.academy.bookshop.mapper;

import mate.academy.bookshop.config.MapperConfig;
import mate.academy.bookshop.dto.order.OrderResponseDto;
import mate.academy.bookshop.model.Order;
import org.mapstruct.Mapper;

@Mapper(config = MapperConfig.class, uses = OrderItemMapper.class)
public interface OrderMapper {
    OrderResponseDto toDto(Order order);
}
