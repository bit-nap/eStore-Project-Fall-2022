package com.estore.api.estoreapi.movies.persistence;

import com.estore.api.estoreapi.movies.model.Movie;
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
 * Test the Movie File DAO class
 *
 * @author Group 3C, The Code Monkeys
 */
@Tag("Persistence-tier")
public class MovieJSONDAOTest {
	MovieJSONDAO heroFileDAO;
	Movie[] testMovies;
	ObjectMapper mockObjectMapper;

	/**
	 * Before each test, we will create and inject a Mock Object Mapper to
	 * isolate the tests from the underlying file
	 *
	 * @throws IOException
	 */
	@BeforeEach
	public void setupMovieJSONDAO () throws IOException {
		mockObjectMapper = mock(ObjectMapper.class);
		testMovies = new Movie[3];
		testMovies[0] = new Movie(99, "Wi-Fire");
		testMovies[1] = new Movie(100, "Galactic Agent");
		testMovies[2] = new Movie(101, "Ice Gladiator");

		// When the object mapper is supposed to read from the file the mock object mapper will return the hero array above
		when(mockObjectMapper
			     .readValue(new File("doesnt_matter.txt"), Movie[].class))
			.thenReturn(testMovies);
		heroFileDAO = new MovieJSONDAO("doesnt_matter.txt", mockObjectMapper);
	}

	@Test
	public void testGetMovies () {
		// Invoke
		Movie[] movies = heroFileDAO.getMovies();

		// Analyze
		assertEquals(movies.length, testMovies.length);
		for (int i = 0; i < testMovies.length; ++i) {
			assertEquals(movies[i], testMovies[i]);
		}
	}

	@Test
	public void testFindMovies () {
		// Invoke
		Movie[] movies = heroFileDAO.findMovies("la");

		// Analyze
		assertEquals(movies.length, 2);
		assertEquals(movies[0], testMovies[1]);
		assertEquals(movies[1], testMovies[2]);
	}

	@Test
	public void testGetMovie () {
		// Invoke
		Movie hero = heroFileDAO.getMovie(99);

		// Analyze
		assertEquals(hero, testMovies[0]);
	}

	@Test
	public void testDeleteMovie () {
		// Invoke
		boolean result = assertDoesNotThrow(() -> heroFileDAO.deleteMovie(99),
		                                    "Unexpected exception thrown");

		// Analyze
		assertTrue(result);
		// We check the internal tree map size against the length of the test movies array - 1 (because of the delete function call)
		// Because movies attribute of MovieJSONDAO is package private we can access it directly
		assertEquals(heroFileDAO.movies.size(), testMovies.length - 1);
	}

	@Test
	public void testCreateMovie () {
		// Setup
		Movie hero = new Movie(102, "Wonder-Person");

		// Invoke
		Movie result = assertDoesNotThrow(() -> heroFileDAO.createMovie(hero),
		                                  "Unexpected exception thrown");

		// Analyze
		assertNotNull(result);
		Movie actual = heroFileDAO.getMovie(hero.getId());
		assertEquals(actual.getId(), hero.getId());
		assertEquals(actual.getMovie(), hero.getMovie());
	}

	@Test
	public void testUpdateMovie () {
		// Setup
		Movie hero = new Movie(99, "Galactic Agent");

		// Invoke
		Movie result = assertDoesNotThrow(() -> heroFileDAO.updateMovie(hero),
		                                  "Unexpected exception thrown");

		// Analyze
		assertNotNull(result);
		Movie actual = heroFileDAO.getMovie(hero.getId());
		assertEquals(actual, hero);
	}

	@Test
	public void testSaveException () throws IOException {
		doThrow(new IOException())
			.when(mockObjectMapper)
			.writeValue(any(File.class), any(Movie[].class));

		Movie hero = new Movie(102, "Wi-Fire");

		assertThrows(IOException.class,
		             () -> heroFileDAO.createMovie(hero),
		             "IOException not thrown");
	}

	@Test
	public void testGetMovieNotFound () {
		// Invoke
		Movie hero = heroFileDAO.getMovie(98);

		// Analyze
		assertNull(hero);
	}

	@Test
	public void testDeleteMovieNotFound () {
		// Invoke
		boolean result = assertDoesNotThrow(() -> heroFileDAO.deleteMovie(98),
		                                    "Unexpected exception thrown");

		// Analyze
		assertFalse(result);
		assertEquals(heroFileDAO.movies.size(), testMovies.length);
	}

	@Test
	public void testUpdateMovieNotFound () {
		// Setup
		Movie hero = new Movie(98, "Bolt");

		// Invoke
		Movie result = assertDoesNotThrow(() -> heroFileDAO.updateMovie(hero),
		                                  "Unexpected exception thrown");

		// Analyze
		assertNull(result);
	}

	@Test
	public void testConstructorException () throws IOException {
		// Setup
		ObjectMapper mockObjectMapper = mock(ObjectMapper.class);
		// We want to simulate with a Mock Object Mapper that an exception was raised during JSON object deserialization into Java objects
		// When the Mock Object Mapper readValue method is called from the MovieJSONDAO load method, an IOException is raised
		doThrow(new IOException())
			.when(mockObjectMapper)
			.readValue(new File("doesnt_matter.txt"), Movie[].class);

		// Invoke & Analyze
		assertThrows(IOException.class,
		             () -> new MovieJSONDAO("doesnt_matter.txt", mockObjectMapper),
		             "IOException not thrown");
	}
}
