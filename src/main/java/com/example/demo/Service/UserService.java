package com.example.demo.Service;

import com.example.demo.Models.Users;
import com.example.demo.Repository.UsersRepository;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private final UsersRepository userRepository;

    public UserService(UsersRepository userRepository) {
        this.userRepository = userRepository;
    }

    // Método para cargar usuario por Google ID
    public Users loadUserByGoogleId(String googleId) {
        return userRepository.findByGoogleId(googleId)
                .orElseThrow(() -> new UsernameNotFoundException("Usuario no encontrado"));
    }

    // Método opcional: crear usuario si no existe
    public Users createUserIfNotExists(String googleId, String email, String name, String pictureUrl) {
        return userRepository.findByGoogleId(googleId)
                .orElseGet(() -> userRepository.save(new Users(googleId, email, name, pictureUrl)));
    }
}
