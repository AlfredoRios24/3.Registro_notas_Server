package com.example.demo.Service;

import com.example.demo.Models.NoteState;
import com.example.demo.Models.Notes;
import com.example.demo.Models.Users;
import com.example.demo.Repository.NotesRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class NotesService {

    private final NotesRepository notesRepository;

    public NotesService(NotesRepository notesRepository) {
        this.notesRepository = notesRepository;
    }

    // Guardar una nueva nota asociada a un usuario
    public Notes saveNoteForUser(Notes notes, Users user) {
        if (notes.getCreateAt() == null) {
            notes.setCreateAt(LocalDateTime.now());
        }
        if (notes.getState() == null) {
            notes.setState(NoteState.PENDING);
        }
        notes.setUser(user);
        return notesRepository.save(notes);
    }

    // Obtener todas las notas de un usuario
    public List<Notes> getAllNotesForUser(Users user) {
        return notesRepository.findAllByUser(user);
    }

    // Obtener nota por ID y usuario
    public Optional<Notes> getNoteByIdForUser(Long id, Users user) {
        return notesRepository.findByIdAndUser(id, user);
    }

    // Actualizar nota de un usuario
    public Optional<Notes> updateNoteForUser(Long id, Notes updatedNotes, Users user) {
        Optional<Notes> notesOptional = notesRepository.findByIdAndUser(id, user);
        if (notesOptional.isPresent()) {
            Notes existingNotes = notesOptional.get();
            existingNotes.setTitle(updatedNotes.getTitle());
            existingNotes.setContent(updatedNotes.getContent());

            if (updatedNotes.getStartDate() != null) {
                existingNotes.setStartDate(updatedNotes.getStartDate());
            }
            if (updatedNotes.getEndDate() != null) {
                if (existingNotes.getStartDate() != null &&
                        updatedNotes.getEndDate().isBefore(existingNotes.getStartDate())) {
                    throw new IllegalArgumentException("La fecha de fin no puede ser anterior a la fecha de inicio.");
                }
                existingNotes.setEndDate(updatedNotes.getEndDate());
            }

            existingNotes.setState(updatedNotes.getState());

            return Optional.of(notesRepository.save(existingNotes));
        }
        return Optional.empty();
    }

    // Eliminar nota de un usuario
    public boolean deleteNoteForUser(Long id, Users user) {
        Optional<Notes> notesOptional = notesRepository.findByIdAndUser(id, user);
        if (notesOptional.isPresent()) {
            notesRepository.delete(notesOptional.get());
            return true;
        }
        return false;
    }
}
