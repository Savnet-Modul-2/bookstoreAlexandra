package com.modul2.bookstore.service;

import com.modul2.bookstore.entities.Book;
import com.modul2.bookstore.entities.Librarian;
import com.modul2.bookstore.entities.Library;
import com.modul2.bookstore.repository.LibraryRepository;
import jakarta.persistence.EntityExistsException;
import jakarta.persistence.EntityNotFoundException;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Random;

@Service
public class LibraryService {
    @Autowired
    private LibraryRepository libraryRepository;

    public Library addBookToLibrary(Long libraryID, Book book) {
        Library library = libraryRepository.findById(libraryID)
                .orElseThrow(EntityNotFoundException::new);

        library.addBook(book);
        return libraryRepository.save(library);
    }

    public void removeBookToLibrary(Long libraryID, Long bookID) {
        Library library = libraryRepository.findById(libraryID)
                .orElseThrow(EntityNotFoundException::new);

        Book bookToRemove = library.getBooks().stream()
                .filter(book -> book.getId().equals(bookID))
                .findFirst()
                .orElseThrow(EntityNotFoundException::new);

        library.getBooks().remove(bookToRemove);
        libraryRepository.save(library);
    }


    public Library getById(Long libraryId){
        return libraryRepository.findById(libraryId)
                .orElseThrow(EntityNotFoundException::new);
    }

    public List<Library> findAll() {
        return libraryRepository.findAll();
    }

    public void deleteById(Long libraryId) {
        libraryRepository.deleteById(libraryId);
    }

    public Library updateById(Long libraryId, Library libraryBody) {
        Library updatedLibrary = libraryRepository.findById(libraryId)
                .orElseThrow(EntityNotFoundException::new);

        updatedLibrary.setName(libraryBody.getName());
        updatedLibrary.setAddress(libraryBody.getAddress());
        updatedLibrary.setPhoneNumber(libraryBody.getPhoneNumber());

        return libraryRepository.save(updatedLibrary);
    }
}
