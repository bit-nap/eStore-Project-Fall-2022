package com.estore.api.estoreapi.suggestions.controller;

import com.estore.api.estoreapi.suggestions.model.Suggestion;
import com.estore.api.estoreapi.suggestions.persistence.SuggestionDAO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.*;

/**
 * Test the SuggestionController class.
 *
 * @author Group 3C, The Code Monkeys
 */
@Tag("Controller-Tier")
public class SuggestionControllerTest {
	private SuggestionController suggestionController;
	private SuggestionDAO mockSuggestionDao;

	/**
	 * Before a test, create a new SuggestionController object and inject a mock Suggestion DAO.
	 */
	@BeforeEach
	public void setupSuggestionController () {
		mockSuggestionDao = mock(SuggestionDAO.class);
		suggestionController = new SuggestionController(mockSuggestionDao);
	}

	@Test
	public void testGetSuggestion () throws IOException {
		// setup
		Suggestion suggestion = new Suggestion(104, "Star Wars: Episode IV – A New Hope", 77);
		// when the same id is passed in, our mock suggestion DAO will return the Suggestion object
		when(mockSuggestionDao.getSuggestion(suggestion.getId())).thenReturn(suggestion);

		// invoke
		ResponseEntity<Suggestion> response = suggestionController.getSuggestion(suggestion.getId());

		// analyze
		assertEquals(HttpStatus.OK, response.getStatusCode());
		assertEquals(suggestion, response.getBody());
	}

	@Test
	public void testGetSuggestionNotFound () throws Exception {
		// setup
		int suggestionId = 99;
		// when the same id is passed in, our mock suggestion DAO will return null, simulating no suggestion found
		when(mockSuggestionDao.getSuggestion(suggestionId)).thenReturn(null);

		// invoke
		ResponseEntity<Suggestion> response = suggestionController.getSuggestion(suggestionId);

		// analyze
		assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
	}

	@Test
	public void testGetSuggestionHandleException () throws Exception {
		// setup
		int suggestionId = 99;
		// when getSuggestion is called on the mock suggestion DAO, throw an IOException
		doThrow(new IOException()).when(mockSuggestionDao).getSuggestion(suggestionId);

		// invoke
		ResponseEntity<Suggestion> response = suggestionController.getSuggestion(suggestionId);

		// analyze
		assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
	}

	/**
	 * Method to test if getting all the suggestions work
	 *
	 * @throws Exception if something goes wrong with the http request
	 */
	@Test
	public void testGetSuggestions () throws Exception {
		// New list of suggestions
		Suggestion[] suggestions = new Suggestion[3];
		suggestions[0] = new Suggestion(104, "Star Wars: Episode IV – A New Hope", 77);
		suggestions[1] = new Suggestion(105, "Star Wars: Episode V – The Empire Strikes Back", 80);
		suggestions[2] = new Suggestion(106, "Star Wars: Episode VI - Return of the Jedi", 83);
		// When getSuggestions is called, return the list of suggestions from above
		when(mockSuggestionDao.getSuggestions()).thenReturn(suggestions);

		ResponseEntity<Suggestion[]> response = suggestionController.getSuggestions();

		assertEquals(HttpStatus.OK, response.getStatusCode());
		assertEquals(suggestions, response.getBody());
	}

	@Test
	public void testGetEmptySuggestions () throws Exception {
		// When getSuggestions is called, return null
		when(mockSuggestionDao.getSuggestions()).thenReturn(null);
		// Get NOT_FOUND response from SuggestionController
		ResponseEntity<Suggestion[]> response = suggestionController.getSuggestions();

		assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
		assertNull(response.getBody());
	}

	/**
	 * Test to make sure the exception is handled when getSuggestions throws one
	 *
	 * @throws Exception if something goes wrong with Http request
	 */
	@Test
	public void testGetSuggestionsHandleException () throws Exception {
		// Throw an exception when the get suggestions method is called
		doThrow(new IOException()).when(mockSuggestionDao).getSuggestions();

		ResponseEntity<Suggestion[]> response = suggestionController.getSuggestions();

		assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
	}

	@Test
	public void testCreateSuggestion () throws IOException {
		// setup
		Suggestion suggestion = new Suggestion(104, "Star Wars: Episode IV – A New Hope", 77);
		// when createSuggestion is called, return true simulating successful creation and save
		when(mockSuggestionDao.createSuggestion(suggestion)).thenReturn(suggestion);

		// invoke
		ResponseEntity<Suggestion> response = suggestionController.createSuggestion(suggestion);

		// analyze
		assertEquals(HttpStatus.CREATED, response.getStatusCode());
		assertEquals(suggestion, response.getBody());
	}

