package mate.academy.bookshop.dto.item;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class CartItemUpdateDto {
    @NotNull
    @Min(1)
    private int quantity;
}
