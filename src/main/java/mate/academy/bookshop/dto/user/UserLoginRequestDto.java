package mate.academy.bookshop.dto.user;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record UserLoginRequestDto(@NotNull @Size(max = 320) @Email String email,
                                  @NotNull @Size(min = 8, max = 30)String password) {
}
