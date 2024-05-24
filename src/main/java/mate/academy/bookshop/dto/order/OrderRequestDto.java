package mate.academy.bookshop.dto.order;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class OrderRequestDto {
    @NotNull
    @Size(max = 1000)
    private String shippingAddress;
}
