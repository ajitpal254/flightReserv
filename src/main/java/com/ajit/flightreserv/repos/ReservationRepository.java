package com.ajit.flightreserv.repos;

import com.ajit.flightreserv.entities.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;



public interface ReservationRepository extends JpaRepository<Reservation, Long> {

}
