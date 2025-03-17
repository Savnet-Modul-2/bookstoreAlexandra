package com.modul2.bookstore.dto;

public class CreateExemplaryDTO {
    private Long bookId;
    private Integer nrOfExemplaries;
    private String publisher;
    private Integer maxBorrowDays;
    private Integer version;

    public Long getBookId() {
        return bookId;
    }

    public void setBookId(Long bookId) {
        this.bookId = bookId;
    }

    public Integer getNrOfExemplaries() {
        return nrOfExemplaries;
    }

    public void setNrOfExemplaries(Integer nrOfExemplaries) {
        this.nrOfExemplaries = nrOfExemplaries;
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

    public Integer getVersion() {
        return version;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }
}