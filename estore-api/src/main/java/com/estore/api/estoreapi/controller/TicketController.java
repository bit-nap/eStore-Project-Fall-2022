package com.estore.api.estoreapi.controller;

import com.estore.api.estoreapi.model.Ticket;
import com.estore.api.estoreapi.persistence.TicketDAO;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Handles the REST API requests for a Ticket object.
 * <p>
 * {@literal @}RestController Spring annotation identifies this class as a REST API
 * method handler to the Spring framework
 */

@RestController
@RequestMapping("tickets")
public class TicketController {
	/** TODO: Add description of the purpose of Logger, once it's actually used. */
	private static final Logger LOG = Logger.getLogger(TicketController.class.getName());
	/** The TicketDAO object this Controller interacts with to get Ticket objects. */
	private TicketDAO ticketDao;

	/**
	 * Creates a REST API controller to respond to Ticket requests.
	 *
	 * @param ticketDAO The {@link TicketDAO Ticket Data Access Object} to perform CRUD operations
	 *                  <br>
	 *                  This dependency is injected by the Spring Framework
	 */
	public TicketController (TicketDAO ticketDAO) {
		this.ticketDao = ticketDAO;
	}

	/**
	 * Responds to the GET request for a {@linkplain Ticket ticket} for the given id.
	 *
	 * @param id The id used to locate the {@link Ticket ticket}
	 * @return ResponseEntity with {@link Ticket ticket} object and HTTP status of OK if found<br>
	 * ResponseEntity with HTTP status of NOT_FOUND if not found<br>
	 * ResponseEntity with HTTP status of INTERNAL_SERVER_ERROR otherwise
	 */
	@GetMapping("/{id}")
	public ResponseEntity<Ticket> getTicket (@PathVariable int id) {
		LOG.info("GET /tickets/" + id);
		try {
			Ticket ticket = ticketDao.getTicket(id);
			if (ticket != null)
				return new ResponseEntity<Ticket>(ticket, HttpStatus.OK);
			else
				return new ResponseEntity<Ticket>(HttpStatus.NOT_FOUND);
		}
		catch (IOException e) {
			LOG.log(Level.SEVERE, e.getLocalizedMessage());
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	/**
	 * Responds to the GET request for all {@linkplain Ticket ticket}.
	 *
	 * @return ResponseEntity with array of {@link Ticket ticket} objects (may be empty) and
	 * HTTP status of OK<br>
	 * ResponseEntity with HTTP status of INTERNAL_SERVER_ERROR otherwise
	 */
	@GetMapping("")
	public ResponseEntity<Ticket[]> getTickets () {
		// TODO
		return null;
	}

	/**
	 * Responds to the GET request for all {@linkplain Ticket tickets} whose movie title
	 * contains the given text.
	 *
	 * @param text A String which contains the text used to find the {@link Ticket ticket} to a movie
	 * @return ResponseEntity with array of {@link Ticket ticket} objects (may be empty) and
	 * HTTP status of OK<br>
	 * ResponseEntity with HTTP status of INTERNAL_SERVER_ERROR otherwise
	 * <p>
	 * Example: Find all tickets that contain the text "ma"
	 * GET http://localhost:8080/tickets/?name=ma
	 */
	@GetMapping("/")
	public ResponseEntity<Ticket[]> searchTickets (@RequestParam String text) {
		// TODO
		return null;
	}

	/**
	 * Creates a {@linkplain Ticket ticket} with the provided ticket object.
	 *
	 * @param ticket The {@link Ticket ticket} to create
	 * @return ResponseEntity with created {@link Ticket ticket} object and HTTP status of CREATED<br>
	 * ResponseEntity with HTTP status of CONFLICT if {@link Ticket ticket} object already exists<br>
	 * ResponseEntity with HTTP status of INTERNAL_SERVER_ERROR otherwise
	 */
	@PostMapping("")
	public ResponseEntity<Ticket> createTicket (@RequestBody Ticket ticket) {
		// TODO
		return null;
	}

	/**
	 * Updates the {@linkplain Ticket ticket} with the provided {@linkplain Ticket ticket} object, if it exists.
	 *
	 * @param ticket The {@link Ticket ticket} to update
	 * @return ResponseEntity with updated {@link Ticket ticket} object and HTTP status of OK if updated<br>
	 * ResponseEntity with HTTP status of NOT_FOUND if not found<br>
	 * ResponseEntity with HTTP status of INTERNAL_SERVER_ERROR otherwise
	 */
	@PutMapping("")
	public ResponseEntity<Ticket> updateTicket (@RequestBody Ticket ticket) {
		// TODO
		return null;
	}

	/**
	 * Deletes a {@linkplain Ticket ticket} with the given id.
	 *
	 * @param id The id of the {@link Ticket ticket} to deleted
	 * @return ResponseEntity HTTP status of OK if deleted<br>
	 * ResponseEntity with HTTP status of NOT_FOUND if not found<br>
	 * ResponseEntity with HTTP status of INTERNAL_SERVER_ERROR otherwise
	 */
	@DeleteMapping("/{id}")
	public ResponseEntity<Ticket> deleteTicket (@PathVariable int id) {
		// TODO
		return null;
	}
}
