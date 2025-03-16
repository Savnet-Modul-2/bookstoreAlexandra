package com.modul2.bookstore.controller;

import com.modul2.bookstore.dto.CreateExemplaryDTO;
import com.modul2.bookstore.dto.ExemplaryDTO;
import com.modul2.bookstore.entities.Exemplary;
import com.modul2.bookstore.mapper.ExemplaryMapper;
import com.modul2.bookstore.service.ExemplaryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/exemplaries")
public class ExemplaryController {
    @Autowired
    private ExemplaryService exemplaryService;

    @PostMapping
    public ResponseEntity<?> create(@RequestBody CreateExemplaryDTO exemplaryDTO) {
        Exemplary exemplaryToCreate = ExemplaryMapper.createExemplaryDtoToExemplary(exemplaryDTO);
        List<Exemplary> createdExemplaries = exemplaryService.create(exemplaryDTO.getBookId(), exemplaryDTO.getNrOfExemplaries(), exemplaryToCreate);
        return ResponseEntity.ok(createdExemplaries.stream()
                .map(ExemplaryMapper::exemplary2ExemplaryDto)
                .toList());
    }

    @GetMapping("/{exemplaryId}")
    public ResponseEntity<?> getById(@PathVariable(name = "exemplaryId") Long exemplaryId) {
        Exemplary foundExemplary = exemplaryService.getById(exemplaryId);
        return ResponseEntity.ok(ExemplaryMapper.exemplary2ExemplaryDto(foundExemplary));
    }

    @GetMapping
    public ResponseEntity<?> findAll(@RequestParam(name = "pageNumber", required = false) Integer pageNumber,
                                     @RequestParam(name = "pageSize", required = false) Integer pageSize) {
        if (pageSize != null && pageNumber != null) {
            Page<Exemplary> exemplaryPage = exemplaryService.findAll(PageRequest.of(pageNumber, pageSize));
            return ResponseEntity.ok(exemplaryPage.map(ExemplaryMapper::exemplary2ExemplaryDto));
        }
        List<Exemplary> exemplaries = exemplaryService.findAll();
        return ResponseEntity.ok(exemplaries.stream()
                .map(ExemplaryMapper::exemplary2ExemplaryDto)
                .toList());
    }

    @PutMapping("/{exemplaryId}")
    public ResponseEntity<?> updateById(@PathVariable(name = "exemplaryId") Long exemplaryId, @RequestBody ExemplaryDTO exemplaryBody) {
        Exemplary exemplaryEntity = ExemplaryMapper.exemplaryDto2Exemplary(exemplaryBody);
        Exemplary updatedExemplary = exemplaryService.updateById(exemplaryId, exemplaryEntity);
        return ResponseEntity.ok(ExemplaryMapper.exemplary2ExemplaryDto(updatedExemplary));
    }

    @DeleteMapping("/{exemplaryId}")
    public ResponseEntity<?> deleteById(@PathVariable(name = "exemplaryId") Long exemplaryId) {
        exemplaryService.deleteById(exemplaryId);
        return ResponseEntity.noContent().build();
    }
}