package com.modul2.bookstore.entities;

import jakarta.persistence.*;

@Entity(name = "exemplary")
@Table(name = "EXEMPLARY", schema = "public")
public class Exemplary {
    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "PUBLISHER")
    private String publisher;

    @Column(name = "MAX_BORROW_DAYS")
    private String maxBorrowDays;

    @ManyToOne
    @JoinColumn(name = "book_id")
    private Book book;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPublisher() {
        return publisher;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }

    public String getMaxBorrowDays() {
        return maxBorrowDays;
    }

    public void setMaxBorrowDays(String maxBorrowDays) {
        this.maxBorrowDays = maxBorrowDays;
    }

    public Book getBook() {
        return book;
    }

    public void setBook(Book book) {
        this.book = book;
    }
}
