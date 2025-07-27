package com.example.demo.Models;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import com.example.demo.Models.*;
import com.example.demo.Repository.NotesRepository;
import com.example.demo.Service.NotesService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
public class NotesServiceTest {

    @Mock
    private NotesRepository notesRepository;

    @InjectMocks
    private NotesService notesService;

    @Test
    public void testUpdateNote() {
        Notes originalNote = new Notes();
        originalNote.setId(1L);
        originalNote.setTitle("Original");
        originalNote.setContent("Contenido original");
        originalNote.setStartDate(LocalDateTime.now());
        originalNote.setEndDate(LocalDateTime.now().plusDays(1));
        originalNote.setState(NoteState.PENDING);

        Notes updatedNote = new Notes();
        updatedNote.setTitle("Actualizado");
        updatedNote.setContent("Contenido actualizado");
        updatedNote.setStartDate(originalNote.getStartDate());
        updatedNote.setEndDate(originalNote.getEndDate().plusDays(1));
        updatedNote.setState(NoteState.COMPLETED);

        when(notesRepository.findById(1L)).thenReturn(Optional.of(originalNote));
        when(notesRepository.save(any(Notes.class))).thenAnswer(invocation -> invocation.getArgument(0));

        Optional<Notes> result = notesService.updateNote(1L, updatedNote);

        assertTrue(result.isPresent());
        assertEquals("Actualizado", result.get().getTitle());
        assertEquals("Contenido actualizado", result.get().getContent());
        assertEquals(NoteState.COMPLETED, result.get().getState());
    }

    @Test
    public void testUpdateNote_EndDateBeforeStartDate_ThrowsException() {
        Notes originalNote = new Notes();
        originalNote.setId(1L);
        originalNote.setTitle("Original");
        originalNote.setContent("Contenido original");
        originalNote.setStartDate(LocalDateTime.now());
        originalNote.setEndDate(LocalDateTime.now().plusDays(1));
        originalNote.setState(NoteState.PENDING);

        Notes updatedNote = new Notes();
        updatedNote.setTitle("Actualizado");
        updatedNote.setContent("Contenido actualizado");
        updatedNote.setStartDate(originalNote.getStartDate());
        // Fecha de fin antes que fecha de inicio para provocar error
        updatedNote.setEndDate(originalNote.getStartDate().minusDays(1));
        updatedNote.setState(NoteState.COMPLETED);

        when(notesRepository.findById(1L)).thenReturn(Optional.of(originalNote));

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            notesService.updateNote(1L, updatedNote);
        });

        assertEquals("La fecha de fin no puede ser anterior a la fecha de inicio.", exception.getMessage());
    }
}
