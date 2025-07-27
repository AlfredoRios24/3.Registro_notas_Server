package com.example.demo.Models;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
public class Notes {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;
    private String content;

    @Column(name = "start_date")
    private LocalDateTime startDate;

    @Column(name = "end_date")
    private LocalDateTime endDate;

    @Column(name = "create_at")
    private LocalDateTime createAt;

    @Enumerated(EnumType.STRING)
    @Column(name = "state")
    private NoteState state;

    //Constructor


    public Notes() {
    }

    // Constructor de conveniencia (opcional)
    public Notes(String testTitle, String testContent) {
        this.title = testTitle;
        this.content = testContent;
    }

    public Notes(Long id, String title, String content, LocalDateTime endDate, LocalDateTime startDate, LocalDateTime createAt, NoteState state) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.endDate = endDate;
        this.startDate = startDate;
        this.createAt = createAt;
        this.state = state;
    }

    //Getters ands Setters
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

    public LocalDateTime getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDateTime startDate) {
        this.startDate = startDate;
    }

    public LocalDateTime getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDateTime endDate) {
        this.endDate = endDate;
    }

    public LocalDateTime getCreateAt() {
        return createAt;
    }

    public void setCreateAt(LocalDateTime createAt) {
        this.createAt = createAt;
    }

    public NoteState getState() {
        return state;
    }

    public void setState(NoteState state) {
        this.state = state;
    }
}
