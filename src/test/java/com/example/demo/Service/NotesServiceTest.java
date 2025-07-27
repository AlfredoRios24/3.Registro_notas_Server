package com.example.demo.Service;

import com.example.demo.Models.NoteState;
import com.example.demo.Models.Notes;
import com.example.demo.Repository.NotesRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class NotesServiceTest {

    @InjectMocks
    private NotesService notesService;

    @Mock
    private NotesRepository notesRepository;

    private Notes note;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        note = new Notes(1L, "Test Title", "Test Content", LocalDateTime.now(), LocalDateTime.now().plusDays(1), LocalDateTime.now(), NoteState.PENDING);
    }

    @Test
    public void testSaveNote() {
        when(notesRepository.save(any(Notes.class))).thenReturn(note);

        Notes savedNote = notesService.saveNote(note);

        assertNotNull(savedNote);
        assertEquals("Test Title", savedNote.getTitle());
        verify(notesRepository, times(1)).save(any(Notes.class));
    }

    @Test
    public void testGetNoteById() {
        when(notesRepository.findById(1L)).thenReturn(Optional.of(note));

        Optional<Notes> foundNote = notesService.getNoteById(1L);

        assertTrue(foundNote.isPresent());
        assertEquals("Test Title", foundNote.get().getTitle());
    }

    @Test
    public void testDeleteNote() {
        when(notesRepository.findById(1L)).thenReturn(Optional.of(note));

        boolean deleted = notesService.deleteNote(1L);

        assertTrue(deleted);
        verify(notesRepository, times(1)).deleteById(1L);
    }

    @Test
    public void testUpdateNote() {
        Notes updatedNote = new Notes(1L, "Updated Title", "Updated Content", LocalDateTime.now(), LocalDateTime.now().plusDays(1), LocalDateTime.now(), NoteState.PENDING);
        when(notesRepository.findById(1L)).thenReturn(Optional.of(note));
        when(notesRepository.save(any(Notes.class))).thenReturn(updatedNote);

        Optional<Notes> result = notesService.updateNote(1L, updatedNote);

        assertTrue(result.isPresent());
        assertEquals("Updated Title", result.get().getTitle());
    }
}
