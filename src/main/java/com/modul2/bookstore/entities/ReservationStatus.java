package com.modul2.bookstore.entities;

public enum ReservationStatus {
    PENDING {
        @Override
        public boolean isNextStatePossible(ReservationStatus status) {
            return status.equals(ReservationStatus.IN_PROGRESS) || status.equals(ReservationStatus.CANCELED);
        }
    }, IN_PROGRESS {
        @Override
        public boolean isNextStatePossible(ReservationStatus status) {
            return status.equals(ReservationStatus.DELAYED) || status.equals(ReservationStatus.FINISHED);
        }
    }, DELAYED {
        @Override
        public boolean isNextStatePossible(ReservationStatus status) {
            return status.equals(ReservationStatus.FINISHED);
        }
    }, FINISHED {
        @Override
        public boolean isNextStatePossible(ReservationStatus status) {
            return false;
        }
    }, CANCELED {
        @Override
        public boolean isNextStatePossible(ReservationStatus status) {
            return false;
        }
    };

    public abstract boolean isNextStatePossible(ReservationStatus status);
}