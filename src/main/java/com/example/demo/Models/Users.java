package com.example.demo.Models;

import jakarta.persistence.*;
import java.util.List;

@Entity
public class Users {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // ID único proporcionado por Google
    @Column(unique = true)
    private String googleId;

    private String email;
    private String name;
    private String pictureUrl; // (opcional) para guardar la imagen de perfil

    // Relación con notas
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Notes> notes;

    public Users() {}

    public Users(String googleId, String email, String name, String pictureUrl) {
        this.googleId = googleId;
        this.email = email;
        this.name = name;
        this.pictureUrl = pictureUrl;
    }

    // Getters y setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getGoogleId() { return googleId; }
    public void setGoogleId(String googleId) { this.googleId = googleId; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getPictureUrl() { return pictureUrl; }
    public void setPictureUrl(String pictureUrl) { this.pictureUrl = pictureUrl; }

    public List<Notes> getNotes() { return notes; }
    public void setNotes(List<Notes> notes) { this.notes = notes; }
};

