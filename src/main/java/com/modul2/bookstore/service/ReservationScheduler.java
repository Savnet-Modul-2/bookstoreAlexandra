package com.modul2.bookstore.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

@Configuration
@EnableScheduling
public class ReservationScheduler {
    @Autowired
    private ReservationService reservationService;

    @Scheduled(cron = "0 0 22 * * *")
    public void scheduleReservationUpdates() {
        reservationService.updateExpiredPendingReservations();
        reservationService.updateExpiredInProgressReservations();
    }
}