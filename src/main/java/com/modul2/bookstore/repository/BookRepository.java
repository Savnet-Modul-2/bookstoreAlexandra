package com.modul2.bookstore.repository;

import com.modul2.bookstore.entities.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BookRepository extends JpaRepository<Book, Long> {
    @Query(value = """
            SELECT book FROM book book
            WHERE (:author IS NULL OR LOWER(book.author) LIKE LOWER(CONCAT('%', :author, '%')))
            AND (:title IS NULL OR LOWER(book.title) LIKE LOWER(CONCAT('%', :title, '%')))
            """)
    List<Book> findBooks(String author, String title);
}
