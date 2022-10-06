package com.estore.api.estoreapi.persistence;

import com.estore.api.estoreapi.model.Screening;
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
 * @author SWEN Faculty
 */
@Tag("Persistence-tier")
public class ScreeningJSONDAOTest {
	ScreeningJSONDAO heroFileDAO;
	Screening[] testScreenings;
	ObjectMapper mockObjectMapper;

	/**
	 * Before each test, we will create and inject a Mock Object Mapper to
	 * isolate the tests from the underlying file
	 *
	 * @throws IOException
	 */
	@BeforeEach
	public void setupScreeningJSONDAO () throws IOException {
		mockObjectMapper = mock(ObjectMapper.class);
		testScreenings = new Screening[3];
		testScreenings[0] = new Screening(99, "Wi-Fire");
		testScreenings[1] = new Screening(100, "Galactic Agent");
		testScreenings[2] = new Screening(101, "Ice Gladiator");

		// When the object mapper is supposed to read from the file the mock object mapper will return the hero array above
		when(mockObjectMapper
			     .readValue(new File("doesnt_matter.txt"), Screening[].class))
			.thenReturn(testScreenings);
		heroFileDAO = new ScreeningJSONDAO("doesnt_matter.txt", mockObjectMapper);
	}

	@Test
	public void testGetScreenings () {
		// Invoke
		Screening[] screenings = heroFileDAO.getScreenings();

		// Analyze
		assertEquals(screenings.length, testScreenings.length);
		for (int i = 0; i < testScreenings.length; ++i) {
			assertEquals(screenings[i], testScreenings[i]);
		}
	}

	@Test
	public void testFindScreenings () {
		// Invoke
		Screening[] screenings = heroFileDAO.findScreenings("la");

		// Analyze
		assertEquals(screenings.length, 2);
		assertEquals(screenings[0], testScreenings[1]);
		assertEquals(screenings[1], testScreenings[2]);
	}

	@Test
	public void testGetScreening () {
		// Invoke
		Screening hero = heroFileDAO.getScreening(99);

		// Analyze
		assertEquals(hero, testScreenings[0]);
	}

	@Test
	public void testDeleteScreening () {
		// Invoke
		boolean result = assertDoesNotThrow(() -> heroFileDAO.deleteScreening(99),
		                                    "Unexpected exception thrown");

		// Analyze
		assertTrue(result);
		// We check the internal tree map size against the length of the test screenings array - 1 (because of the delete function call)
		// Because screenings attribute of ScreeningJSONDAO is package private we can access it directly
		assertEquals(heroFileDAO.screenings.size(), testScreenings.length - 1);
	}

	@Test
	public void testCreateScreening () {
		// Setup
		Screening hero = new Screening(102, "Wonder-Person");

		// Invoke
		Screening result = assertDoesNotThrow(() -> heroFileDAO.createScreening(hero),
		                                      "Unexpected exception thrown");

		// Analyze
		assertNotNull(result);
		Screening actual = heroFileDAO.getScreening(hero.getId());
		assertEquals(actual.getId(), hero.getId());
		assertEquals(actual.getMovie(), hero.getMovie());
	}

	@Test
	public void testUpdateScreening () {
		// Setup
		Screening hero = new Screening(99, "Galactic Agent");

		// Invoke
		Screening result = assertDoesNotThrow(() -> heroFileDAO.updateScreening(hero),
		                                      "Unexpected exception thrown");

		// Analyze
		assertNotNull(result);
		Screening actual = heroFileDAO.getScreening(hero.getId());
		assertEquals(actual, hero);
	}

	@Test
	public void testSaveException () throws IOException {
		doThrow(new IOException())
			.when(mockObjectMapper)
			.writeValue(any(File.class), any(Screening[].class));

		Screening hero = new Screening(102, "Wi-Fire");

		assertThrows(IOException.class,
		             () -> heroFileDAO.createScreening(hero),
		             "IOException not thrown");
	}

	@Test
	public void testGetScreeningNotFound () {
		// Invoke
		Screening hero = heroFileDAO.getScreening(98);

		// Analyze
		assertNull(hero);
	}

	@Test
	public void testDeleteScreeningNotFound () {
		// Invoke
		boolean result = assertDoesNotThrow(() -> heroFileDAO.deleteScreening(98),
		                                    "Unexpected exception thrown");

		// Analyze
		assertFalse(result);
		assertEquals(heroFileDAO.screenings.size(), testScreenings.length);
	}

	@Test
	public void testUpdateScreeningNotFound () {
		// Setup
		Screening hero = new Screening(98, "Bolt");

		// Invoke
		Screening result = assertDoesNotThrow(() -> heroFileDAO.updateScreening(hero),
		                                      "Unexpected exception thrown");

		// Analyze
		assertNull(result);
	}

	@Test
	public void testConstructorException () throws IOException {
		// Setup
		ObjectMapper mockObjectMapper = mock(ObjectMapper.class);
		// We want to simulate with a Mock Object Mapper that an exception was raised during JSON object deserialization into Java objects
		// When the Mock Object Mapper readValue method is called from the ScreeningJSONDAO load method, an IOException is raised
		doThrow(new IOException())
			.when(mockObjectMapper)
			.readValue(new File("doesnt_matter.txt"), Screening[].class);

		// Invoke & Analyze
		assertThrows(IOException.class,
		             () -> new ScreeningJSONDAO("doesnt_matter.txt", mockObjectMapper),
		             "IOException not thrown");
	}
}
