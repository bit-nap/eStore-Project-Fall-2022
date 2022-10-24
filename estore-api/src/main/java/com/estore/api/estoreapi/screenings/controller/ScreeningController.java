package com.estore.api.estoreapi.screenings.controller;

import com.estore.api.estoreapi.screenings.model.Screening;
import com.estore.api.estoreapi.screenings.persistence.ScreeningDAO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.time.LocalDate;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Handles the REST API requests for a Screening object.<p>
 * {@literal @}RestController Spring annotation identifies this class as a REST API method handler to the Spring framework
 *
 * @author Group 3C, The Code Monkeys
 */

@RestController
@RequestMapping("screenings")
public class ScreeningController {
	/** TODO: Add description of the purpose of Logger, once it's actually used. */
	private static final Logger LOG = Logger.getLogger(ScreeningController.class.getName());

	/** The ScreeningDAO object this Controller interacts with to get Screening objects. */
	private final ScreeningDAO screeningDao;

	/**
	 * Creates a REST API controller to respond to Screening requests.
	 *
	 * @param screeningDao The {@link ScreeningDAO Screening Data Access Object} to perform CRUD operations<br>
	 *                     This dependency is injected by the Spring Framework
	 */
	public ScreeningController (ScreeningDAO screeningDao) {
		this.screeningDao = screeningDao;
	}

