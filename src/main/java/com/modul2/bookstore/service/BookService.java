package com.modul2.bookstore.service;

import com.modul2.bookstore.entities.Book;
import com.modul2.bookstore.entities.Review;
import com.modul2.bookstore.entities.User;
import com.modul2.bookstore.repository.BookRepository;
import com.modul2.bookstore.repository.ReviewRepository;
import com.modul2.bookstore.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookService {
    @Autowired
    private BookRepository bookRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ReviewRepository reviewRepository;

    public Book create(Book book) {
        if (book.getId() != null) {
            throw new RuntimeException("You cannot provide an ID to a new book that you want to create");
        }
        return bookRepository.save(book);
    }

    public Book getById(Long bookId) {
        return bookRepository.findById(bookId)
                .orElseThrow(EntityNotFoundException::new);
    }

    public List<Book> findAll() {
        return bookRepository.findAll();
    }

    public Page<Book> findAll(PageRequest pageRequest) {
        return bookRepository.findAll(pageRequest);
    }

    public Page<Book> findAllFav(Long userId, PageRequest pageRequest) {
        User user = userRepository.findById(userId)
                .orElseThrow(EntityNotFoundException::new);

        return bookRepository.findAll(pageRequest);
    }

    public Page<Book> search(PageRequest pageRequest) {
        return bookRepository.findAll(pageRequest);
    }

    public List<Book> search(String bookAuthor, String bookTitle) {
        return bookRepository.findBooks(bookAuthor, bookTitle);
    }

    public Book updateById(Long bookId, Book bookEntity) {
        Book updatedBook = bookRepository.findById(bookId)
                .orElseThrow(EntityNotFoundException::new);

        updatedBook.setTitle(bookEntity.getTitle());
        updatedBook.setAuthor(bookEntity.getAuthor());
        updatedBook.setAppearanceDate(bookEntity.getAppearanceDate());
        updatedBook.setNrOfPages(bookEntity.getNrOfPages());
        updatedBook.setBookCategory(bookEntity.getBookCategory());
        updatedBook.setLanguage(bookEntity.getLanguage());

        return bookRepository.save(updatedBook);
    }

    public void deleteById(Long bookId) {
        bookRepository.deleteById(bookId);
    }

    public Book addReviewToBook(Long userId, Long bookId, Review review) {
        User user = userRepository.findById(userId)
                .orElseThrow(EntityNotFoundException::new);

        Book book = bookRepository.findById(bookId)
                .orElseThrow(EntityNotFoundException::new);

        user.addReview(review);
        book.addReview(review);
        book.calculMedie();
        reviewRepository.save(review);

        return bookRepository.save(book);
    }
}