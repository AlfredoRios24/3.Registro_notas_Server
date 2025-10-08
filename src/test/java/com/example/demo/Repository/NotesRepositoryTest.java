package com.example.demo.Repository;

import com.example.demo.Models.NoteState;
import com.example.demo.Models.Notes;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
class NotesRepositoryTest {

    @Autowired
    private NotesRepository notesRepository;

    private Notes note;

    @BeforeEach
    void setUp() {
        note = new Notes();
        note.setTitle("Test Note");
        note.setContent("This is a test content");
        note.setCreateAt(LocalDateTime.now());
        note.setStartDate(LocalDateTime.now().plusDays(1));
        note.setEndDate(LocalDateTime.now().plusDays(2));
        note.setState(NoteState.PENDING);

        notesRepository.save(note);
    }

    @Test
    void testSaveNote() {
        Notes saved = notesRepository.save(note);
        assertThat(saved.getId()).isNotNull();
        assertThat(saved.getTitle()).isEqualTo("Test Note");
    }

    @Test
    void testFindById() {
        Optional<Notes> found = notesRepository.findById(note.getId());
        assertThat(found).isPresent();
        assertThat(found.get().getContent()).isEqualTo("This is a test content");
    }

    @Test
    void testFindAll() {
        List<Notes> notes = notesRepository.findAll();
        assertThat(notes).isNotEmpty();
    }

    @Test
    void testDeleteNote() {
        notesRepository.deleteById(note.getId());
        Optional<Notes> deleted = notesRepository.findById(note.getId());
        assertThat(deleted).isEmpty();
    }
}
