package com.estore.api.estoreapi.controller;

import com.estore.api.estoreapi.model.Screening;
import com.estore.api.estoreapi.persistence.ScreeningDAO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

/**
 * Test the ScreeningController class.
 *
 * @author Group 3C, The Code Monkeys
 */
@Tag("Controller-Tier")
public class ScreeningControllerTest {
	private ScreeningController screeningController;
	private ScreeningDAO mockScreeningDao;

	/**
	 * Before a test, create a new ScreeningController object and inject a mock Screening DAO.
	 */
	@BeforeEach
	public void setupScreeningController () {
		mockScreeningDao = mock(ScreeningDAO.class);
		screeningController = new ScreeningController(mockScreeningDao);
	}

	@Test
	public void testGetScreening () throws IOException {
		// setup
		Screening screening = new Screening(99, "Star Wars");
		// when the same id is passed in, our mock screening DAO will return the Screening object
		when(mockScreeningDao.getScreening(screening.getId())).thenReturn(screening);

		// invoke
		ResponseEntity<Screening> response = screeningController.getScreening(screening.getId());

		// analyze
		assertEquals(HttpStatus.OK, response.getStatusCode());
		assertEquals(screening, response.getBody());
	}

	@Test
	public void testGetScreeningNotFound () throws Exception {
		// setup
		int screeningId = 99;
		// when the same id is passed in, our mock screening DAO will return null, simulating no screening found
		when(mockScreeningDao.getScreening(screeningId)).thenReturn(null);

		// invoke
		ResponseEntity<Screening> response = screeningController.getScreening(screeningId);

		// analyze
		assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
	}

	@Test
	public void testGetScreeningHandleException () throws Exception {
		// setup
		int screeningId = 99;
		// when getScreening is called on the mock screening DAO, throw an IOException
		doThrow(new IOException()).when(mockScreeningDao).getScreening(screeningId);

		// invoke
		ResponseEntity<Screening> response = screeningController.getScreening(screeningId);

		// analyze
		assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
	}

	@Test
	public void testCreateScreening () throws IOException {
		// setup
		Screening screening = new Screening(72, "The Godfather");
		// when createScreening is called, return true simulating successful creation and save
		when(mockScreeningDao.createScreening(screening)).thenReturn(screening);

		// invoke
		ResponseEntity<Screening> response = screeningController.createScreening(screening);

		// analyze
		assertEquals(HttpStatus.CREATED, response.getStatusCode());
		assertEquals(screening, response.getBody());
	}

	@Test
	public void testCreateScreeningFailed () throws IOException {
		// setup
		Screening screening = new Screening(72, "The Godfather");
		// when createScreening is called, return false simulating failed creation and save
		when(mockScreeningDao.createScreening(screening)).thenReturn(null);

		// invoke
		ResponseEntity<Screening> response = screeningController.createScreening(screening);

		// analyze
		assertEquals(HttpStatus.CONFLICT, response.getStatusCode());
	}

	@Test
	public void testCreateScreeningHandleException () throws IOException {
		// setup
		Screening screening = new Screening(72, "The Godfather");

		// when createScreening is called, throw an IOException
		doThrow(new IOException()).when(mockScreeningDao).createScreening(screening);

		// invoke
		ResponseEntity<Screening> response = screeningController.createScreening(screening);

		// analyze
		assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
	}

	@Test
	public void testSearchScreenings () throws IOException {
		// Setup
		String searchString = "The";
		Screening[] foundScreenings = new Screening[2];
		foundScreenings[0] = new Screening(99, "The Terminator");
		foundScreenings[1] = new Screening(100, "The Godfather");
		// When findScreenings is called with the search string, return the two
		// screenings above
		when(mockScreeningDao.findScreenings(searchString)).thenReturn(foundScreenings);

		// Invoke
		ResponseEntity<Screening[]> response = screeningController.searchScreenings(searchString);

		// Analyze
		assertEquals(HttpStatus.OK, response.getStatusCode());
		assertEquals(foundScreenings, response.getBody());
	}

	@Test
	public void testSearchScreeningsHandleException () throws IOException {
		// Setup
		String searchString = "an";
		// When createScreening is called on the Mock Screening DAO, throw an IOException
		doThrow(new IOException()).when(mockScreeningDao).findScreenings(searchString);

		// Invoke
		ResponseEntity<Screening[]> response = screeningController.searchScreenings(searchString);

		// Analyze
		assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
	}

	@Test
	public void testUpdateScreening () throws IOException {
		// Setup
		Screening screening = new Screening(99, "Star Wars IV: A New Hope");
		// when updateScreening is called, return true simulating successful
		// update and save
		when(mockScreeningDao.updateScreening(screening)).thenReturn(screening);
		ResponseEntity<Screening> response = screeningController.updateScreening(screening);
		screening.setMovie("Star Wars V: The Empire Strikes Back");

		// Invoke
		response = screeningController.updateScreening(screening);

		// Analyze
		assertEquals(HttpStatus.OK, response.getStatusCode());
		assertEquals(screening, response.getBody());
	}

	@Test
	public void testUpdateScreeningExceptionNotFound () throws IOException {
		// Setup
		Screening screening = new Screening(99, "Spider-Man");
		// when updateScreening is called, return true simulating successful
		// update and save
		when(mockScreeningDao.updateScreening(screening)).thenReturn(null);

		// Invoke
		ResponseEntity<Screening> response = screeningController.updateScreening(screening);

		// Analyze
		assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
	}

	@Test
	public void testUpdateScreeningHandleException () throws IOException {
		// Setup
		Screening screening = new Screening(99, "Spider-Man");
		// When updateScreening is called on the Mock Screening DAO, throw an IOException
		doThrow(new IOException()).when(mockScreeningDao).updateScreening(screening);

		// Invoke
		ResponseEntity<Screening> response = screeningController.updateScreening(screening);

		// Analyze
		assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
	}

	@Test
	public void testDeleteScreening () throws IOException { // deleteScreening may throw IOException
		// Setup
		int screeningId = 99;
		// when deleteScreening is called return true, simulating successful deletion
		when(mockScreeningDao.deleteScreening(screeningId)).thenReturn(true);

		// Invoke
		ResponseEntity<Screening> response = screeningController.deleteScreening(screeningId);

		// Analyze
		assertEquals(HttpStatus.OK, response.getStatusCode());
	}

	@Test
	public void testDeleteScreeningNotFound () throws IOException { // deleteScreening may throw IOException
		// Setup
		int screeningId = 99;
		// when deleteScreening is called return false, simulating failed deletion
		when(mockScreeningDao.deleteScreening(screeningId)).thenReturn(false);

		// Invoke
		ResponseEntity<Screening> response = screeningController.deleteScreening(screeningId);

		// Analyze
		assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
	}

	@Test
	public void testDeleteScreeningHandleException () throws IOException { // deleteScreening may throw IOException
		// Setup
		int screeningId = 99;
		// When deleteScreening is called on the Mock Screening DAO, throw an IOException
		doThrow(new IOException()).when(mockScreeningDao).deleteScreening(screeningId);

		// Invoke
		ResponseEntity<Screening> response = screeningController.deleteScreening(screeningId);

		// Analyze
		assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
	}
}
