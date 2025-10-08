package com.example.demo;

import com.example.demo.Models.NoteState;
import com.example.demo.Models.Notes;
import com.example.demo.Repository.NotesRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;

import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
class NotesControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private NotesRepository notesRepository;

    @Autowired
    private ObjectMapper objectMapper;

    private Notes note;

    @BeforeEach
    void setup() {
        notesRepository.deleteAll();

        note = new Notes();
        note.setTitle("Nota de integración");
        note.setContent("Contenido de integración");
        note.setCreateAt(LocalDateTime.now());
        note.setState(NoteState.PENDING);

        notesRepository.save(note);
    }

    @Test
    void deberiaListarNotas() throws Exception {
        mockMvc.perform(get("/api/notes/"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].title").value("Nota de integración"));
    }

    @Test
    void deberiaObtenerNotaPorId() throws Exception {
        mockMvc.perform(get("/api/notes/{id}", note.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title").value("Nota de integración"));
    }

    @Test
    void deberiaCrearNota() throws Exception {
        Notes newNote = new Notes();
        newNote.setTitle("Nueva nota");
        newNote.setContent("Contenido nuevo");

        mockMvc.perform(post("/api/register/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(newNote)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title").value("Nueva nota"));
    }

    @Test
    void deberiaActualizarNota() throws Exception {
        note.setTitle("Título actualizado");

        mockMvc.perform(put("/api/notes/{id}", note.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(note)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title").value("Título actualizado"));
    }

    @Test
    void deberiaEliminarNota() throws Exception {
        mockMvc.perform(delete("/api/notes/{id}", note.getId()))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("Nota eliminada exitosamente")));
    }

    @Test
    void deberiaRetornar404SiNoExisteNota() throws Exception {
        mockMvc.perform(get("/api/notes/{id}", 999))
                .andExpect(status().isNotFound());
    }
}
