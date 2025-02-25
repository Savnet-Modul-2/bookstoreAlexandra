package com.modul2.bookstore.service;

import com.modul2.bookstore.entities.Book;
import com.modul2.bookstore.entities.Exemplary;
import com.modul2.bookstore.repository.BookRepository;
import com.modul2.bookstore.repository.ExemplaryRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ExemplaryService {
    @Autowired
    private ExemplaryRepository exemplaryRepository;
    @Autowired
    private BookRepository bookRepository;

    public List<Exemplary> create(Long bookId, Integer nrOfExeplaries, Exemplary exemplaryToCreate) {
        Book book = bookRepository.findById(bookId)
                .orElseThrow(EntityNotFoundException::new);

        if (exemplaryToCreate.getId() != null) {
            throw new RuntimeException("You cannot provide an ID to a new exemplary that you want to create");
        }

        List<Exemplary> createdExemplaries = new ArrayList<>();
        for (int i = 1; i <= nrOfExeplaries; i++) {
            Exemplary newExemplary = new Exemplary();
            newExemplary.setPublisher(exemplaryToCreate.getPublisher());
            newExemplary.setMaxBorrowDays(exemplaryToCreate.getMaxBorrowDays());
            newExemplary.setBook(book);

            exemplaryRepository.save(newExemplary);
            createdExemplaries.add(newExemplary);
            book.addExemplary(newExemplary);
        }

        bookRepository.save(book);

        return createdExemplaries;
    }

    public Exemplary getById(Long exemplaryId) {
        return exemplaryRepository.findById(exemplaryId)
                .orElseThrow(EntityNotFoundException::new);
    }

    public List<Exemplary> findAll() {
        return exemplaryRepository.findAll();
    }

    public Page<Exemplary> findAll(PageRequest pageRequest) {
        return exemplaryRepository.findAll(pageRequest);
    }

    public Exemplary updateById(Long exemplaryId, Exemplary exemplaryEntity) {
        Exemplary exemplary = exemplaryRepository.findById(exemplaryId)
                .orElseThrow(EntityNotFoundException::new);

        exemplary.setPublisher(exemplaryEntity.getPublisher());
        exemplary.setMaxBorrowDays(exemplaryEntity.getMaxBorrowDays());

        return exemplaryRepository.save(exemplary);
    }

    public void deleteById(Long exemplaryId) {
        exemplaryRepository.deleteById(exemplaryId);
    }
}
