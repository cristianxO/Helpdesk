package com.cristian.helpdesk.helpdesk.service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.cristian.helpdesk.helpdesk.dto.AuthRequest;
import com.cristian.helpdesk.helpdesk.dto.AuthResponse;
import com.cristian.helpdesk.helpdesk.model.User;
import com.cristian.helpdesk.helpdesk.repository.UserRepository;
import com.cristian.helpdesk.helpdesk.security.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Optional;

@Service
public class AuthService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private JwtUtil jwtUtil;

    /**
     * Metodo que valida las credenciales de un usuario y de estar presenta en la base
     * de datos genera un token unico con el correo del usuario
     */
    public Optional<AuthResponse> login(AuthRequest request) {
        Optional<User> user = userRepository.findByEmailAndPassword(request.getEmail(), request.getPassword());
        if (user.isPresent()) {
            String token = jwtUtil.generateToken(user.get().getEmail());
            return Optional.of(new AuthResponse(token));
        }
        return Optional.empty();
    }
}
