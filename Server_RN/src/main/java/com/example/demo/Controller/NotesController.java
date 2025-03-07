package com.example.demo.Controller;

import com.example.demo.Models.Notes;
import com.example.demo.Repository.NotesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/")
@CrossOrigin(origins = "http://localhost:5173")
public class NotesController {

    @Autowired
    private NotesRepository notesRepository;

    NotesController(NotesRepository notesRepository){
        this.notesRepository = notesRepository;
    }


    // Método para guardar todas las notas
    @PostMapping("/register/")
    public ResponseEntity<Notes> registerNotes (@RequestBody Notes notes){
        Notes saveNotes = notesRepository.save(notes);
        return ResponseEntity.ok(saveNotes);
    }

    //Método para obtener 1 nota con ID
    @GetMapping("/notes/{id}")
    public ResponseEntity<Notes> getNoteById(@PathVariable Long id) {
        Optional<Notes> noteOptional = notesRepository.findById(id);
        if (noteOptional.isPresent()) {
            return ResponseEntity.ok(noteOptional.get());
        } else {
            return ResponseEntity.status(404).body(null);
        }
    }

    // Método para obtener todas las notas
   @GetMapping("/notes/")
   public ResponseEntity<List<Notes>> getNotes(){
        try {
           List<Notes> notes = notesRepository.findAll();
           return ResponseEntity.ok(notes);
       } catch (Exception e) {
           e.printStackTrace(); // Muestra el error completo en los logs
           return ResponseEntity.status(500).body(null); // Devuelve una respuesta 500
       }
   }

    // Método para eliminar una nota por ID
    @DeleteMapping("/notes/{id}")
    public ResponseEntity<String> deleteNotes(@PathVariable Long id){
        Optional<Notes> notesOptional = notesRepository.findById(id);
            if (notesOptional.isPresent()){
                notesRepository.deleteById(id);
                return ResponseEntity.ok("Nota eliminada existosamente");
            } else {
                return ResponseEntity.status(404).body("Usuario no encontrado");
            }
    }

    // Método para actualizar una nota
    @PutMapping("/notes/{id}")
    public ResponseEntity<Notes> updateNotes(@PathVariable Long id, @RequestBody Notes updatedNotes) {
        Optional<Notes> notesOptional = notesRepository.findById(id);
        if (notesOptional.isPresent()) {
            Notes notes = notesOptional.get();
            notes.setTitle(updatedNotes.getTitle());
            notes.setContent(updatedNotes.getContent());

            Notes savedNotes = notesRepository.save(notes);
            return ResponseEntity.ok(savedNotes);
        } else {
            return ResponseEntity.status(404).body(null);
        }
    }

}
