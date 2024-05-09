package mate.academy.bookshop.service;

import mate.academy.bookshop.dto.user.UserRegistrationRequestDto;
import mate.academy.bookshop.dto.user.UserResponseDto;

public interface UserService {
    UserResponseDto save(UserRegistrationRequestDto dto);

    void checkEmailAvailability(String email);
}
