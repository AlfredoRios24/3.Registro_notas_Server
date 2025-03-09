package com.example.demo.Controller;


import com.example.demo.Models.Sections;
import com.example.demo.Repository.SectionsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/sections")  // La ruta raíz es /api/sections
@CrossOrigin(origins = "http://localhost:5173")
public class SectionsController {

    @Autowired
    private SectionsRepository sectionsRepository;

    // Método para guardar una sección
    @PostMapping("")
    public ResponseEntity<Sections> registerSections(@RequestBody Sections sections){
        Sections savedSections = sectionsRepository.save(sections);
        return ResponseEntity.ok(savedSections);
    }

    // Método para obtener una sección por ID
    @GetMapping("/{id}")
    public ResponseEntity<Sections> getSectionById(@PathVariable Long id) {
        Optional<Sections> sectionsOptional = sectionsRepository.findById(id);
        return sectionsOptional.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.status(404).body(null));
    }

    // Método para obtener todas las secciones
    @GetMapping("")
    public ResponseEntity<List<Sections>> getSections() {
        try {
            List<Sections> sections = sectionsRepository.findAll();
            return ResponseEntity.ok(sections);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body(null);
        }
    }

    // Método para eliminar una sección por ID
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteSections(@PathVariable Long id) {
        Optional<Sections> sectionsOptional = sectionsRepository.findById(id);
        if (sectionsOptional.isPresent()) {
            sectionsRepository.deleteById(id);
            return ResponseEntity.ok("Sección eliminada exitosamente");
        } else {
            return ResponseEntity.status(404).body("Sección no encontrada");
        }
    }

    // Método para actualizar una sección
    @PutMapping("/{id}")
    public ResponseEntity<Sections> updateSections(@PathVariable Long id, @RequestBody Sections updatedSections) {
        Optional<Sections> sectionsOptional = sectionsRepository.findById(id);
        if (sectionsOptional.isPresent()) {
            Sections sections = sectionsOptional.get();
            sections.setTitleSections(updatedSections.getTitleSections());
            Sections savedSections = sectionsRepository.save(sections);
            return ResponseEntity.ok(savedSections);
        } else {
            return ResponseEntity.status(404).body(null);
        }
    }
}
