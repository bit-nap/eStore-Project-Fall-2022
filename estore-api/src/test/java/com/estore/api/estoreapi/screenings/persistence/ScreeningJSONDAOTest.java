package com.estore.api.estoreapi.screenings.persistence;

import com.estore.api.estoreapi.screenings.model.Screening;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

/**
 * Test the Screening File DAO class
 *
 * @author Group 3C, The Code Monkeys
 */
@Tag("Persistence-tier")
class ScreeningJSONDAOTest {
	ScreeningJSONDAO screeningFileDAO;
	Screening[] testScreenings;
	ObjectMapper mockObjectMapper;

	/**
	 * Before each test, we will create and inject a Mock Object Mapper to
	 * isolate the tests from the underlying file
	 *
	 * @throws IOException if screeningFileDAO cannot read from fake file
	 */
	@BeforeEach
	void setupScreeningJSONDAO () throws IOException {
		mockObjectMapper = mock(ObjectMapper.class);

		testScreenings = new Screening[3];
		boolean[][] seats = { { false, false, false, false, false }, { false, false, true, true, true },
			{ true, true, true, true, true }, { true, true, true, true, true } };
		testScreenings[0] = new Screening(101, 104, 6, "01/17/2023", "18:00", seats);
		testScreenings[1] = new Screening(102, 104, 0, "01/17/2023", "20:00", seats);
		testScreenings[2] = new Screening(103, 104, 8, "01/17/2023", "22:00", seats);

		// When the object mapper is supposed to read from the file the mock object
		// mapper will return the screening array above
		when(mockObjectMapper.readValue(new File("mao-zedongs-little-red-book.epub"), Screening[].class))
			.thenReturn(testScreenings);
		screeningFileDAO = new ScreeningJSONDAO("mao-zedongs-little-red-book.epub", mockObjectMapper);
	}

	@Test
	void testGetScreenings () {
		// Invoke
		Screening[] screenings = screeningFileDAO.getScreenings();

		// Analyze
		assertEquals(screenings.length, testScreenings.length);
		for (int i = 0; i < testScreenings.length; ++i) {
			assertEquals(screenings[i], testScreenings[i]);
		}
	}

	@Test
	void testGetScreening () {
		// Invoke
		Screening screening = screeningFileDAO.getScreening(101);

		// Analyze
		assertEquals(screening, testScreenings[0]);
	}

	@Test
	void testDeleteScreening () {
		// Invoke
		boolean result = assertDoesNotThrow(() -> screeningFileDAO.deleteScreening(101), "Unexpected exception thrown");

		// Analyze
		assertTrue(result);
		// We check the internal tree map size against the length of the test screenings
		// array - 1 (because of the delete function call)
		// Because screenings attribute of ScreeningJSONDAO is package private we can
		// access it directly
		assertEquals(screeningFileDAO.screenings.size(), testScreenings.length - 1);
	}

	@Test
	void testCreateScreening () {
		// Setup
		boolean[][] seats = { { false, false, false, false, false }, { false, false, true, true, true },
			{ true, true, true, true, true }, { true, true, true, true, true } };
		Screening screening = new Screening(104, 104, 6, "01/18/2023", "18:00", seats);

		// Invoke
		Screening result = assertDoesNotThrow(() -> screeningFileDAO.createScreening(screening),
		                                      "Unexpected exception thrown");

		// Analyze
		assertNotNull(result);
		Screening actual = screeningFileDAO.getScreening(screening.getId());
		assertEquals(actual.getId(), screening.getId());
		assertEquals(actual.getMovieId(), screening.getMovieId());
		assertEquals(actual.getTicketsRemaining(), screening.getTicketsRemaining());
		assertEquals(actual.getDate(), screening.getDate());
		assertEquals(actual.getTime(), screening.getTime());
	}

	@Test
	void testUpdateScreening () {
		// Setup
		boolean[][] seats = { { false, false, false, false }, { false, false, true, true } };
		Screening screening = new Screening(101, 105, 6, "01/18/2023", "18:00", seats);

		// Invoke
		Screening result = assertDoesNotThrow(() -> screeningFileDAO.updateScreening(screening),
		                                      "Unexpected exception thrown");

		// Analyze
		assertNotNull(result);
		Screening actual = screeningFileDAO.getScreening(screening.getId());
		assertEquals(actual, screening);
	}

	@Test
	void testSaveException () throws IOException {
		doThrow(new IOException()).when(mockObjectMapper).writeValue(any(File.class), any(Screening[].class));

		boolean[][] seats = { { false, false, false, false }, { false, false, true, true } };
		Screening screening = new Screening(104, 104, 6, "01/18/2023", "18:00", seats);

		assertThrows(IOException.class, () -> screeningFileDAO.createScreening(screening), "IOException not thrown");
	}

	@Test
	void testGetScreeningNotFound () {
		// Invoke
		Screening screening = screeningFileDAO.getScreening(104);

		// Analyze
		assertNull(screening);
	}

	@Test
	void testScreeningsForMovie () {
		// Invoke
		Screening[] screenings = screeningFileDAO.findScreeningsForMovie(104);
		Screening[] emptyScreenings = screeningFileDAO.findScreeningsForMovie(0);

		// Analyze
		assertEquals(screenings.length, 3);
		assertEquals(screenings[0], testScreenings[0]);
		assertEquals(screenings[1], testScreenings[1]);
		assertEquals(screenings[2], testScreenings[2]);
		assertEquals(emptyScreenings.length, 0); // no screenings with movieId = 0 should be found

	}

	@Test
	void testDeleteScreeningNotFound () {
		// Invoke
		boolean result = assertDoesNotThrow(() -> screeningFileDAO.deleteScreening(104), "Unexpected exception thrown");

		// Analyze
		assertFalse(result);
		assertEquals(screeningFileDAO.screenings.size(), testScreenings.length);
	}

	@Test
	void testUpdateScreeningNotFound () {
		// Setup
		boolean[][] seats = { { false, false, false, false }, { false, false, true, true } };
		Screening screening = new Screening(104, 104, 6, "01/18/2023", "18:00", seats);

		// Invoke
		Screening result = assertDoesNotThrow(() -> screeningFileDAO.updateScreening(screening),
		                                      "Unexpected exception thrown");

		// Analyze
		assertNull(result);
	}

	@Test
	void testConstructorException () throws IOException {
		// Setup
		ObjectMapper mockObjectMapper = mock(ObjectMapper.class);
		// We want to simulate with a Mock Object Mapper that an exception was raised
		// during JSON object deserialization into Java objects
		// When the Mock Object Mapper readValue method is called from the
		// ScreeningJSONDAO load method, an IOException is raised
		doThrow(new IOException()).when(mockObjectMapper).readValue(new File("mao-zedongs-little-red-book.epub"),
		                                                            Screening[].class);

		// Invoke & Analyze
		assertThrows(IOException.class,
		             () -> new ScreeningJSONDAO("mao-zedongs-little-red-book.epub", mockObjectMapper),
		             "IOException not thrown");
	}
}
