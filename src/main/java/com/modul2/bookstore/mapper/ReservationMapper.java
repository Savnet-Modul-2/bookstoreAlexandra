package com.modul2.bookstore.mapper;

import com.modul2.bookstore.dto.ReservationDTO;
import com.modul2.bookstore.entities.Reservation;

public class ReservationMapper {
    public static Reservation reservationDTOTo2Reservation(ReservationDTO reservationDTO) {
        Reservation reservation = new Reservation();
        reservation.setStartDate(reservationDTO.getStartDate());
        reservation.setEndDate(reservationDTO.getEndDate());
        reservation.setReservationStatus(reservationDTO.getReservationStatus());
        reservation.setUser(UserMapper.userDTO2User(reservationDTO.getUser()));
        reservation.setExemplary(ExemplaryMapper.exemplaryDto2Exemplary(reservationDTO.getExemplary()));

        return reservation;
    }

    public static ReservationDTO reservationTo2ReservationDTO(Reservation reservation) {
        ReservationDTO reservationDTO = new ReservationDTO();
        reservationDTO.setId(reservation.getId());
        reservationDTO.setStartDate(reservation.getStartDate());
        reservationDTO.setEndDate(reservation.getEndDate());
        reservationDTO.setReservationStatus(reservation.getReservationStatus());
        reservationDTO.setUser(UserMapper.user2UserDTO(reservation.getUser()));
        reservationDTO.setExemplary(ExemplaryMapper.exemplary2ExemplaryDto(reservation.getExemplary()));

        return reservationDTO;
    }
}
