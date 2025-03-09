package com.example.demo.Controller;

import com.example.demo.Models.NoteState;
import com.example.demo.Models.Notes;
import com.example.demo.Service.NotesService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

public class NotesControllerTest {

    private MockMvc mockMvc;

    @Mock
    private NotesService notesService;

    @InjectMocks
    private NotesController notesController;

    private ObjectMapper objectMapper;
    private Notes note;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(notesController).build();
        objectMapper = new ObjectMapper();
        objectMapper.registerModule(new com.fasterxml.jackson.datatype.jsr310.JavaTimeModule());

        note = new Notes(1L, "Test Title", "Test Content", LocalDateTime.now(), LocalDateTime.now().plusDays(1), LocalDateTime.now(), NoteState.PENDING);
    }

    @Test
    public void testRegisterNotes() throws Exception {
        when(notesService.saveNote(any(Notes.class))).thenReturn(note);

        mockMvc.perform(post("/api/register/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(note)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title").value("Test Title"))
                .andExpect(jsonPath("$.content").value("Test Content"));

        verify(notesService, times(1)).saveNote(any(Notes.class));
    }

    @Test
    public void testGetNoteById_Success() throws Exception {
        when(notesService.getNoteById(1L)).thenReturn(Optional.of(note));

        mockMvc.perform(get("/api/notes/{id}", 1L))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title").value("Test Title"))
                .andExpect(jsonPath("$.content").value("Test Content"));

        verify(notesService, times(1)).getNoteById(1L);
    }

    @Test
    public void testGetNoteById_NotFound() throws Exception {
        when(notesService.getNoteById(1L)).thenReturn(Optional.empty());

        mockMvc.perform(get("/api/notes/{id}", 1L))
                .andExpect(status().isNotFound());

        verify(notesService, times(1)).getNoteById(1L);
    }

    @Test
    public void testDeleteNote_Success() throws Exception {
        when(notesService.deleteNote(1L)).thenReturn(true);

        mockMvc.perform(delete("/api/notes/{id}", 1L))
                .andExpect(status().isOk())
                .andExpect(content().string("Nota eliminada exitosamente"));

        verify(notesService, times(1)).deleteNote(1L);
    }

    @Test
    public void testDeleteNote_NotFound() throws Exception {
        when(notesService.deleteNote(1L)).thenReturn(false);

        mockMvc.perform(delete("/api/notes/{id}", 1L))
                .andExpect(status().isNotFound())
                .andExpect(content().string("Nota no encontrada"));

        verify(notesService, times(1)).deleteNote(1L);
    }

    @Test
    public void testUpdateNote_Success() throws Exception {
        Notes updatedNote = new Notes(1L, "Updated Title", "Updated Content", LocalDateTime.now(), LocalDateTime.now().plusDays(1), LocalDateTime.now(), NoteState.PENDING);

        when(notesService.updateNote(eq(1L), any(Notes.class))).thenReturn(Optional.of(updatedNote));

        mockMvc.perform(put("/api/notes/{id}", 1L)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updatedNote)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title").value("Updated Title"))
                .andExpect(jsonPath("$.content").value("Updated Content"));

        verify(notesService, times(1)).updateNote(eq(1L), any(Notes.class));
    }

    @Test
    public void testUpdateNote_NotFound() throws Exception {
        Notes updatedNote = new Notes(1L, "Updated Title", "Updated Content", LocalDateTime.now(), LocalDateTime.now().plusDays(1), LocalDateTime.now(), NoteState.PENDING);

        when(notesService.updateNote(eq(1L), any(Notes.class))).thenReturn(Optional.empty());

        mockMvc.perform(put("/api/notes/{id}", 1L)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updatedNote)))
                .andExpect(status().isNotFound());

        verify(notesService, times(1)).updateNote(eq(1L), any(Notes.class));
    }
}
