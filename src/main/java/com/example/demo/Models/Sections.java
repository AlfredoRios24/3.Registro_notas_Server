package com.example.demo.Models;


import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Sections {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idsection;

    private String titlesection;

    //Constructor
    public Sections() {}

    public Sections(Long idsection, String titlesection) {
        setTitleSections(titlesection);

    }

    // Método para validar que el título no esté vacío
    public void setTitleSections (String titlesection){
        if (titlesection == null || titlesection.trim().isEmpty()){
            throw new IllegalArgumentException("El título no puede estar vacio.");
        }
        this.titlesection= titlesection;
    }



    //Getters ands Setteres

    public Long getIdsection() {
        return idsection;
    }

    public void setIdsection(Long idsection) {
        this.idsection = idsection;
    }

    public String getTitleSections() {
        return titlesection;
    }


}
