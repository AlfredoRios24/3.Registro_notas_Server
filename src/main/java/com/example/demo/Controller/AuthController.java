package com.example.demo.Controller;

import com.example.demo.Service.AuthService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/google")
    public ResponseEntity<Map<String, String>> loginWithGoogle(@RequestBody Map<String, String> payload) {
        String googleId = payload.get("googleId");
        String email = payload.get("email");
        String name = payload.get("name");

        String jwt = authService.loginWithGoogle(googleId, email, name);
        return ResponseEntity.ok(Map.of("token", jwt));
    }
}
