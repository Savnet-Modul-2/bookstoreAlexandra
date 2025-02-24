package com.modul2.bookstore.mapper;

import com.modul2.bookstore.dto.BookDTO;
import com.modul2.bookstore.entities.Book;

public class BookMapper {
    public static Book bookDto2Book(BookDTO bookDTO) {
        Book book = new Book();
        book.setTitle(bookDTO.getTitle());
        book.setAuthor(bookDTO.getAuthor());
        book.setAppearanceDate(bookDTO.getAppearanceDate());
        book.setNrOfPages(bookDTO.getNrOfPages());
        book.setBookCategory(bookDTO.getBookCategory());
        book.setLanguage(bookDTO.getLanguage());
        return book;
    }

    public static BookDTO book2BookDto(Book book) {
        BookDTO bookDTO = new BookDTO();
        bookDTO.setId(book.getId());
        bookDTO.setTitle(book.getTitle());
        bookDTO.setAuthor(book.getAuthor());
        bookDTO.setAppearanceDate(book.getAppearanceDate());
        bookDTO.setNrOfPages(book.getNrOfPages());
        bookDTO.setBookCategory(book.getBookCategory());
        bookDTO.setLanguage(book.getLanguage());
        return bookDTO;
    }
}
