package com.example.demo.Service;

import com.example.demo.Models.NoteState;
import com.example.demo.Models.Notes;
import com.example.demo.Repository.NotesRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class NotesServiceTest {

    @Mock
    private NotesRepository notesRepository;

    @InjectMocks
    private NotesService notesService;

    private Notes note;

    @BeforeEach
    void setup() {
        note = new Notes();
        note.setId(1L);
        note.setTitle("Nota de prueba");
        note.setContent("Contenido de prueba");
        note.setCreateAt(LocalDateTime.now());
        note.setState(NoteState.PENDING);
    }

    @Test
    void saveNote_deberiaGuardarNotaCorrectamente() {
        when(notesRepository.save(any(Notes.class))).thenReturn(note);

        Notes result = notesService.saveNote(note);

        assertNotNull(result);
        assertEquals("Nota de prueba", result.getTitle());
        verify(notesRepository, times(1)).save(any(Notes.class));
    }

    @Test
    void getNoteById_deberiaRetornarNota() {
        when(notesRepository.findById(1L)).thenReturn(Optional.of(note));

        Optional<Notes> found = notesService.getNoteById(1L);

        assertTrue(found.isPresent());
        assertEquals("Nota de prueba", found.get().getTitle());
    }

    @Test
    void getAllNotes_deberiaRetornarListaDeNotas() {
        when(notesRepository.findAll()).thenReturn(List.of(note));

        List<Notes> notes = notesService.getAllNotes();

        assertEquals(1, notes.size());
        verify(notesRepository, times(1)).findAll();
    }

    @Test
    void deleteNote_deberiaEliminarSiExiste() {
        when(notesRepository.findById(1L)).thenReturn(Optional.of(note));

        boolean deleted = notesService.deleteNote(1L);

        assertTrue(deleted);
        verify(notesRepository).deleteById(1L);
    }

    @Test
    void deleteNote_noDebeEliminarSiNoExiste() {
        when(notesRepository.findById(1L)).thenReturn(Optional.empty());

        boolean deleted = notesService.deleteNote(1L);

        assertFalse(deleted);
        verify(notesRepository, never()).deleteById(any());
    }

    @Test
    void updateNote_deberiaActualizarNotaExistente() {
        Notes updated = new Notes();
        updated.setTitle("Título nuevo");
        updated.setContent("Contenido nuevo");
        updated.setState(NoteState.IN_PROGRESS);

        when(notesRepository.findById(1L)).thenReturn(Optional.of(note));
        when(notesRepository.save(any(Notes.class))).thenReturn(updated);

        Optional<Notes> result = notesService.updateNote(1L, updated);

        assertTrue(result.isPresent());
        assertEquals("Título nuevo", result.get().getTitle());
    }

    @Test
    void updateNote_noDebeActualizarSiNoExiste() {
        when(notesRepository.findById(1L)).thenReturn(Optional.empty());

        Optional<Notes> result = notesService.updateNote(1L, note);

        assertTrue(result.isEmpty());
        verify(notesRepository, never()).save(any());
    }

    @Test
    void updateNote_lanzaExcepcionSiEndDateAntesDeStartDate() {
        Notes updated = new Notes();
        updated.setStartDate(LocalDateTime.now());
        updated.setEndDate(LocalDateTime.now().minusDays(1));

        when(notesRepository.findById(1L)).thenReturn(Optional.of(note));

        assertThrows(IllegalArgumentException.class,
                () -> notesService.updateNote(1L, updated));
    }
}
