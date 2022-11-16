package com.estore.api.estoreapi.screenings.controller;

import com.estore.api.estoreapi.screenings.model.Screening;
import com.estore.api.estoreapi.screenings.persistence.ScreeningDAO;
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
		boolean[][] seats = { { false, false, false, false, false }, { false, false, true, true, true },
			{ true, true, true, true, true }, { true, true, true, true, true } };
		Screening screening = new Screening(101, 104, 6, "01/17/2023", "18:00", seats);
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
		int screeningId = 101;
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
		int screeningId = 101;
		// when getScreening is called on the mock screening DAO, throw an IOException
		doThrow(new IOException()).when(mockScreeningDao).getScreening(screeningId);

		// invoke
		ResponseEntity<Screening> response = screeningController.getScreening(screeningId);

		// analyze
		assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
	}

	/**
	 * Method to test if getting all of the screenings works
	 *
	 * @throws Exception if something goes wrong with the http request
	 */
	@Test
	public void testGetScreenings () throws Exception {
		// New list of screenings
		boolean[][] seats = { { false, false, false, false, false }, { false, false, true, true, true },
			{ true, true, true, true, true }, { true, true, true, true, true } };
		Screening[] screenings = new Screening[3];
		screenings[0] = new Screening(1, 1, 80, "01/01/2023", "18:00:00", seats);
		screenings[1] = new Screening(2, 2, 50, "01/01/2023", "18:00:00", seats);
		screenings[2] = new Screening(3, 2, 20, "01/01/2023", "18:00:00", seats);
		// When getScreenings is called, return the list of screenings from above
		when(mockScreeningDao.getScreenings()).thenReturn(screenings);

		ResponseEntity<Screening[]> response = screeningController.getScreenings();

		assertEquals(HttpStatus.OK, response.getStatusCode());
		assertEquals(screenings, response.getBody());
	}

	@Test
	public void testGetEmptyScreenings () throws Exception {
		// When getScreenings is called, return null
		when(mockScreeningDao.getScreenings()).thenReturn(null);
		// Get NOT_FOUND response from ScreeningController
		ResponseEntity<Screening[]> response = screeningController.getScreenings();

		assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
		assertNull(response.getBody());
	}

	/**
	 * Test to make sure the exception is handled when getScreenings throws one
	 *
	 * @throws Exception if something goes wrong with Http request
	 */
	@Test
	public void testGetScreeningsHandleException () throws Exception {
		// Throw an exception when the get screenings method is called
		doThrow(new IOException()).when(mockScreeningDao).getScreenings();

		ResponseEntity<Screening[]> response = screeningController.getScreenings();

		assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
	}

	@Test
	public void testCreateScreening () throws IOException {
		// setup
		boolean[][] seats = { { false, false, false, false, false }, { false, false, true, true, true },
			{ true, true, true, true, true }, { true, true, true, true, true } };
		Screening screening = new Screening(101, 104, 6, "01/17/2023", "18:00", seats);
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
		boolean[][] seats = { { false, false, false, false, false }, { false, false, true, true, true },
			{ true, true, true, true, true }, { true, true, true, true, true } };
		Screening screening = new Screening(101, 104, 6, "01/17/2023", "18:00", seats);
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
		boolean[][] seats = { { false, false, false, false, false }, { false, false, true, true, true },
			{ true, true, true, true, true }, { true, true, true, true, true } };
		Screening screening = new Screening(101, 104, 6, "01/17/2023", "18:00", seats);

		// when createScreening is called, throw an IOException
		doThrow(new IOException()).when(mockScreeningDao).createScreening(screening);

		// invoke
		ResponseEntity<Screening> response = screeningController.createScreening(screening);

		// analyze
		assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
	}

	@Test
	public void testSearchScreeningsByMovieId () throws IOException {
		// Setup
		int searchId = 104; // the movieId of 104 points to Star Wars IV
		Screening[] foundScreenings = new Screening[2];
		boolean[][] seats = { { false, false, false, false, false }, { false, false, true, true, true },
			{ true, true, true, true, true }, { true, true, true, true, true } };
		foundScreenings[0] = new Screening(101, 104, 6, "01/17/2023", "18:00", seats);
		foundScreenings[1] = new Screening(102, 104, 6, "01/17/2023", "18:00", seats);
		// When findScreenings is called with the search string, return the two screenings above
		when(mockScreeningDao.findScreeningsForMovie(searchId)).thenReturn(foundScreenings);

		// Invoke
		ResponseEntity<Screening[]> response = screeningController.searchScreeningsByMovieId(searchId);

		// Analyze
		assertEquals(HttpStatus.OK, response.getStatusCode());
		assertEquals(foundScreenings, response.getBody());
	}

	@Test
	public void testSearchScreeningsByMovieIdHandleException () throws IOException {
		// Setup
		int searchId = 104;
		// When createScreening is called on the Mock Screening DAO, throw an IOException
		doThrow(new IOException()).when(mockScreeningDao).findScreeningsForMovie(searchId);

		// Invoke
		ResponseEntity<Screening[]> response = screeningController.searchScreeningsByMovieId(searchId);

		// Analyze
		assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
	}

	@Test
	public void testUpdateScreening () throws IOException {
		// Setup
		boolean[][] seats = { { false, false, false, false, false }, { false, false, true, true, true },
			{ true, true, true, true, true }, { true, true, true, true, true } };
		Screening screening = new Screening(101, 104, 6, "01/17/2023", "18:00", seats);
		// when updateScreening is called, return true simulating successful update and save
		when(mockScreeningDao.updateScreening(screening)).thenReturn(screening);
		screening.setMovieId(105); // does not exist, but will not throw an error because of setup in @BeforeEach method

		// Invoke
		ResponseEntity<Screening> response = screeningController.updateScreening(screening);

		// Analyze
		assertEquals(HttpStatus.OK, response.getStatusCode());
		assertEquals(screening, response.getBody());
	}

	@Test
	public void testUpdateScreeningExceptionNotFound () throws IOException {
		// Setup
		boolean[][] seats = { { false, false, false, false, false }, { false, false, true, true, true },
			{ true, true, true, true, true }, { true, true, true, true, true } };
		Screening screening = new Screening(101, 104, 6, "01/17/2023", "18:00", seats);
		// when updateScreening is called, return null simulating screening not found
		when(mockScreeningDao.updateScreening(screening)).thenReturn(null);

		// Invoke
		ResponseEntity<Screening> response = screeningController.updateScreening(screening);

		// Analyze
		assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
	}

	@Test
	public void testUpdateScreeningHandleException () throws IOException {
		// Setup
		boolean[][] seats = { { false, false, false, false, false }, { false, false, true, true, true },
			{ true, true, true, true, true }, { true, true, true, true, true } };
		Screening screening = new Screening(101, 104, 6, "01/17/2023", "18:00", seats);
		// When updateScreening is called on the Mock Screening DAO, throw an IOException
		doThrow(new IOException()).when(mockScreeningDao).updateScreening(screening);

		// Invoke
		ResponseEntity<Screening> response = screeningController.updateScreening(screening);

		// Analyze
		assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
	}

	@Test
	public void testDeleteScreening () throws IOException {
		// Setup
		int screeningId = 101;
		// when deleteScreening is called return true, simulating successful deletion
		when(mockScreeningDao.deleteScreening(screeningId)).thenReturn(true);

		// Invoke
		ResponseEntity<Screening> response = screeningController.deleteScreening(screeningId);

		// Analyze
		assertEquals(HttpStatus.OK, response.getStatusCode());
	}

	@Test
	public void testDeleteScreeningNotFound () throws IOException {
		// Setup
		int screeningId = 101;
		// when deleteScreening is called return false, simulating failed deletion
		when(mockScreeningDao.deleteScreening(screeningId)).thenReturn(false);

		// Invoke
		ResponseEntity<Screening> response = screeningController.deleteScreening(screeningId);

		// Analyze
		assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
	}

	@Test
	public void testDeleteScreeningHandleException () throws IOException {
		// Setup
		int screeningId = 101;
		// When deleteScreening is called on the Mock Screening DAO, throw an IOException
		doThrow(new IOException()).when(mockScreeningDao).deleteScreening(screeningId);

		// Invoke
		ResponseEntity<Screening> response = screeningController.deleteScreening(screeningId);

		// Analyze
		assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
	}
}
