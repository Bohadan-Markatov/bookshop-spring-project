package mate.academy.bookshop.dto.user;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;
import mate.academy.bookshop.validation.FieldMatch;

@Data
@FieldMatch(
        firstField = "password",
        secondField = "repeatPassword",
        message = "Password and repeat password must match")
public class UserRegistrationRequestDto {
    @NotNull
    @Size(max = 320)
    @Email
    private String email;
    @NotNull
    @Size(min = 8, max = 30)
    private String password;
    @NotNull
    @Size(min = 8, max = 30)
    private String repeatPassword;
    @NotNull
    @Size(min = 1, max = 100)
    private String firstName;
    @NotNull
    @Size(min = 1, max = 100)
    private String lastName;
    @Size(max = 1000)
    private String shippingAddress;
}
