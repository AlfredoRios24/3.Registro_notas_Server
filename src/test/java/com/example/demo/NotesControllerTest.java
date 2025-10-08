package com.example.demo;

import com.example.demo.Models.NoteState;
import com.example.demo.Models.Notes;
import com.example.demo.Models.Users;
import com.example.demo.Service.NotesService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

class NotesControllerTest {

    @Mock
    private NotesService notesService;

    @Mock
    private Authentication authentication;

    @InjectMocks
    private NotesController notesController;

    private Users testUser;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        testUser = new Users();
        testUser.setId(1L);
        testUser.setName("Test User");
        testUser.setEmail("test@example.com");

        when(authentication.getPrincipal()).thenReturn(testUser);
    }

    @Test
    void testRegisterNotes() {
        Notes note = new Notes();
        note.setTitle("New Note");
        note.setContent("Content");

        when(notesService.saveNoteForUser(note, testUser)).thenReturn(note);

        ResponseEntity<Notes> response = notesController.registerNotes(note, authentication);

        assertEquals(200, response.getStatusCodeValue());
        assertEquals(note, response.getBody());
        verify(notesService, times(1)).saveNoteForUser(note, testUser);
    }

    @Test
    void testGetNotes() {
        Notes note = new Notes();
        note.setUser(testUser);

        when(notesService.getAllNotesForUser(testUser)).thenReturn(List.of(note));

        ResponseEntity<List<Notes>> response = notesController.getNotes(authentication);

        assertEquals(200, response.getStatusCodeValue());
        assertEquals(1, response.getBody().size());
        assertEquals(testUser, response.getBody().get(0).getUser());
    }

    @Test
    void testGetNoteById() {
        Notes note = new Notes();
        note.setId(1L);
        note.setUser(testUser);

        when(notesService.getNoteByIdForUser(1L, testUser)).thenReturn(Optional.of(note));

        ResponseEntity<Notes> response = notesController.getNoteById(1L, authentication);

        assertEquals(200, response.getStatusCodeValue());
        assertEquals(note, response.getBody());
    }

    @Test
    void testUpdateNote() {
        Notes updatedNote = new Notes();
        updatedNote.setTitle("Updated");
        updatedNote.setContent("Updated content");

        Notes returnedNote = new Notes();
        returnedNote.setId(1L);
        returnedNote.setTitle("Updated");
        returnedNote.setContent("Updated content");
        returnedNote.setUser(testUser);

        when(notesService.updateNoteForUser(1L, updatedNote, testUser)).thenReturn(Optional.of(returnedNote));

        ResponseEntity<?> response = notesController.updateNote(1L, updatedNote, authentication);

        assertEquals(200, ((ResponseEntity<?>) response).getStatusCodeValue());
        assertEquals(returnedNote, ((ResponseEntity<?>) response).getBody());
    }

    @Test
    void testDeleteNote() {
        when(notesService.deleteNoteForUser(1L, testUser)).thenReturn(true);

        ResponseEntity<String> response = notesController.deleteNotes(1L, authentication);

        assertEquals(200, response.getStatusCodeValue());
        assertEquals("Nota eliminada exitosamente", response.getBody());
    }

    @Test
    void testDeleteNoteNotFound() {
        when(notesService.deleteNoteForUser(2L, testUser)).thenReturn(false);

        ResponseEntity<String> response = notesController.deleteNotes(2L, authentication);

        assertEquals(404, response.getStatusCodeValue());
        assertEquals("Nota no encontrada", response.getBody());
    }
}
