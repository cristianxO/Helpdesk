package com.cristian.helpdesk.helpdesk.service;

import com.cristian.helpdesk.helpdesk.dto.AuthRequest;
import com.cristian.helpdesk.helpdesk.dto.AuthResponse;
import com.cristian.helpdesk.helpdesk.model.User;
import com.cristian.helpdesk.helpdesk.repository.UserRepository;
import com.cristian.helpdesk.helpdesk.security.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AuthService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private PasswordEncoder passwordEncoder;

    /**
     * Metodo que valida las credenciales de un usuario y de estar presenta en la base
     * de datos genera un token unico con el correo del usuario
     */
    public Optional<AuthResponse> login(AuthRequest request) {
        System.out.println(passwordEncoder.encode("algo"));
        Optional<User> user = userRepository.findByEmail(request.getEmail());
        if (user.isPresent()) {
            if (passwordEncoder.matches(request.getPassword(), user.get().getPassword())) {
                String token = jwtUtil.generateToken(user.get().getEmail());
                return Optional.of(new AuthResponse(token));
            }
        }
        return Optional.empty();
    }
}
