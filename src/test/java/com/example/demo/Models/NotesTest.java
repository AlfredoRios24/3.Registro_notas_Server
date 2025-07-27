package com.example.demo.Models;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

public class NotesTest {

    private Notes note;

    @BeforeEach
    public void init() {
        this.note = new Notes();
    }

    @Test
    public void testSetEndDateValid() {
        // When
        LocalDateTime futureDate = LocalDateTime.now().plusDays(2);
        note.setStartDate(LocalDateTime.now());  // Asegurarse de que la fecha de inicio esté establecida

        // Given
        note.setEndDate(futureDate);

        // Then
        assertEquals(futureDate, note.getEndDate());
    }

    @Test
    public void testSetTitleNotEmpty() {
        // When & Then
        assertThrows(IllegalArgumentException.class, () -> {
            note.setTitle("");  // Título vacío
        });

        assertThrows(IllegalArgumentException.class, () -> {
            note.setTitle(null);  // Título null
        });
    }

    @Test
    public void testSetValidTitle() {
        // When
        note.setTitle("Valid Title");

        // Then
        assertEquals("Valid Title", note.getTitle());
    }

    // Prueba unitaria para setContent
    @Test
    public void testSetContentThrowsExceptionWhenContentIsNull() {
        // When & Then
        assertThrows(IllegalArgumentException.class, () -> {
            note.setContent(null);
        }, "El contenido no puede estar vacío.");
    }

    @Test
    public void testSetContentThrowsExceptionWhenContentIsEmpty() {
        // When & Then
        assertThrows(IllegalArgumentException.class, () -> {
            note.setContent("");
        }, "El contenido no puede estar vacío.");
    }

    @Test
    public void testSetValidContent() {
        // When
        String validContent = "Contenido de prueba";
        note.setContent(validContent);

        // Then
        assertEquals(validContent, note.getContent());
    }

    @Test
    public void testSetEndDateBeforeStartDateThrowsException() {
        // Establecemos una fecha de inicio
        LocalDateTime startDate = LocalDateTime.now();
        note.setStartDate(startDate);

        // Intentamos establecer una fecha de fin anterior a la fecha de inicio
        LocalDateTime invalidEndDate = startDate.minusDays(1);

        // Verificamos que se lance la excepción esperada
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            note.setEndDate(invalidEndDate);
        });

        assertEquals("La fecha de fin no puede ser anterior a la fecha de inicio.", exception.getMessage());
    }

}
