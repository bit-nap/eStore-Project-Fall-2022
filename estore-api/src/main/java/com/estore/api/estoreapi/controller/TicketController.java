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
 * Handles the REST API requests for the Hero resource
 * <p>
 * {@literal @}RestController Spring annotation identifies this class as a REST API
 * method handler to the Spring framework
 *
 * @author SWEN Faculty
 */

@RestController
@RequestMapping("tickets")
public class TicketController {
	private static final Logger LOG = Logger.getLogger(TicketController.class.getName());
	private TicketDAO ticketDao;

	/**
	 * Creates a REST API controller to reponds to requests
	 *
	 * @param heroDao The {@link TicketDAO Hero Data Access Object} to perform CRUD operations
	 *                <br>
	 *                This dependency is injected by the Spring Framework
	 */
	public TicketController (TicketDAO heroDao) {
		this.ticketDao = heroDao;
	}

	/**
	 * Responds to the GET request for a {@linkplain Movie hero} for the given id
	 *
	 * @param id The id used to locate the {@link Movie hero}
	 * @return ResponseEntity with {@link Movie hero} object and HTTP status of OK if found<br>
	 * ResponseEntity with HTTP status of NOT_FOUND if not found<br>
	 * ResponseEntity with HTTP status of INTERNAL_SERVER_ERROR otherwise
	 */
	@GetMapping("/{id}")
	public ResponseEntity<Ticket> getTicket (@PathVariable int id) {
		LOG.info("GET /heroes/" + id);
		try {
			Ticket ticket = ticketDao.getTicket(id);
			if (ticket != null) {
				return new ResponseEntity<Ticket>(ticket, HttpStatus.OK);
			} else {
				return new ResponseEntity<>(HttpStatus.NOT_FOUND);
			}
		} catch (IOException e) {
			LOG.log(Level.SEVERE, e.getLocalizedMessage());
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	/**
	 * Responds to the GET request for all {@linkplain Movie heroes}
	 *
	 * @return ResponseEntity with array of {@link Movie hero} objects (may be empty) and
	 * HTTP status of OK<br>
	 * ResponseEntity with HTTP status of INTERNAL_SERVER_ERROR otherwise
	 */
	@GetMapping("")
	public ResponseEntity<Ticket[]> getTickets () {
		LOG.info("GET /heroes");

		try {
			// Get the list of all heros and check if it is null or not (return appropriately)
			Ticket[] hero = ticketDao.getTickets();
			if (hero != null) {
				return new ResponseEntity<Ticket[]>(hero, HttpStatus.OK);
			} else {
				return new ResponseEntity<>(HttpStatus.NOT_FOUND);
			}

		} catch (IOException e) {
			LOG.log(Level.SEVERE, e.getLocalizedMessage());
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	/**
	 * Responds to the GET request for all {@linkplain Movie heroes} whose name contains
	 * the text in name
	 *
	 * @param name The name parameter which contains the text used to find the {@link Movie heroes}
	 * @return ResponseEntity with array of {@link Movie hero} objects (may be empty) and
	 * HTTP status of OK<br>
	 * ResponseEntity with HTTP status of INTERNAL_SERVER_ERROR otherwise
	 * <p>
	 * Example: Find all heroes that contain the text "ma"
	 * GET http://localhost:8080/heroes/?name=ma
	 */
	@GetMapping("/")
	public ResponseEntity<Ticket[]> searchHeroes (@RequestParam String name) {
		LOG.info("GET /heroes/?name=" + name);

		try {
			Ticket[] hero = ticketDao.findTickets(name);
			if (hero != null) {
				return new ResponseEntity<Ticket[]>(hero, HttpStatus.OK);
			} else {
				return new ResponseEntity<>(HttpStatus.NOT_FOUND);
			}
		} catch (IOException e) {
			LOG.log(Level.SEVERE, e.getLocalizedMessage());
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	/**
	 * Creates a {@linkplain Movie hero} with the provided hero object
	 *
	 * @param hero - The {@link Movie hero} to create
	 * @return ResponseEntity with created {@link Movie hero} object and HTTP status of CREATED<br>
	 * ResponseEntity with HTTP status of CONFLICT if {@link Movie hero} object already exists<br>
	 * ResponseEntity with HTTP status of INTERNAL_SERVER_ERROR otherwise
	 */
	@PostMapping("")
	public ResponseEntity<Ticket> createHero (@RequestBody Ticket hero) {
		LOG.info("POST /heroes " + hero);

		try {
			Ticket newHero = ticketDao.createTicket(hero);
			if (newHero != null) {
				return new ResponseEntity<Ticket>(newHero, HttpStatus.CREATED);
			} else {
				return new ResponseEntity<Ticket>(HttpStatus.CONFLICT);
			}
		} catch (IOException e) {
			LOG.log(Level.SEVERE, e.getLocalizedMessage());
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	/**
	 * Updates the {@linkplain Movie hero} with the provided {@linkplain Movie hero} object, if it exists
	 *
	 * @param hero The {@link Movie hero} to update
	 * @return ResponseEntity with updated {@link Movie hero} object and HTTP status of OK if updated<br>
	 * ResponseEntity with HTTP status of NOT_FOUND if not found<br>
	 * ResponseEntity with HTTP status of INTERNAL_SERVER_ERROR otherwise
	 */
	@PutMapping("")
	public ResponseEntity<Ticket> updateHero (@RequestBody Ticket hero) {
		LOG.info("PUT /heroes " + hero);

		try {
			Ticket newHero = ticketDao.updateTicket(hero);
			if (newHero != null) {
				return new ResponseEntity<Ticket>(newHero, HttpStatus.OK);
			} else {
				return new ResponseEntity<Ticket>(HttpStatus.NOT_FOUND);
			}
		} catch (IOException e) {
			LOG.log(Level.SEVERE, e.getLocalizedMessage());
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	/**
	 * Deletes a {@linkplain Movie hero} with the given id
	 *
	 * @param id The id of the {@link Movie hero} to deleted
	 * @return ResponseEntity HTTP status of OK if deleted<br>
	 * ResponseEntity with HTTP status of NOT_FOUND if not found<br>
	 * ResponseEntity with HTTP status of INTERNAL_SERVER_ERROR otherwise
	 */
	@DeleteMapping("/{id}")
	public ResponseEntity<Ticket> deleteHero (@PathVariable int id) {
		LOG.info("DELETE /heroes/" + id);

		try {
			boolean isDeleted = ticketDao.deleteTicket(id);
			if (isDeleted) {
				return new ResponseEntity<>(HttpStatus.OK);
			} else {
				return new ResponseEntity<>(HttpStatus.NOT_FOUND);
			}
		} catch (IOException e) {
			LOG.log(Level.SEVERE, e.getLocalizedMessage());
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
}
