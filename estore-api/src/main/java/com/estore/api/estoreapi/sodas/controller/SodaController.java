package com.estore.api.estoreapi.sodas.controller;

import com.estore.api.estoreapi.sodas.model.Soda;
import com.estore.api.estoreapi.sodas.persistence.SodaDAO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Handles the REST API requests for a Soda object.<p>
 * {@literal @}RestController Spring annotation identifies this class as a REST API method handler to the Spring framework
 *
 * @author Group 3C, The Code Monkeys
 */

@RestController
@RequestMapping("sodas")
public class SodaController {
	/** TODO: Add description of the purpose of Logger, once it's actually used. */
	private static final Logger LOG = Logger.getLogger(SodaController.class.getName());

	/** The SodaDAO object this Controller interacts with to get Soda objects. */
	private final SodaDAO sodaDao;

	/**
	 * Creates a REST API controller to respond to Soda requests.
	 *
	 * @param sodaDao The {@link SodaDAO Soda Data Access Object} to perform CRUD operations<br>
	 *                 This dependency is injected by the Spring Framework
	 */
	public SodaController (SodaDAO sodaDao) {
		this.sodaDao = sodaDao;
	}

	/**
	 * Creates a {@linkplain Soda soda} with the provided soda object.
	 *
	 * @param soda The {@link Soda soda} to create
	 * @return ResponseEntity with created {@link Soda soda} object and HTTP status of CREATED<br>
	 * ResponseEntity with HTTP status of CONFLICT if {@link Soda soda} object already exists<br>
	 * ResponseEntity with HTTP status of INTERNAL_SERVER_ERROR otherwise
	 */
	@PostMapping("")
	public ResponseEntity<Soda> createSoda (@RequestBody Soda soda) {
		LOG.info("POST /sodas/" + soda);

		try {
			Soda newSoda = sodaDao.createSoda(soda);
			if (newSoda != null) {
				return new ResponseEntity<>(newSoda, HttpStatus.CREATED);
			} else {
				return new ResponseEntity<>(HttpStatus.CONFLICT);
			}
		} catch (IOException e) {
			LOG.log(Level.SEVERE, e.getLocalizedMessage());
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	/**
	 * Updates the {@linkplain Soda soda} with the provided {@linkplain Soda soda} object, if it exists.
	 *
	 * @param soda The {@link Soda soda} to update
	 * @return ResponseEntity with updated {@link Soda soda} object and HTTP status of OK if updated<br>
	 * ResponseEntity with HTTP status of NOT_FOUND if not found<br>
	 * ResponseEntity with HTTP status of INTERNAL_SERVER_ERROR otherwise
	 */
	@PutMapping("")
	public ResponseEntity<Soda> updateSoda (@RequestBody Soda soda) {
		LOG.info("PUT /sodas/" + soda);
		try {
			Soda updatedSoda = sodaDao.updateSoda(soda);
			if (updatedSoda != null) {
				return new ResponseEntity<>(updatedSoda, HttpStatus.OK);
			} else {
				return new ResponseEntity<>(HttpStatus.NOT_FOUND);
			}
		} catch (Exception e) {
			LOG.log(Level.SEVERE, e.getLocalizedMessage());
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	/**
	 * Deletes a {@linkplain Soda soda} with the given id.
	 *
	 * @param id The id of the {@link Soda soda} to deleted
	 * @return ResponseEntity HTTP status of OK if deleted<br>
	 * ResponseEntity with HTTP status of NOT_FOUND if not found<br>
	 * ResponseEntity with HTTP status of INTERNAL_SERVER_ERROR otherwise
	 */
	@DeleteMapping("/{id}")
	public ResponseEntity<Soda> deleteSoda (@PathVariable int id) {
		LOG.info("DELETE /sodas/" + id);
		try {
			if (sodaDao.deleteSoda(id)) {
				return new ResponseEntity<>(HttpStatus.OK);
			} else {
				return new ResponseEntity<>(HttpStatus.NOT_FOUND);
			}
		} catch (IOException e) {
			LOG.log(Level.SEVERE, e.getLocalizedMessage());
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	/**
	 * Responds to the GET request for a {@linkplain Soda soda} with the given id.
	 *
	 * @param id The id used to locate a {@link Soda soda}
	 * @return ResponseEntity with {@link Soda soda} object and HTTP status of OK if found<br>
	 * ResponseEntity with HTTP status of NOT_FOUND if not found<br>
	 * ResponseEntity with HTTP status of INTERNAL_SERVER_ERROR otherwise
	 */
	@GetMapping("/{id}")
	public ResponseEntity<Soda> getSoda (@PathVariable int id) {
		LOG.info("GET /sodas/" + id);
		try {
			// Try to get the soda based on the id entered by the user
			Soda soda = sodaDao.getSoda(id);
			if (soda != null) {
				return new ResponseEntity<>(soda, HttpStatus.OK);
			} else {
				return new ResponseEntity<>(HttpStatus.NOT_FOUND);
			}
		} catch (IOException e) {
			LOG.log(Level.SEVERE, e.getLocalizedMessage());
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	/**
	 * Responds to the GET request for all {@linkplain Soda sodas}.
	 *
	 * @return ResponseEntity with array of {@link Soda soda} objects (may be empty) and HTTP status of OK<br>
	 * ResponseEntity with HTTP status of INTERNAL_SERVER_ERROR otherwise
	 */
	@GetMapping("")
	public ResponseEntity<Soda[]> getSodas () {
		LOG.info("GET /sodas/");
		try {
			// Try and get a list of all the sodas from the system
			Soda[] sodas = sodaDao.getSodas();
			if (sodas != null) {
				return new ResponseEntity<>(sodas, HttpStatus.OK);
			} else {
				return new ResponseEntity<>(HttpStatus.NOT_FOUND);
			}
		} catch (IOException e) {
			LOG.log(Level.SEVERE, e.getLocalizedMessage());
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	/**
	 * Responds to the GET request for all {@linkplain Soda sodas} whose soda title contains the given text.
	 *
	 * @param title A String which contains the text used to find the {@link Soda soda} to a soda
	 * @return ResponseEntity with array of {@link Soda soda} objects (may be empty) and HTTP status of OK<br>
	 * ResponseEntity with HTTP status of INTERNAL_SERVER_ERROR otherwise
	 */
	@GetMapping("/")
	public ResponseEntity<Soda[]> searchSodas (@RequestParam String title) {
		LOG.info("GET /sodas/?title=" + title);
		try {
			Soda[] foundSodas = sodaDao.findSodas(title);
			/*
			 * If sodaDao.findSodas() fails, an IOException is thrown. Assume function
			 * passed successfully and return the Soda array even if it is empty. Which
			 * returns a null list
			 */
			return new ResponseEntity<>(foundSodas, HttpStatus.OK);
		} catch (IOException e) {
			LOG.log(Level.SEVERE, e.getLocalizedMessage());
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
}
