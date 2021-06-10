package com.ajit.flightreserv.services;


import com.ajit.flightreserv.dto.ReservationRequest;
import com.ajit.flightreserv.entities.Flight;
import com.ajit.flightreserv.entities.Passenger;
import com.ajit.flightreserv.entities.Reservation;
import com.ajit.flightreserv.repos.FlightRepository;
import com.ajit.flightreserv.repos.PassengerRepository;
import com.ajit.flightreserv.repos.ReservationRepository;
import com.ajit.flightreserv.util.EmailUtil;
import com.ajit.flightreserv.util.PDFGenerator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class ReservationServiceImpl implements ReservationService{

	@Autowired
	PassengerRepository passengerRepository;
	@Autowired
	FlightRepository flightRepository;
	@Autowired
	ReservationRepository reservationRepository;

	@Autowired
	PDFGenerator pdfGenerator;

	@Autowired
	EmailUtil emailUtil;

	private static final Logger LOGGER = LoggerFactory.getLogger(ReservationServiceImpl.class);

	@Value("${com.ajit.flightreservationapplication.itinerary.dirpath}")
	private String Itinerary_DIR;
	@Override
	public Reservation bookFlight(ReservationRequest request) {


		Long flightId = request.getFlightId();
		LOGGER.info("Fetching Flight for flight ID:"+flightId);
		Flight flight = flightRepository.findById(flightId).get();
		Passenger passenger= new Passenger();
		passenger.setFirstName(request.getPassengerFirstName());
		passenger.setLastName(request.getPassengerLastName());
		passenger.setPhone(request.getPassengerPhone());
		passenger.setEmail(request.getPassengerEmail());
		LOGGER.info("Saving the Passenger"+passenger);
		Passenger savedPassenger = passengerRepository.save(passenger);


		Reservation reservation = new Reservation();
		reservation.setFlight(flight);
		reservation.setPassenger(savedPassenger);
		reservation.setCheckedIn(false);
		LOGGER.info("Saving the Reservation"+reservation);
		Reservation savedReservation = reservationRepository.save(reservation);
		String filePath=Itinerary_DIR + savedReservation.getId()+".pdf";
		LOGGER.info("Generating the Itinerary");
		pdfGenerator.generateItenerary(savedReservation,filePath);
		LOGGER.info("Sending out email");
		emailUtil.sendItinerary(passenger.getEmail(),filePath);
		return savedReservation;
	}
}