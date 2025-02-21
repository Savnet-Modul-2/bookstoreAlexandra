package com.modul2.bookstore.repository;

import com.modul2.bookstore.entities.Book;
import com.modul2.bookstore.entities.Library;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BookRepository extends JpaRepository<Book, Long> {
}
