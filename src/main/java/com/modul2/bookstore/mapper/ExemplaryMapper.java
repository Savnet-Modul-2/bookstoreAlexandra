package com.modul2.bookstore.mapper;

import com.modul2.bookstore.dto.CreateExemplaryDTO;
import com.modul2.bookstore.dto.ExemplaryDTO;
import com.modul2.bookstore.entities.Exemplary;

public class ExemplaryMapper {
    public static Exemplary exemplaryDto2Exemplary(ExemplaryDTO exemplaryDTO) {
        Exemplary exemplary = new Exemplary();
        exemplary.setPublisher(exemplaryDTO.getPublisher());
        exemplary.setMaxBorrowDays(exemplaryDTO.getMaxBorrowDays());
        exemplary.setVersion(exemplaryDTO.getVersion());
        return exemplary;
    }

    public static ExemplaryDTO exemplary2ExemplaryDto(Exemplary exemplary) {
        ExemplaryDTO exemplaryDTO = new ExemplaryDTO();
        exemplaryDTO.setId(exemplary.getId());
        exemplaryDTO.setPublisher(exemplary.getPublisher());
        exemplaryDTO.setMaxBorrowDays(exemplary.getMaxBorrowDays());
        exemplaryDTO.setVersion(exemplary.getVersion());
        return exemplaryDTO;
    }

    public static Exemplary createExemplaryDtoToExemplary(CreateExemplaryDTO dto) {
        Exemplary exemplary = new Exemplary();
        exemplary.setPublisher(dto.getPublisher());
        exemplary.setMaxBorrowDays(dto.getMaxBorrowDays());
        exemplary.setVersion(exemplary.getVersion());
        return exemplary;
    }
}