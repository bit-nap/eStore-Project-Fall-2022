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
	MovieJSONDAO movieFileDAO;
	Movie[] testMovies;
	ObjectMapper mockObjectMapper;

	/**
	 * Before each test, we will create and inject a Mock Object Mapper to
	 * isolate the tests from the underlying file
	 *
	 * @throws IOException if movieFileDAO cannot read from fake file
	 */
	@BeforeEach
	public void setupMovieJSONDAO () throws IOException {
		mockObjectMapper = mock(ObjectMapper.class);
		testMovies = new Movie[3];
		testMovies[0] = new Movie(104, "Star Wars: Episode IV – A New Hope", "1:45", "PG", 1977);
		testMovies[1] = new Movie(105, "Star Wars: Episode V – The Empire Strikes Back", "2:04", "PG", 1980);
		testMovies[2] = new Movie(106, "Star Wars: Episode VI - Return of the Jedi", "2:11", "PG", 1983);

		// When the object mapper is supposed to read from the file the mock object mapper will return the movie array above
		when(mockObjectMapper.readValue(new File("mao-zedongs-little-red-book.epub"), Movie[].class)).thenReturn(testMovies);
		movieFileDAO = new MovieJSONDAO("mao-zedongs-little-red-book.epub", mockObjectMapper);
	}

	@Test
	public void testGetMovies () {
		// Invoke
		Movie[] movies = movieFileDAO.getMovies();

		// Analyze
		assertEquals(movies.length, testMovies.length);
		for (int i = 0; i < testMovies.length; ++i) {
			assertEquals(movies[i], testMovies[i]);
		}
	}

	@Test
	public void testFindMovies () {
		// Invoke
		Movie[] movies = movieFileDAO.findMovies("Star Wars");

		// Analyze
		assertEquals(movies.length, 3);
		assertEquals(movies[0], testMovies[0]);
		assertEquals(movies[1], testMovies[1]);
		assertEquals(movies[2], testMovies[2]);
	}

	@Test
	public void testGetMovie () {
		// Invoke
		Movie movie = movieFileDAO.getMovie(104);

		// Analyze
		assertEquals(movie, testMovies[0]);
	}

	@Test
	public void testDeleteMovie () {
		// Invoke
		boolean result = assertDoesNotThrow(() -> movieFileDAO.deleteMovie(104), "Unexpected exception thrown");

		// Analyze
		assertTrue(result);
		// We check the internal tree map size against the length of the test movies array - 1 (because of the delete function call)
		// Because movies attribute of MovieJSONDAO is package private we can access it directly
		assertEquals(movieFileDAO.movies.size(), testMovies.length - 1);
	}

	@Test
	public void testCreateMovie () {
		// Setup
		Movie movie = new Movie(107, "Star Wars: The Force Awakens", "2:16", "PG-13", 2015);

		// Invoke
		Movie result = assertDoesNotThrow(() -> movieFileDAO.createMovie(movie), "Unexpected exception thrown");

		// Analyze
		assertNotNull(result);
		Movie actual = movieFileDAO.getMovie(movie.getId());
		assertEquals(actual.getId(), movie.getId());
		assertEquals(actual.getTitle(), movie.getTitle());
		assertEquals(actual.getRuntime(), movie.getRuntime());
		assertEquals(actual.getYear(), movie.getYear());
	}

	@Test
	public void testUpdateMovie () {
		// Setup
		Movie movie = new Movie(104, "Star Wars: The Force Awakens", "2:16", "PG-13", 2015);

		// Invoke
		Movie result = assertDoesNotThrow(() -> movieFileDAO.updateMovie(movie), "Unexpected exception thrown");

		// Analyze
		assertNotNull(result);
		Movie actual = movieFileDAO.getMovie(movie.getId());
		assertEquals(actual, movie);
	}

	@Test
	public void testSaveException () throws IOException {
		doThrow(new IOException()).when(mockObjectMapper).writeValue(any(File.class), any(Movie[].class));

		Movie movie = new Movie(107, "Star Wars: The Force Awakens", "2:16", "PG-13", 2015);

		assertThrows(IOException.class, () -> movieFileDAO.createMovie(movie), "IOException not thrown");
	}

	@Test
	public void testGetMovieNotFound () {
		// Invoke
		Movie movie = movieFileDAO.getMovie(103);

		// Analyze
		assertNull(movie);
	}

	@Test
	public void testDeleteMovieNotFound () {
		// Invoke
		boolean result = assertDoesNotThrow(() -> movieFileDAO.deleteMovie(103), "Unexpected exception thrown");

		// Analyze
		assertFalse(result);
		assertEquals(movieFileDAO.movies.size(), testMovies.length);
	}

	@Test
	public void testUpdateMovieNotFound () {
		// Setup
		Movie movie = new Movie(103, "Star Wars: The Force Awakens", "2:16", "PG-13", 2015);

		// Invoke
		Movie result = assertDoesNotThrow(() -> movieFileDAO.updateMovie(movie), "Unexpected exception thrown");

		// Analyze
		assertNull(result);
	}

	@Test
	public void testConstructorException () throws IOException {
		// Setup
		ObjectMapper mockObjectMapper = mock(ObjectMapper.class);
		// We want to simulate with a Mock Object Mapper that an exception was raised during JSON object deserialization into Java objects
		// When the Mock Object Mapper readValue method is called from the MovieJSONDAO load method, an IOException is raised
		doThrow(new IOException()).when(mockObjectMapper).readValue(new File("mao-zedongs-little-red-book.epub"), Movie[].class);

		// Invoke & Analyze
		assertThrows(IOException.class, () -> new MovieJSONDAO("mao-zedongs-little-red-book.epub", mockObjectMapper), "IOException not thrown");
	}
}
