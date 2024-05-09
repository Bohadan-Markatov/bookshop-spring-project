package mate.academy.bookshop.service.impl;

import lombok.RequiredArgsConstructor;
import mate.academy.bookshop.dto.user.UserRegistrationRequestDto;
import mate.academy.bookshop.dto.user.UserResponseDto;
import mate.academy.bookshop.exception.RegistrationException;
import mate.academy.bookshop.mapper.UserMapper;
import mate.academy.bookshop.model.User;
import mate.academy.bookshop.repository.UserRepository;
import mate.academy.bookshop.service.UserService;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;

    @Override
    public UserResponseDto save(UserRegistrationRequestDto dto) {
        checkEmailAvailability(dto.getEmail());
        User user = userRepository.save(userMapper.toModel(dto));
        return userMapper.toDto(user);
    }

    @Override
    public void checkEmailAvailability(String email) {
        if (userRepository.existsByEmail(email)) {
            throw new RegistrationException("This email is already used");
        }
    }
}
