package com.modul2.bookstore.controller;

import com.modul2.bookstore.dto.BookDTO;
import com.modul2.bookstore.dto.LibrarianDTO;
import com.modul2.bookstore.dto.LibraryDTO;
import com.modul2.bookstore.entities.Librarian;
import com.modul2.bookstore.entities.Library;
import com.modul2.bookstore.mapper.BookMapper;
import com.modul2.bookstore.mapper.LibrarianMapper;
import com.modul2.bookstore.mapper.LibraryMapper;
import com.modul2.bookstore.service.LibraryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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


    @GetMapping("/{libraryId}")
    public ResponseEntity<?> getById(@PathVariable(name = "libraryId") Long libraryIdToSearchFor) {
        Library foundLibrary = libraryService.getById(libraryIdToSearchFor);
        return ResponseEntity.ok(LibraryMapper.library2LibraryDto(foundLibrary));
    }

    @GetMapping()
    public ResponseEntity<?> findAll() {
        List<Library> library = libraryService.findAll();
        return ResponseEntity.ok(library.stream().map(LibraryMapper::library2LibraryDto).toList());
    }

    @DeleteMapping("/{libraryId}")
    public ResponseEntity<?> deleteById(@PathVariable(name = "libraryId") Long libraryIdToDelete) {
        libraryService.deleteById(libraryIdToDelete);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{libraryId}")
    public ResponseEntity<?> updateById(@PathVariable(name = "libraryId") Long libraryIdToUpdate, @RequestBody LibraryDTO libraryBody) {
        Library libraryEntity = LibraryMapper.libraryDto2Library(libraryBody);
        Library updatedLibrary = libraryService.updateById(libraryIdToUpdate, libraryEntity);
        return ResponseEntity.ok(LibraryMapper.library2LibraryDto(updatedLibrary));
    }

}
