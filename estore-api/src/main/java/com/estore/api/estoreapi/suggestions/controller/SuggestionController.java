package com.estore.api.estoreapi.suggestions.controller;

import com.estore.api.estoreapi.suggestions.model.Suggestion;
import com.estore.api.estoreapi.suggestions.persistence.SuggestionDAO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Handles the REST API requests for a Suggestion object.<p>
 * {@literal @}RestController Spring annotation identifies this class as a REST API method handler to the Spring framework
 *
 * @author Group 3C, The Code Monkeys
 */

@RestController
@RequestMapping("suggestions")
public class SuggestionController {
	/* Logger is used to log to command line the HTTP request performed, or any internal server errors encountered. */
	private static final Logger LOG = Logger.getLogger(SuggestionController.class.getName());

	/** The SuggestionDAO object this Controller interacts with to get Suggestion objects. */
	private final SuggestionDAO suggestionDao;

	/**
	 * Creates a REST API controller to respond to Suggestion requests.
	 *
	 * @param suggestionDao The {@link SuggestionDAO Suggestion Data Access Object} to perform CRUD operations<br>
	 *                      This dependency is injected by the Spring Framework
	 */
	public SuggestionController (SuggestionDAO suggestionDao) {
		this.suggestionDao = suggestionDao;
	}

	/**
	 * Creates a {@linkplain Suggestion suggestion} with the provided suggestion object.
	 *
	 * @param suggestion The {@link Suggestion suggestion} to create
	 * @return ResponseEntity with created {@link Suggestion suggestion} object and HTTP status of CREATED<br>
	 * ResponseEntity with HTTP status of CONFLICT if {@link Suggestion suggestion} object already exists<br>
	 * ResponseEntity with HTTP status of INTERNAL_SERVER_ERROR otherwise
	 */
	@PostMapping("")
	public ResponseEntity<Suggestion> createSuggestion (@RequestBody Suggestion suggestion) {
		LOG.info("POST /suggestions/" + suggestion);

		try {
			Suggestion newSuggestion = suggestionDao.createSuggestion(suggestion);
			if (newSuggestion != null) {
				return new ResponseEntity<>(newSuggestion, HttpStatus.CREATED);
			} else {
				return new ResponseEntity<>(HttpStatus.CONFLICT);
			}
		} catch (IOException e) {
			LOG.log(Level.SEVERE, e.getLocalizedMessage());
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	/**
	 * Updates the {@linkplain Suggestion suggestion} with the provided {@linkplain Suggestion suggestion} object, if it exists.
	 *
	 * @param suggestion The {@link Suggestion suggestion} to update
	 * @return ResponseEntity with updated {@link Suggestion suggestion} object and HTTP status of OK if updated<br>
	 * ResponseEntity with HTTP status of NOT_FOUND if not found<br>
	 * ResponseEntity with HTTP status of INTERNAL_SERVER_ERROR otherwise
	 */
	@PutMapping("")
	public ResponseEntity<Suggestion> updateSuggestion (@RequestBody Suggestion suggestion) {
		LOG.info("PUT /suggestions/" + suggestion);
		try {
			Suggestion updatedSuggestion = suggestionDao.updateSuggestion(suggestion);
			if (updatedSuggestion != null) {
				return new ResponseEntity<>(updatedSuggestion, HttpStatus.OK);
			} else {
				return new ResponseEntity<>(HttpStatus.NOT_FOUND);
			}
		} catch (Exception e) {
			LOG.log(Level.SEVERE, e.getLocalizedMessage());
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	/**
	 * Deletes a {@linkplain Suggestion suggestion} with the given id.
	 *
	 * @param id The id of the {@link Suggestion suggestion} to be deleted
	 * @return ResponseEntity HTTP status of OK if deleted<br>
	 * ResponseEntity with HTTP status of NOT_FOUND if not found<br>
	 * ResponseEntity with HTTP status of INTERNAL_SERVER_ERROR otherwise
	 */
	@DeleteMapping("/{id}")
	public ResponseEntity<Suggestion> deleteSuggestion (@PathVariable int id) {
		LOG.info("DELETE /suggestions/" + id);
		try {
			if (suggestionDao.deleteSuggestion(id)) {
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
	 * Responds to the GET request for a {@linkplain Suggestion suggestion} with the given id.
	 *
	 * @param id The id used to locate a {@link Suggestion suggestion}
	 * @return ResponseEntity with {@link Suggestion suggestion} object and HTTP status of OK if found<br>
	 * ResponseEntity with HTTP status of NOT_FOUND if not found<br>
	 * ResponseEntity with HTTP status of INTERNAL_SERVER_ERROR otherwise
	 */
	@GetMapping("/{id}")
	public ResponseEntity<Suggestion> getSuggestion (@PathVariable int id) {
		LOG.info("GET /suggestions/" + id);
		try {
			// Try to get the suggestion based on the id entered by the user
			Suggestion suggestion = suggestionDao.getSuggestion(id);
			if (suggestion != null) {
				return new ResponseEntity<>(suggestion, HttpStatus.OK);
			} else {
				return new ResponseEntity<>(HttpStatus.NOT_FOUND);
			}
		} catch (IOException e) {
			LOG.log(Level.SEVERE, e.getLocalizedMessage());
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	/**
	 * Responds to the GET request for all {@linkplain Suggestion suggestions}.
	 *
	 * @return ResponseEntity with array of {@link Suggestion suggestion} objects (may be empty) and HTTP status of OK<br>
	 * ResponseEntity with HTTP status of INTERNAL_SERVER_ERROR otherwise
	 */
	@GetMapping("")
	public ResponseEntity<Suggestion[]> getSuggestions () {
		LOG.info("GET /suggestions/");
		try {
			// Try and get a list of all the suggestions from the system
			Suggestion[] suggestions = suggestionDao.getSuggestions();
			if (suggestions != null) {
				return new ResponseEntity<>(suggestions, HttpStatus.OK);
			} else {
				return new ResponseEntity<>(HttpStatus.NOT_FOUND);
			}
		} catch (IOException e) {
			LOG.log(Level.SEVERE, e.getLocalizedMessage());
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
}
