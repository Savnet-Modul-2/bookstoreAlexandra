package com.modul2.bookstore.controller;

import com.modul2.bookstore.dto.BookDTO;
import com.modul2.bookstore.dto.ReviewDTO;
import com.modul2.bookstore.entities.Book;
import com.modul2.bookstore.entities.Review;
import com.modul2.bookstore.mapper.BookMapper;
import com.modul2.bookstore.mapper.ReviewMapper;
import com.modul2.bookstore.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/books")
public class BookController {
    @Autowired
    private BookService bookService;

    @PostMapping
    public ResponseEntity<?> create(@RequestBody BookDTO bookDto) {
        Book bookToCreate = BookMapper.bookDto2Book(bookDto);
        Book createdBook = bookService.create(bookToCreate);
        return ResponseEntity.ok(BookMapper.book2BookDto(createdBook));
    }

    @GetMapping("/{bookId}")
    public ResponseEntity<?> getById(@PathVariable(name = "bookId") Long bookIdToSearchFor) {
        Book foundBook = bookService.getById(bookIdToSearchFor);
        return ResponseEntity.ok(BookMapper.book2BookDto(foundBook));
    }

    @GetMapping
    public ResponseEntity<?> findAll(@RequestParam(name = "pageNumber", required = false) Integer pageNumber,
                                     @RequestParam(name = "pageSize", required = false) Integer pageSize) {
        if (pageSize != null && pageNumber != null) {
            Page<Book> bookPage = bookService.findAll(PageRequest.of(pageNumber, pageSize));
            return ResponseEntity.ok(bookPage.map(BookMapper::book2BookDto));
        }
        List<Book> books = bookService.findAll();
        return ResponseEntity.ok(books.stream()
                .map(BookMapper::book2BookDto)
                .toList());
    }

    @GetMapping("/search")
    public ResponseEntity<?> search(@RequestParam(name = "bookAuthor", required = false) String bookAuthor,
                                    @RequestParam(name = "bookTitle", required = false) String bookTitle,
                                    @RequestParam(name = "pageNumber", required = false) Integer pageNumber,
                                    @RequestParam(name = "pageSize", required = false) Integer pageSize) {
        if (pageSize != null && pageNumber != null) {
            Page<Book> bookPage = bookService.search(PageRequest.of(pageNumber, pageSize));
            return ResponseEntity.ok(bookPage.map(BookMapper::book2BookDto));
        }
        List<Book> books = bookService.search(bookAuthor, bookTitle);
        return ResponseEntity.ok(books.stream()
                .map(BookMapper::book2BookDto)
                .toList());
    }

    @PutMapping("/{bookId}")
    public ResponseEntity<?> updateById(@PathVariable(name = "bookId") Long bookIdToUpdate, @RequestBody BookDTO bookBody) {
        Book bookEntity = BookMapper.bookDto2Book(bookBody);
        Book updatedBook = bookService.updateById(bookIdToUpdate, bookEntity);
        return ResponseEntity.ok(BookMapper.book2BookDto(updatedBook));
    }

    @DeleteMapping("/{bookId}")
    public ResponseEntity<?> deleteById(@PathVariable(name = "bookId") Long bookIdToDelete) {
        bookService.deleteById(bookIdToDelete);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{userId}/add-review/{bookId}")
    public ResponseEntity<?> addReviewToBook(@PathVariable Long userId, @PathVariable Long bookId, @RequestBody ReviewDTO reviewDTO) {
        Review review = ReviewMapper.reviewDto2Review(reviewDTO);
        Book book = bookService.addReviewToBook(userId, bookId, review);
        return ResponseEntity.ok(BookMapper.book2BookDto(book));
    }

    @GetMapping("/fav/{userId}")
    public ResponseEntity<?> findAllFav(@PathVariable Long userId,
                                        @RequestParam(name = "pageNumber", required = false) Integer pageNumber,
                                        @RequestParam(name = "pageSize", required = false) Integer pageSize) {
        if (pageSize != null && pageNumber != null) {
            Page<Book> bookPage = bookService.findAllFav(userId, PageRequest.of(pageNumber, pageSize));
            return ResponseEntity.ok(bookPage.map(BookMapper::book2BookDto));
        }
        List<Book> books = bookService.findAll();
        return ResponseEntity.ok(books.stream()
                .map(BookMapper::book2BookDto)
                .toList());
    }
}