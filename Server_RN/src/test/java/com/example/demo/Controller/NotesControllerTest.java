package com.example.demo.Controller;

import com.example.demo.Models.Notes;
import com.example.demo.Service.NotesService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Arrays;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
class NotesControllerTest {

    @InjectMocks
    private NotesController notesController;  // Inyección del controlador

    @Mock
    private NotesService notesService;  // Mock del servicio

    @Test
    void testRegisterNotes() {
        Notes newNote = new Notes();
        newNote.setTitle("Test Note");
        newNote.setContent("Test Description");

        when(notesService.saveNote(any(Notes.class))).thenReturn(newNote);

        ResponseEntity<Notes> response = notesController.registerNotes(newNote);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals("Test Note", response.getBody().getTitle());
    }

    @Test
    void testGetNoteById_Found() {
        Notes existingNote = new Notes();
        existingNote.setId(1L);
        existingNote.setTitle("Existing Note");
        existingNote.setContent("Description of the existing note");

        when(notesService.getNoteById(1L)).thenReturn(Optional.of(existingNote));

        ResponseEntity<Notes> response = notesController.getNoteById(1L);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals("Existing Note", response.getBody().getTitle());
    }

    @Test
    void testGetNoteById_NotFound() {
        when(notesService.getNoteById(999L)).thenReturn(Optional.empty());

        ResponseEntity<Notes> response = notesController.getNoteById(999L);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertNull(response.getBody());
    }

    @Test
    void testGetNotes() {
        Notes note1 = new Notes();
        note1.setTitle("Note 1");
        Notes note2 = new Notes();
        note2.setTitle("Note 2");

        when(notesService.getAllNotes()).thenReturn(Arrays.asList(note1, note2));

        ResponseEntity<List<Notes>> response = notesController.getNotes();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(2, response.getBody().size());
    }

    @Test
    void testDeleteNote_Success() {
        when(notesService.deleteNote(1L)).thenReturn(true);

        ResponseEntity<String> response = notesController.deleteNotes(1L);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Nota eliminada exitosamente", response.getBody());
    }

    @Test
    void testDeleteNote_NotFound() {
        when(notesService.deleteNote(999L)).thenReturn(false);

        ResponseEntity<String> response = notesController.deleteNotes(999L);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertEquals("Nota no encontrada", response.getBody());
    }

    @Test
    void testUpdateNotes_Success() {
        Notes existingNote = new Notes();
        existingNote.setId(1L);
        existingNote.setTitle("Updated Note");

        Notes updatedNote = new Notes();
        updatedNote.setTitle("Updated Title");

        when(notesService.updateNote(1L, updatedNote)).thenReturn(Optional.of(existingNote));

        ResponseEntity<Notes> response = notesController.updateNotes(1L, updatedNote);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals("Updated Note", response.getBody().getTitle());
    }

    @Test
    void testUpdateNotes_NotFound() {
        Notes updatedNote = new Notes();
        updatedNote.setTitle("Updated Title");

        when(notesService.updateNote(999L, updatedNote)).thenReturn(Optional.empty());

        ResponseEntity<Notes> response = notesController.updateNotes(999L, updatedNote);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertNull(response.getBody());
    }
}
