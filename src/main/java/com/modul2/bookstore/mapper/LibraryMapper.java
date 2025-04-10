package com.modul2.bookstore.mapper;

import com.modul2.bookstore.dto.LibraryDTO;
import com.modul2.bookstore.entities.Library;

public class LibraryMapper {
    public static Library libraryDto2Library(LibraryDTO libraryDTO) {
        Library library = new Library();
        library.setName(libraryDTO.getName());
        library.setAddress(libraryDTO.getAddress());
        library.setPhoneNumber(libraryDTO.getPhoneNumber());
        return library;
    }

    public static LibraryDTO library2LibraryDto(Library library) {
        LibraryDTO libraryDTO = new LibraryDTO();
        libraryDTO.setId(library.getId());
        libraryDTO.setName(library.getName());
        libraryDTO.setAddress(library.getAddress());
        libraryDTO.setPhoneNumber(library.getPhoneNumber());
        return libraryDTO;
    }
}