package mate.academy.bookshop.service.impl;

import java.util.Set;
import lombok.RequiredArgsConstructor;
import mate.academy.bookshop.dto.user.UserRegistrationRequestDto;
import mate.academy.bookshop.dto.user.UserResponseDto;
import mate.academy.bookshop.exception.RegistrationException;
import mate.academy.bookshop.mapper.UserMapper;
import mate.academy.bookshop.model.Role;
import mate.academy.bookshop.model.User;
import mate.academy.bookshop.repository.RoleRepository;
import mate.academy.bookshop.repository.UserRepository;
import mate.academy.bookshop.service.ShoppingCartService;
import mate.academy.bookshop.service.UserService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private static final Role.RoleName DEFAULT_ROLE_NAME = Role.RoleName.USER;
    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;
    private final RoleRepository roleRepository;
    private final ShoppingCartService shoppingCartService;

    @Override
    public UserResponseDto save(UserRegistrationRequestDto dto) {
        checkEmailAvailability(dto.getEmail());
        User user = userMapper.toModel(dto);
        user.setPassword(passwordEncoder.encode(dto.getPassword()));
        Role defaultRole = roleRepository.findByName(DEFAULT_ROLE_NAME);
        user.setRoles(Set.of(defaultRole));
        user = userRepository.save(user);
        shoppingCartService.save(user);
        return userMapper.toDto(user);
    }

    @Override
    public void checkEmailAvailability(String email) {
        if (userRepository.existsByEmail(email)) {
            throw new RegistrationException("Email: " + email + " is already used");
        }
    }
}
