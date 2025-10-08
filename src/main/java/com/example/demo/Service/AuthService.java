package com.example.demo.Service;

import com.example.demo.Models.Users;
import com.example.demo.Repository.UsersRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AuthService {

    private final UsersRepository userRepository;
    private final JwtService jwtService;

    public AuthService(UsersRepository userRepository, JwtService jwtService) {
        this.userRepository = userRepository;
        this.jwtService = jwtService;
    }

    // Login con Google: si no existe usuario, lo crea
    public String loginWithGoogle(String googleId, String email, String name) {
        Optional<Users> existingUser = userRepository.findByGoogleId(googleId);
        Users user = existingUser.orElseGet(() -> {
            Users newUser = new Users();
            newUser.setGoogleId(googleId);
            newUser.setEmail(email);
            newUser.setName(name);
            return userRepository.save(newUser);
        });

        return jwtService.generateToken(user.getGoogleId());
    }
}
