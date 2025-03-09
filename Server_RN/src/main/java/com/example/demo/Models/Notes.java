package com.example.demo.Models;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

import java.time.LocalDateTime;

@Entity
public class Notes {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;
    private String content;
    private LocalDateTime createAt;
    private LocalDateTime startDate;
    private LocalDateTime endDate;

    // Constructor
    public Notes() {}

    public Notes(String titleNotes, String content) {
        setTitle(titleNotes);  // Se usa el método setTitle para validar
        setContent(content);   // Se usa el método setContent para validar
        this.createAt = LocalDateTime.now();
        this.startDate = LocalDateTime.now();
    }

    // Método para validar que el título no esté vacío
    public void setTitle(String title) {
        if (title == null || title.trim().isEmpty()) {
            throw new IllegalArgumentException("El título no puede estar vacío.");
        }
        this.title = title;
    }

    // Método para validar que el contenido no esté vacío
    public void setContent(String content) {
        if (content == null || content.trim().isEmpty()) {
            throw new IllegalArgumentException("El contenido no puede estar vacío.");
        }
        this.content = content;
    }

    // Método para establecer la fecha de fin y validarla
    public void setEndDate(LocalDateTime endDate) {
        if (this.startDate == null) {
            throw new IllegalArgumentException("La fecha de inicio no puede ser nula.");
        }
        if (endDate.isBefore(this.startDate)) {
            throw new IllegalArgumentException("La fecha de fin no puede ser anterior a la fecha de inicio.");
        }
        this.endDate = endDate;
    }

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public String getContent() {
        return content;
    }

    public LocalDateTime getCreateAt() {
        return createAt;
    }

    public void setCreateAt(LocalDateTime createAt) {
        this.createAt = createAt;
    }

    public LocalDateTime getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDateTime startDate) {
        this.startDate = startDate;
    }

    public LocalDateTime getEndDate() {
        return endDate;
    }
}
