package com.modul2.bookstore.entities;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

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
    private Integer maxBorrowDays;

    @ManyToOne
    @JoinColumn(name = "book_id")
    private Book book;

    @OneToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REMOVE},
            fetch = FetchType.LAZY,
            orphanRemoval = true,
            mappedBy = "exemplary")
    private List<Reservation> reservations = new ArrayList<>();

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

    public Integer getMaxBorrowDays() {
        return maxBorrowDays;
    }

    public void setMaxBorrowDays(Integer maxBorrowDays) {
        this.maxBorrowDays = maxBorrowDays;
    }

    public Book getBook() {
        return book;
    }

    public void setBook(Book book) {
        this.book = book;
    }

    public List<Reservation> getReservations() {
        return reservations;
    }

    public void setReservations(List<Reservation> reservations) {
        this.reservations = reservations;
    }

    public void addReservation(Reservation reservation) {
        reservations.add(reservation);
        reservation.setExemplary(this);
    }
}
