package mate.academy.bookshop.service;

import mate.academy.bookshop.dto.user.UserRegistrationRequestDto;
import mate.academy.bookshop.dto.user.UserResponseDto;
import mate.academy.bookshop.model.User;

public interface UserService {
    UserResponseDto save(UserRegistrationRequestDto dto);

    void checkEmailAvailability(String email);

    User getById(Long id);
}
