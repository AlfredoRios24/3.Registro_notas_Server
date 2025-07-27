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
    private NoteState state;

    public Notes(Long l, String testTitle, String testContent, LocalDateTime now, LocalDateTime localDateTime, LocalDateTime now1, NoteState noteState) {
    }

    public Notes() {
        // Constructor vacío requerido para crear objetos sin parámetros
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
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

    // Aquí no va validación para evitar lanzar excepción
    public void setEndDate(LocalDateTime endDate) {
        this.endDate = endDate;
    }

    public NoteState getState() {
        return state;
    }

    public void setState(NoteState state) {
        this.state = state;
    }
}
