package com.example.demo.Models;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class SectionsTest {

    private Sections section;

    @BeforeEach
    public void init() {
        this.section = new Sections();
    }

    @Test
    public void testSetTitleSectionsNotEmpty() {
        // When & Then
        assertThrows(IllegalArgumentException.class, () -> {
            section.setTitleSections("");
        });

        assertThrows(IllegalArgumentException.class, () -> {
            section.setTitleSections(null);
        });
    }

    @Test
    public void testSetValidTitleSection() {
        // When
        section.setTitleSections("Valid Title Section");

        // Then
        assertEquals("Valid Title Section", section.getTitleSections());
    }
}
