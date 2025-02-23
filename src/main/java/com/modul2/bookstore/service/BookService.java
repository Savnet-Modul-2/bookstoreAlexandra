package com.modul2.bookstore.service;

import com.modul2.bookstore.entities.Book;
import com.modul2.bookstore.repository.BookRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookService {
    @Autowired
    private BookRepository bookRepository;

    public Book create(Book book) {
        if(book.getId() != null){
            throw new RuntimeException("You cannot provide an ID to a new application that you want to create");
        }
        return bookRepository.save(book);
    }

    public Book getById(Long bookIdToSearchFor) {
        return bookRepository.findById(bookIdToSearchFor)
                .orElseThrow(EntityNotFoundException::new);
    }

    public List<Book> findAll() {
        return bookRepository.findAll();
    }

    public Page<Book> findAll(PageRequest of) {
        return bookRepository.findAll(of);
    }

    public void deleteById(Long bookIdToDelete) {
        bookRepository.deleteById(bookIdToDelete);
    }

    public Book updateById(Long bookIdToUpdate, Book bookEntity) {
        Book updatedBook = bookRepository.findById(bookIdToUpdate)
                .orElseThrow(EntityNotFoundException::new);

        updatedBook.setTitle(bookEntity.getTitle());
        updatedBook.setAuthor(bookEntity.getAuthor());
        updatedBook.setAppearanceDate(bookEntity.getAppearanceDate());
        updatedBook.setNrOfPages(bookEntity.getNrOfPages());
        updatedBook.setCategory(bookEntity.getCategory());
        updatedBook.setLanguage(bookEntity.getLanguage());

        return bookRepository.save(updatedBook);
    }
}
