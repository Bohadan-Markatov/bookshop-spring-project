package mate.academy.bookshop.dto.category;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class CategoryRequestDto {
    @NotNull
    @Size(min = 1, max = 100)
    private String name;
    @Size(max = 1000)
    private String description;
}