	/**
	 * Creates a {@linkplain Screening screening} with the provided screening object.
	 *
	 * @param screening The {@link Screening screening} to create
	 * @return ResponseEntity with created {@link Screening screening} object and HTTP status of CREATED<br>
	 * ResponseEntity with HTTP status of CONFLICT if {@link Screening screening} object already exists<br>
	 * ResponseEntity with HTTP status of INTERNAL_SERVER_ERROR otherwise
	 */
	@PostMapping("")
	public ResponseEntity<Screening> createScreening (@RequestBody Screening screening) {
		LOG.info("POST /screenings/" + screening);

		try {
			Screening newScreening = screeningDao.createScreening(screening);
			if (newScreening != null) {
				return new ResponseEntity<>(newScreening, HttpStatus.CREATED);
			} else {
				return new ResponseEntity<>(HttpStatus.CONFLICT);
			}
		} catch (IOException e) {
			LOG.log(Level.SEVERE, e.getLocalizedMessage());
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	/**
	 * Updates the {@linkplain Screening screening} with the provided {@linkplain Screening screening} object, if it exists.
	 *
	 * @param screening The {@link Screening screening} to update
	 * @return ResponseEntity with updated {@link Screening screening} object and HTTP status of OK if updated<br>
	 * ResponseEntity with HTTP status of NOT_FOUND if not found<br>
	 * ResponseEntity with HTTP status of INTERNAL_SERVER_ERROR otherwise
	 */
	@PutMapping("")
	public ResponseEntity<Screening> updateScreening (@RequestBody Screening screening) {
		LOG.info("PUT /screenings/" + screening);
		try {
			Screening updatedScreening = screeningDao.updateScreening(screening);
			if (updatedScreening != null) {
				return new ResponseEntity<>(updatedScreening, HttpStatus.OK);
			} else {
				return new ResponseEntity<>(HttpStatus.NOT_FOUND);
			}
		} catch (Exception e) {
			LOG.log(Level.SEVERE, e.getLocalizedMessage());
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	/**
	 * Deletes a {@linkplain Screening screening} with the given id.
	 *
	 * @param id The id of the {@link Screening screening} to deleted
	 * @return ResponseEntity HTTP status of OK if deleted<br>
	 * ResponseEntity with HTTP status of NOT_FOUND if not found<br>
	 * ResponseEntity with HTTP status of INTERNAL_SERVER_ERROR otherwise
	 */
	@DeleteMapping("/{id}")
	public ResponseEntity<Screening> deleteScreening (@PathVariable int id) {
		LOG.info("DELETE /screenings/" + id);
		try {
			if (screeningDao.deleteScreening(id)) {
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
	 * Responds to the GET request for a {@linkplain Screening screening} with the given id.
	 *
	 * @param id The id used to locate a {@link Screening screening}
	 * @return ResponseEntity with {@link Screening screening} object and HTTP status of OK if found<br>
	 * ResponseEntity with HTTP status of NOT_FOUND if not found<br>
	 * ResponseEntity with HTTP status of INTERNAL_SERVER_ERROR otherwise
	 */
	@GetMapping("/{id}")
	public ResponseEntity<Screening> getScreening (@PathVariable int id) {
		LOG.info("GET /screenings/" + id);
		try {
			// Try to get the screening based on the id entered by the user
			Screening screening = screeningDao.getScreening(id);
			if (screening != null) {
				return new ResponseEntity<>(screening, HttpStatus.OK);
			} else {
				return new ResponseEntity<>(HttpStatus.NOT_FOUND);
			}
		} catch (IOException e) {
			LOG.log(Level.SEVERE, e.getLocalizedMessage());
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	/**
	 * Responds to the GET request for all {@linkplain Screening screenings}.
	 *
	 * @return ResponseEntity with array of {@link Screening screening} objects (may be empty) and HTTP status of OK<br>
	 * ResponseEntity with HTTP status of INTERNAL_SERVER_ERROR otherwise
	 */
	@GetMapping("")
	public ResponseEntity<Screening[]> getScreenings () {
		LOG.info("GET /screenings/");
		try {
			// Try and get a list of all the screenings from the system
			Screening[] screenings = screeningDao.getScreenings();
			if (screenings != null) {
				return new ResponseEntity<>(screenings, HttpStatus.OK);
			} else {
				return new ResponseEntity<>(HttpStatus.NOT_FOUND);
			}
		} catch (IOException e) {
			LOG.log(Level.SEVERE, e.getLocalizedMessage());
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	/**
	 * Responds to the GET request for all {@linkplain Screening screenings} whose screening title contains the given text.
	 *
	 * @param title A String which contains the text used to find the {@link Screening screening} to a screening
	 * @return ResponseEntity with array of {@link Screening screening} objects (may be empty) and HTTP status of OK<br>
	 * ResponseEntity with HTTP status of INTERNAL_SERVER_ERROR otherwise
	 */
	@GetMapping("/")
	public ResponseEntity<Screening[]> searchScreenings (@RequestParam String title) {
		LOG.info("GET /screenings/?title=" + title);
		try {
			Screening[] foundScreenings = screeningDao.findScreenings(title);
			/*
			 * If screeningDao.findScreenings() fails, an IOException is thrown. Assume function
			 * passed successfully and return the Screening array even if it is empty. Which
			 * returns a null list
			 */
			return new ResponseEntity<>(foundScreenings, HttpStatus.OK);
		} catch (IOException e) {
			LOG.log(Level.SEVERE, e.getLocalizedMessage());
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	/**
	 * Responds to the GET request for all {@linkplain Screening screenings} whose screening title contains the given text.
	 *
	 * @param date A String which contains the text used to find the {@link Screening screening} to a screening
	 * @return ResponseEntity with array of {@link Screening screening} objects (may be empty) and HTTP status of OK<br>
	 * ResponseEntity with HTTP status of INTERNAL_SERVER_ERROR otherwise
	 */
	@GetMapping("/date/")
	public ResponseEntity<Screening[]> searchScreeningsByDate (@RequestParam LocalDate date) {
		LOG.info("GET /screening/?date=" + date);
		try {
			Screening[] foundScreenings = screeningDao.findScreeningsByDate(date);
			/*
			 * If screeningDao.findScreeningsByDate() fails, an IOException is thrown. Assume function
			 * passed successfully and return the Screening array even if it is empty. Which
			 * returns a null list
			 */
			return new ResponseEntity<>(foundScreenings, HttpStatus.OK);
		} catch (IOException e) {
			LOG.log(Level.SEVERE, e.getLocalizedMessage());
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
}
