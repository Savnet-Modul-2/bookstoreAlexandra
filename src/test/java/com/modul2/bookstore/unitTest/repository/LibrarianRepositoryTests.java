package com.modul2.bookstore.unitTest.repository;

import com.modul2.bookstore.entities.Librarian;
import com.modul2.bookstore.repository.LibrarianRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@DataJpaTest
@ExtendWith(SpringExtension.class)
public class LibrarianRepositoryTests {
    @Autowired
    private LibrarianRepository librarianRepository;
    private Librarian testLibrarian;

    @BeforeEach
    void setUp() {
        testLibrarian = new Librarian();
        testLibrarian.setEmail("test@gmail.com");
    }

    @AfterEach
    void tearDown() {
        librarianRepository.deleteAll();
    }

    @Test
    void givenEmail_existsByEmail_returnTrue() {
        String testEmail = "test@gmail.com";

        librarianRepository.save(testLibrarian);
        boolean expected = librarianRepository.existsByEmail(testEmail);

        Assertions.assertThat(expected).isTrue();
    }

    @Test
    void givenNothing_existsByEmail_returnFalse() {
        String testEmail = "test@gmail.com";

        boolean expected = librarianRepository.existsByEmail(testEmail);

        Assertions.assertThat(expected).isFalse();
    }

    @Test
    void givenEmail_findByEmail_returnLibrarian() {
        String testEmail = "test@gmail.com";

        librarianRepository.save(testLibrarian);
        Librarian foundLibrarian = librarianRepository.findByEmail(testEmail).orElse(null);

        Assertions.assertThat(foundLibrarian).isEqualTo(testLibrarian);
    }

    @Test
    void givenNothing_findByEmail_returnNull() {
        String testEmail = "test@gmail.com";

        Librarian foundLibrarian = librarianRepository.findByEmail(testEmail).orElse(null);

        Assertions.assertThat(foundLibrarian).isNull();
    }
}