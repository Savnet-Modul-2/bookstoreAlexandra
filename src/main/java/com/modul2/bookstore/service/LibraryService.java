package com.modul2.bookstore.service;

import com.modul2.bookstore.entities.Book;
import com.modul2.bookstore.entities.Library;
import com.modul2.bookstore.repository.LibraryRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
}
