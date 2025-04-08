package com.modul2.bookstore.unitTest.service;

import com.modul2.bookstore.entities.Librarian;
import com.modul2.bookstore.repository.LibrarianRepository;
import com.modul2.bookstore.service.EmailService;
import com.modul2.bookstore.service.LibrarianService;
import jakarta.persistence.EntityExistsException;
import jakarta.persistence.EntityNotFoundException;
import org.apache.commons.codec.digest.DigestUtils;
import org.assertj.core.api.Assertions;
import org.assertj.core.api.AssertionsForClassTypes;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
public class LibrarianServiceTests {
    @Mock
    private LibrarianRepository librarianRepository;
    @Mock
    private EmailService emailService;
    @InjectMocks
    private LibrarianService librarianService;
    private Librarian testLibrarian;

    @BeforeEach
    void setUp() {
        testLibrarian = new Librarian();
        testLibrarian.setId(1L);
        testLibrarian.setVerificationCode("testVerificationCode");
        testLibrarian.setVerificationCodeTimeExpiration(LocalDateTime.now().plusMinutes(5));
        testLibrarian.setPassword(DigestUtils.md5Hex("testPassword"));
        testLibrarian.setEmail("testEmail@gmail.com");
    }

    @Test
    void givenLibrarian_createLibrarian_returnLibrarian() {
        Mockito.when(librarianRepository.existsByEmail(Mockito.anyString())).thenReturn(false);
        librarianService.create(testLibrarian);

        ArgumentCaptor<Librarian> librarianArgumentCaptor = ArgumentCaptor.forClass(Librarian.class);
        Mockito.verify(librarianRepository).save(librarianArgumentCaptor.capture());
        Librarian capturedLibrarian = librarianArgumentCaptor.getValue();

        AssertionsForClassTypes.assertThat(capturedLibrarian).isEqualTo(testLibrarian);
    }

    @Test
    void givenEmail_existsByEmail_throwException() {
        Mockito.when(librarianRepository.existsByEmail(Mockito.anyString())).thenReturn(true);

        Assertions.assertThatThrownBy(() -> librarianService.create(testLibrarian))
                .isInstanceOf(EntityNotFoundException.class)
                .hasMessageContaining("Librarian with the email address %s already exists".formatted(testLibrarian.getEmail()));
        Mockito.verify(librarianRepository, Mockito.never()).save(testLibrarian);
    }

    @Test
    void givenLibrarianId_findById_returnLibrarian() {
        Mockito.when(librarianRepository.findById(Mockito.anyLong())).thenReturn(Optional.of(testLibrarian));

        Librarian foundLibrarian = librarianService.getById(testLibrarian.getId());
        AssertionsForClassTypes.assertThat(foundLibrarian).isEqualTo(testLibrarian);

        Mockito.verify(librarianRepository, Mockito.times(1)).findById(testLibrarian.getId());
    }

    @Test
    void givenWrongId_findById_throwException() {
        Mockito.when(librarianRepository.findById(Mockito.anyLong())).thenReturn(Optional.empty());

        Assertions.assertThatThrownBy(() -> librarianService.getById(testLibrarian.getId()))
                .isInstanceOf(EntityNotFoundException.class);
        Mockito.verify(librarianRepository, Mockito.times(1)).findById(testLibrarian.getId());
    }

    @Test
    void givenNothing_findAll_verifyCalledMethod() {
        librarianService.findAll();

        Mockito.verify(librarianRepository).findAll();
    }

    @Test
    void givenLibrarianId_deleteById_verifyCalledMethod() {
        librarianService.deleteById(testLibrarian.getId());

        Mockito.verify(librarianRepository).deleteById(testLibrarian.getId());
    }
}
