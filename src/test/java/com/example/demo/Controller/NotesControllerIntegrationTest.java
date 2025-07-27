package com.example.demo.Controller;

import com.example.demo.Models.NoteState;
import com.example.demo.Models.Notes;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class NotesControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper; // Para convertir objetos a JSON

    private Notes note;

    @BeforeEach
    public void setUp() {
        note = new Notes(null, "Test Title", "Test Content",
                LocalDateTime.now(), LocalDateTime.now().plusDays(1),
                LocalDateTime.now(), NoteState.PENDING);
    }

    @Test
    public void testRegisterNote() throws Exception {
        String noteJson = objectMapper.writeValueAsString(note);

        mockMvc.perform(post("/api/register/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(noteJson))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title").value("Test Title"))
                .andExpect(jsonPath("$.content").value("Test Content"))
                .andExpect(jsonPath("$.state").value("PENDING"));
    }

    @Test
    public void testGetNoteById() throws Exception {
        // Crear la nota antes de buscarla
        String noteJson = objectMapper.writeValueAsString(note);
        String response = mockMvc.perform(post("/api/register/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(noteJson))
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();

        Notes savedNote = objectMapper.readValue(response, Notes.class);
        Long noteId = savedNote.getId(); // Obtener el ID generado

        mockMvc.perform(get("/api/notes/" + noteId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title").value("Test Title"))
                .andExpect(jsonPath("$.content").value("Test Content"));
    }

    @Test
    public void testDeleteNote() throws Exception {
        // Crear la nota antes de eliminarla
        String noteJson = objectMapper.writeValueAsString(note);
        String response = mockMvc.perform(post("/api/register/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(noteJson))
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();

        Notes savedNote = objectMapper.readValue(response, Notes.class);
        Long noteId = savedNote.getId(); // Obtener el ID generado

        mockMvc.perform(delete("/api/notes/" + noteId))
                .andExpect(status().isOk())
                .andExpect(content().string("Nota eliminada exitosamente"));

        // Verificar que la nota ya no existe
        mockMvc.perform(get("/api/notes/" + noteId))
                .andExpect(status().isNotFound());
    }
}
