package mate.academy.bookshop.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import mate.academy.bookshop.dto.user.UserRegistrationRequestDto;

public class PasswordMatchValidator
        implements ConstraintValidator<PasswordMatch, UserRegistrationRequestDto> {

    @Override
    public boolean isValid(UserRegistrationRequestDto requestDto,
                           ConstraintValidatorContext constraintValidatorContext
    ) {
        String password = requestDto.getPassword();
        String repeatPassword = requestDto.getRepeatPassword();
        return password != null && password.equals(repeatPassword);
    }
}