	@Test
	public void testCreateSuggestionFailed () throws IOException {
		// setup
		Suggestion suggestion = new Suggestion(104, "Star Wars: Episode IV – A New Hope", 77);
		// when createSuggestion is called, return false simulating failed creation and save
		when(mockSuggestionDao.createSuggestion(suggestion)).thenReturn(null);

		// invoke
		ResponseEntity<Suggestion> response = suggestionController.createSuggestion(suggestion);

		// analyze
		assertEquals(HttpStatus.CONFLICT, response.getStatusCode());
	}

	@Test
	public void testCreateSuggestionHandleException () throws IOException {
		// setup
		Suggestion suggestion = new Suggestion(104, "Star Wars: Episode IV – A New Hope", 77);

		// when createSuggestion is called, throw an IOException
		doThrow(new IOException()).when(mockSuggestionDao).createSuggestion(suggestion);

		// invoke
		ResponseEntity<Suggestion> response = suggestionController.createSuggestion(suggestion);

		// analyze
		assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
	}

	@Test
	public void testUpdateSuggestion () throws IOException {
		// Setup
		Suggestion suggestion = new Suggestion(104, "Star Wars: Episode IV – A New Hope", 77);
		// when updateSuggestion is called, return true simulating successful update and save
		when(mockSuggestionDao.updateSuggestion(suggestion)).thenReturn(suggestion);

		// Invoke
		ResponseEntity<Suggestion> response = suggestionController.updateSuggestion(suggestion);

		// Analyze
		assertEquals(HttpStatus.OK, response.getStatusCode());
		assertEquals(suggestion, response.getBody());
	}

	@Test
	public void testUpdateSuggestionExceptionNotFound () throws IOException {
		// Setup
		Suggestion suggestion = new Suggestion(104, "Star Wars: Episode IV – A New Hope", 77);
		// when updateSuggestion is called, return true simulating successful update and save
		when(mockSuggestionDao.updateSuggestion(suggestion)).thenReturn(null);

		// Invoke
		ResponseEntity<Suggestion> response = suggestionController.updateSuggestion(suggestion);

		// Analyze
		assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
	}

	@Test
	public void testUpdateSuggestionHandleException () throws IOException {
		// Setup
		Suggestion suggestion = new Suggestion(104, "Star Wars: Episode IV – A New Hope", 77);
		// When updateSuggestion is called on the Mock Suggestion DAO, throw an IOException
		doThrow(new IOException()).when(mockSuggestionDao).updateSuggestion(suggestion);

		// Invoke
		ResponseEntity<Suggestion> response = suggestionController.updateSuggestion(suggestion);

		// Analyze
		assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
	}

	@Test
	public void testDeleteSuggestion () throws IOException { // deleteSuggestion may throw IOException
		// Setup
		int suggestionId = 99;
		// when deleteSuggestion is called return true, simulating successful deletion
		when(mockSuggestionDao.deleteSuggestion(suggestionId)).thenReturn(true);

		// Invoke
		ResponseEntity<Suggestion> response = suggestionController.deleteSuggestion(suggestionId);

		// Analyze
		assertEquals(HttpStatus.OK, response.getStatusCode());
	}

	@Test
	public void testDeleteSuggestionNotFound () throws IOException { // deleteSuggestion may throw IOException
		// Setup
		int suggestionId = 99;
		// when deleteSuggestion is called return false, simulating failed deletion
		when(mockSuggestionDao.deleteSuggestion(suggestionId)).thenReturn(false);

		// Invoke
		ResponseEntity<Suggestion> response = suggestionController.deleteSuggestion(suggestionId);

		// Analyze
		assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
	}

	@Test
	public void testDeleteSuggestionHandleException () throws IOException { // deleteSuggestion may throw IOException
		// Setup
		int suggestionId = 99;
		// When deleteSuggestion is called on the Mock Suggestion DAO, throw an IOException
		doThrow(new IOException()).when(mockSuggestionDao).deleteSuggestion(suggestionId);

		// Invoke
		ResponseEntity<Suggestion> response = suggestionController.deleteSuggestion(suggestionId);

		// Analyze
		assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
	}
}
