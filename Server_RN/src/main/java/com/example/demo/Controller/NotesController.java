package com.example.demo.Controller;

import com.example.demo.Models.Notes;
import com.example.demo.Repository.NotesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/notes")
@CrossOrigin(origins = "http://localhost:5173")
public class NotesController {

    @Autowired
    private NotesRepository notesRepository;

    // Método para guardar una nota
    @PostMapping("")
    public ResponseEntity<Notes> registerNotes(@RequestBody Notes notes) {
        // Establecer startDate y endDate si no están presentes
        if (notes.getStartDate() == null) {
            notes.setStartDate(LocalDateTime.now());
        }
        if (notes.getEndDate() == null) {
            notes.setEndDate(LocalDateTime.now());  // O puedes usar un valor distinto si lo prefieres
        }
        // Guardar la nota
        Notes savedNotes = notesRepository.save(notes);
        return ResponseEntity.ok(savedNotes);
    }

    // Método para obtener una nota por ID
    @GetMapping("/{id}")
    public ResponseEntity<Notes> getNoteById(@PathVariable Long id) {
        Optional<Notes> noteOptional = notesRepository.findById(id);
        return noteOptional.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.status(404).body(null));
    }

    // Método para obtener todas las notas
    @GetMapping("")
    public ResponseEntity<List<Notes>> getNotes() {
        try {
            List<Notes> notes = notesRepository.findAll();
            return ResponseEntity.ok(notes);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body(null);
        }
    }

    // Método para eliminar una nota por ID
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteNotes(@PathVariable Long id) {
        Optional<Notes> notesOptional = notesRepository.findById(id);
        if (notesOptional.isPresent()) {
            notesRepository.deleteById(id);
            return ResponseEntity.ok("Nota eliminada exitosamente");
        } else {
            return ResponseEntity.status(404).body("Nota no encontrada");
        }
    }

    // Método para actualizar una nota
    @PutMapping("/{id}")
    public ResponseEntity<Notes> updateNotes(@PathVariable Long id, @RequestBody Notes updatedNotes) {
        Optional<Notes> notesOptional = notesRepository.findById(id);
        if (notesOptional.isPresent()) {
            Notes notes = notesOptional.get();
            notes.setTitle(updatedNotes.getTitle());
            notes.setContent(updatedNotes.getContent());

            // Si las fechas no se proporcionan, asignar fechas por defecto
            if (updatedNotes.getStartDate() == null) {
                notes.setStartDate(LocalDateTime.now());
            } else {
                notes.setStartDate(updatedNotes.getStartDate());
            }

            if (updatedNotes.getEndDate() == null) {
                notes.setEndDate(LocalDateTime.now()); // O puedes poner otro valor
            } else {
                notes.setEndDate(updatedNotes.getEndDate());
            }

            Notes savedNotes = notesRepository.save(notes);
            return ResponseEntity.ok(savedNotes);
        } else {
            return ResponseEntity.status(404).body(null);
        }
    }
}