package com.example.demo.Service;

import com.example.demo.Models.NoteState;
import com.example.demo.Models.Notes;
import com.example.demo.Models.Users;
import com.example.demo.Repository.NotesRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

class NotesServiceTest {

    @Mock
    private NotesRepository notesRepository;

    @InjectMocks
    private NotesService notesService;

    private Users testUser;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        testUser = new Users();
        testUser.setId(1L);
        testUser.setName("Test User");
        testUser.setEmail("test@example.com");
    }

    @Test
    void testSaveNoteForUser() {
        Notes note = new Notes();
        note.setTitle("Test Note");
        note.setContent("Content");

        when(notesRepository.save(any(Notes.class))).thenAnswer(i -> i.getArgument(0));

        Notes saved = notesService.saveNoteForUser(note, testUser);

        assertNotNull(saved.getCreateAt());
        assertEquals(NoteState.PENDING, saved.getState());
        assertEquals(testUser, saved.getUser());
        verify(notesRepository, times(1)).save(any(Notes.class));
    }

    @Test
    void testGetAllNotesForUser() {
        Notes note = new Notes();
        note.setUser(testUser);

        when(notesRepository.findAllByUser(testUser)).thenReturn(List.of(note));

        List<Notes> notes = notesService.getAllNotesForUser(testUser);

        assertEquals(1, notes.size());
        assertEquals(testUser, notes.get(0).getUser());
    }

    @Test
    void testGetNoteByIdForUser() {
        Notes note = new Notes();
        note.setId(1L);
        note.setUser(testUser);

        when(notesRepository.findByIdAndUser(1L, testUser)).thenReturn(Optional.of(note));

        Optional<Notes> result = notesService.getNoteByIdForUser(1L, testUser);

        assertTrue(result.isPresent());
        assertEquals(1L, result.get().getId());
        assertEquals(testUser, result.get().getUser());
    }

    @Test
    void testUpdateNoteForUser() {
        Notes existingNote = new Notes();
        existingNote.setId(1L);
        existingNote.setUser(testUser);
        existingNote.setTitle("Old Title");
        existingNote.setContent("Old Content");
        existingNote.setState(NoteState.PENDING);
        existingNote.setCreateAt(LocalDateTime.now());

        Notes updatedNote = new Notes();
        updatedNote.setTitle("Updated Title");
        updatedNote.setContent("Updated Content");
        updatedNote.setState(NoteState.COMPLETED);

        when(notesRepository.findByIdAndUser(1L, testUser)).thenReturn(Optional.of(existingNote));
        when(notesRepository.save(any(Notes.class))).thenAnswer(i -> i.getArgument(0));

        Optional<Notes> result = notesService.updateNoteForUser(1L, updatedNote, testUser);

        assertTrue(result.isPresent());
        assertEquals("Updated Title", result.get().getTitle());
        assertEquals("Updated Content", result.get().getContent());
        assertEquals(NoteState.COMPLETED, result.get().getState());
    }

    @Test
    void testDeleteNoteForUser() {
        Notes note = new Notes();
        note.setId(1L);
        note.setUser(testUser);

        when(notesRepository.findByIdAndUser(1L, testUser)).thenReturn(Optional.of(note));
        doNothing().when(notesRepository).delete(note);

        boolean deleted = notesService.deleteNoteForUser(1L, testUser);

        assertTrue(deleted);
        verify(notesRepository, times(1)).delete(note);
    }

    @Test
    void testDeleteNoteForUserNotFound() {
        when(notesRepository.findByIdAndUser(2L, testUser)).thenReturn(Optional.empty());

        boolean deleted = notesService.deleteNoteForUser(2L, testUser);

        assertFalse(deleted);
    }
}
