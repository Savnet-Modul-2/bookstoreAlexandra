package com.modul2.bookstore.unitTest.entity;

import com.modul2.bookstore.entities.Librarian;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class LibrarianTests {
    private Librarian testLibrarian;

    @BeforeEach
    void setUp() {
        testLibrarian = new Librarian();
    }

    @Test
    void givenId_getId_returnNotNull() {
        testLibrarian.setId(2L);

        Assertions.assertThat(testLibrarian.getId()).isNotNull();
    }

    @Test
    void givenNothing_getId_returnNull() {
        Assertions.assertThat(testLibrarian.getId()).isNull();
    }

    @Test
    void givenName_getFirstName_returnNotEmpty() {
        testLibrarian.setFirstName("testFirstName");

        Assertions.assertThat(testLibrarian.getFirstName()).isNotEmpty();
    }

    @Test
    void givenName_getLastName_returnNotEmpty() {
        testLibrarian.setLastName("testLastName");

        Assertions.assertThat(testLibrarian.getLastName()).isNotEmpty();
    }
}