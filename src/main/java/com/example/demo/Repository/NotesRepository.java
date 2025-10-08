package com.example.demo.Repository;

import com.example.demo.Models.Notes;
import com.example.demo.Models.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface NotesRepository extends JpaRepository<Notes, Long> {
    List<Notes> findAllByUser(Users user);
    Optional<Notes> findByIdAndUser(Long id, Users user);
}
