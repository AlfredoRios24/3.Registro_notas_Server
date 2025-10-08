package com.example.demo;

import com.example.demo.Models.NoteState;
import com.example.demo.Models.Notes;
import com.example.demo.Models.Users;
import com.example.demo.Service.NotesService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/")
@CrossOrigin(origins = "http://localhost:5173")
public class NotesController {

    private final NotesService notesService;

    // Inyección por constructor
    public NotesController(NotesService notesService) {
        this.notesService = notesService;
    }

    // Crear una nota asociada al usuario autenticado
    @PostMapping("/register/")
    public ResponseEntity<Notes> registerNotes(@RequestBody Notes notes, Authentication auth) {
        Users user = (Users) auth.getPrincipal();

        if (notes.getCreateAt() == null) {
            notes.setCreateAt(LocalDateTime.now());
        }
        if (notes.getState() == null) {
            notes.setState(NoteState.PENDING);
        }

        Notes savedNotes = notesService.saveNoteForUser(notes, user);
        return ResponseEntity.ok(savedNotes);
    }

    // Obtener todas las notas del usuario
    @GetMapping("/notes/")
    public ResponseEntity<List<Notes>> getNotes(Authentication auth) {
        Users user = (Users) auth.getPrincipal();
        List<Notes> notes = notesService.getAllNotesForUser(user);
        return ResponseEntity.ok(notes);
    }

    // Obtener una nota por ID del usuario
    @GetMapping("/notes/{id}")
    public ResponseEntity<Notes> getNoteById(@PathVariable Long id, Authentication auth) {
        Users user = (Users) auth.getPrincipal();
        Optional<Notes> noteOptional = notesService.getNoteByIdForUser(id, user);
        return noteOptional.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.status(404).body(null));
    }

    // Actualizar nota del usuario
    @PutMapping("/notes/{id}")
    public ResponseEntity<?> updateNote(@PathVariable Long id, @RequestBody Notes updatedNote, Authentication auth) {
        Users user = (Users) auth.getPrincipal();

        if (updatedNote.getStartDate() != null && updatedNote.getEndDate() != null &&
                updatedNote.getEndDate().isBefore(updatedNote.getStartDate())) {
            return ResponseEntity.badRequest()
                    .body("La fecha de fin no puede ser anterior a la fecha de inicio.");
        }

        try {
            Optional<Notes> updated = notesService.updateNoteForUser(id, updatedNote, user);
            return updated.map(ResponseEntity::ok)
                    .orElseGet(() -> ResponseEntity.notFound().build());
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    // Eliminar nota del usuario
    @DeleteMapping("/notes/{id}")
    public ResponseEntity<String> deleteNotes(@PathVariable Long id, Authentication auth) {
        Users user = (Users) auth.getPrincipal();
        boolean deleted = notesService.deleteNoteForUser(id, user);
        if (deleted) {
            return ResponseEntity.ok("Nota eliminada exitosamente");
        } else {
            return ResponseEntity.status(404).body("Nota no encontrada");
        }
    }
}
