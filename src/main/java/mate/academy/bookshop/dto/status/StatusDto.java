package mate.academy.bookshop.dto.status;

import jakarta.validation.constraints.NotNull;
import lombok.Data;
import mate.academy.bookshop.model.Status;

@Data
public class StatusDto {
    @NotNull
    private Status.StatusName name;
}
