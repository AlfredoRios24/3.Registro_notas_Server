package com.example.demo.Service;

import com.example.demo.Models.Notes;
import com.example.demo.Repository.NotesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class NotesService {

    @Autowired
    private NotesRepository notesRepository;

    // Guardar una nueva nota
    public Notes saveNote(Notes notes) {
        return notesRepository.save(notes);
    }

    // Obtener una nota por ID
    public Optional<Notes> getNoteById(Long id) {
        return notesRepository.findById(id);
    }

    // Obtener todas las notas
    public List<Notes> getAllNotes() {
        return notesRepository.findAll();
    }

    // Eliminar una nota por ID
    public boolean deleteNote(Long id) {
        Optional<Notes> notesOptional = notesRepository.findById(id);
        if (notesOptional.isPresent()) {
            notesRepository.deleteById(id);
            return true;
        }
        return false;
    }

    // Actualizar una nota
    public Optional<Notes> updateNote(Long id, Notes updatedNotes) {
        Optional<Notes> notesOptional = notesRepository.findById(id);
        if (notesOptional.isPresent()) {
            Notes existingNotes = notesOptional.get();
            existingNotes.setTitle(updatedNotes.getTitle());
            existingNotes.setContent(updatedNotes.getContent());
            return Optional.of(notesRepository.save(existingNotes));
        }
        return Optional.empty();
    }
}
