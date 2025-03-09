package com.example.demo.Service;

import com.example.demo.Models.*;
import com.example.demo.Service.NotesService;
import com.example.demo.Repository.NotesRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class NotesServiceTest {

    @Autowired
    private NotesService notesService;

    @Autowired
    private NotesRepository notesRepository;

    @BeforeEach
    void setUp() {
        notesRepository.deleteAll(); // Limpiar la base de datos antes de cada prueba
    }

    @Test
    void saveNoteTest() {
        Notes note = new Notes("Test Title", "Test Content");
        Notes savedNote = notesService.saveNote(note);

        assertNotNull(savedNote);
        assertEquals("Test Title", savedNote.getTitle());
        assertEquals("Test Content", savedNote.getContent());
    }

    @Test
    void getNoteByIdTest() {
        Notes note = new Notes("Test Title", "Test Content");
        Notes savedNote = notesService.saveNote(note);

        Notes foundNote = notesService.getNoteById(savedNote.getId()).orElse(null);

        assertNotNull(foundNote);
        assertEquals(savedNote.getId(), foundNote.getId());
        assertEquals("Test Title", foundNote.getTitle());
    }

    @Test
    void deleteNoteTest() {
        Notes note = new Notes("Test Title", "Test Content");
        Notes savedNote = notesService.saveNote(note);

        boolean result = notesService.deleteNote(savedNote.getId());
        assertTrue(result);
        assertFalse(notesRepository.existsById(savedNote.getId()));
    }

    @Test
    void updateNoteTest() {
        Notes note = new Notes("Old Title", "Old Content");
        Notes savedNote = notesService.saveNote(note);

        savedNote.setTitle("Updated Title");
        savedNote.setContent("Updated Content");

        Notes updatedNote = notesService.updateNote(savedNote.getId(), savedNote).orElse(null);

        assertNotNull(updatedNote);
        assertEquals("Updated Title", updatedNote.getTitle());
        assertEquals("Updated Content", updatedNote.getContent());
    }
}
