package com.modul2.bookstore.controller;

import com.modul2.bookstore.dto.BookDTO;
import com.modul2.bookstore.entities.Library;
import com.modul2.bookstore.mapper.BookMapper;
import com.modul2.bookstore.mapper.LibraryMapper;
import com.modul2.bookstore.service.LibraryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/libraries")
public class LibraryController {
    @Autowired
    private LibraryService libraryService;

    @PostMapping("/add-book/{libraryId}")
    public ResponseEntity<?> addBookToLibrary(@PathVariable(name = "libraryId") Long libraryID, @RequestBody BookDTO bookDTO){
        Library library = libraryService.addBookToLibrary(libraryID, BookMapper.bookDto2Book(bookDTO));
        return ResponseEntity.ok(LibraryMapper.library2LibraryDto(library));
    }

    @DeleteMapping("/{libraryId}/remove-book/{bookId}")
    public ResponseEntity<?> removeBookFromLibrary(@PathVariable(name = "libraryId") Long libraryID, @PathVariable(name = "bookId") Long bookID){
        libraryService.removeBookToLibrary(libraryID, bookID);
        return ResponseEntity.noContent().build();
    }

}
