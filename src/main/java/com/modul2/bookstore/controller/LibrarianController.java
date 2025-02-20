package com.modul2.bookstore.controller;

import com.modul2.bookstore.dto.LibrarianDTO;
import com.modul2.bookstore.entities.Librarian;
import com.modul2.bookstore.exceptions.AccountNotVerifiedException;
import com.modul2.bookstore.exceptions.InvalidPasswordException;
import com.modul2.bookstore.mapper.LibrarianMapper;
import com.modul2.bookstore.service.LibrarianService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/librarians")
public class LibrarianController {
    @Autowired
    private LibrarianService librarianService;

    @PostMapping()
    public ResponseEntity<?> create (@RequestBody LibrarianDTO librarianDTO){
        Librarian librarianToCreate = LibrarianMapper.librarianDto2Librarian(librarianDTO);
        Librarian createdLibrarian = librarianService.create(librarianToCreate);
        return ResponseEntity.ok(LibrarianMapper.librarian2LibrarianDto(createdLibrarian));
    }

    @PutMapping("/verify")
    public ResponseEntity<?> verifyCode(@RequestParam("librarianId") Long librarianId, @RequestParam("code") String code) {
        Librarian verifiedLibrarian = librarianService.verifyCode(librarianId, code);
        return ResponseEntity.ok(LibrarianMapper.librarian2LibrarianDto(verifiedLibrarian));
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LibrarianDTO librarianDTO) throws InvalidPasswordException, AccountNotVerifiedException {
        Librarian librarianToLogin = LibrarianMapper.librarianDto2Librarian(librarianDTO);
        Librarian loggedLibrarian = librarianService.login(librarianToLogin.getEmail(), librarianToLogin.getPassword());
        return ResponseEntity.ok(loggedLibrarian.getId());
    }

    @GetMapping("/{librarianId}")
    public ResponseEntity<?> getById(@PathVariable(name = "librarianId") Long librarianIdToSearchFor) {
        Librarian foundLibrarian = librarianService.getById(librarianIdToSearchFor);
        return ResponseEntity.ok(LibrarianMapper.librarian2LibrarianDto(foundLibrarian));
    }

    @GetMapping()
    public ResponseEntity<?> findAll() {
        List<Librarian> librarians = librarianService.findAll();
        return ResponseEntity.ok(librarians.stream().map(LibrarianMapper::librarian2LibrarianDto).toList());
    }

    @DeleteMapping("/{librarianId}")
    public ResponseEntity<?> deleteById(@PathVariable(name = "librarianId") Long librarianIdToDelete) {
        librarianService.deleteById(librarianIdToDelete);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{librarianId}")
    public ResponseEntity<?> updateById(@PathVariable(name = "librarianId") Long librarianIdToUpdate, @RequestBody LibrarianDTO librarianBody) {
        Librarian librarianEntity = LibrarianMapper.librarianDto2Librarian(librarianBody);
        Librarian updatedLibrarian = librarianService.updateById(librarianIdToUpdate, librarianEntity);
        return ResponseEntity.ok(LibrarianMapper.librarian2LibrarianDto(updatedLibrarian));
    }
}
