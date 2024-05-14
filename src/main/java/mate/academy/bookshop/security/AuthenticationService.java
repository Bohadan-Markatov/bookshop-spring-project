package mate.academy.bookshop.security;

import lombok.RequiredArgsConstructor;
import mate.academy.bookshop.dto.user.UserLoginRequestDto;
import mate.academy.bookshop.dto.user.UserLoginResponseDto;
import mate.academy.bookshop.util.JwtUtil;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthenticationService {
    private final JwtUtil jwtUtil;
    private final AuthenticationManager authenticationManager;

    public UserLoginResponseDto authenticate(UserLoginRequestDto request) {
        final Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.email(), request.password())
        );

        String token = jwtUtil.generateToken(request.email());
        return new UserLoginResponseDto(token);
    }
}
