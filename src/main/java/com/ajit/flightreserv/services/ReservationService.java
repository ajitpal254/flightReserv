package com.ajit.flightreserv.services;

import com.ajit.flightreserv.dto.ReservationRequest;
import com.ajit.flightreserv.entities.Reservation;


public interface ReservationService {

    public Reservation bookFlight(ReservationRequest request);

}
