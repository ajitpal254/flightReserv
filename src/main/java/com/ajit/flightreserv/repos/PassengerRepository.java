package com.ajit.flightreserv.repos;

import com.ajit.flightreserv.entities.Passenger;
import org.springframework.data.jpa.repository.JpaRepository;



public interface PassengerRepository extends JpaRepository<Passenger, Long> {

}
