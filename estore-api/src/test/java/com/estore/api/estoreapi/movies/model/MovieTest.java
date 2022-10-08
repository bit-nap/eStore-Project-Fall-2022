package com.estore.api.estoreapi.movies.model;

import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * The unit test suite for the Movie class
 *
 * @author Group 3C, The Code Monkeys
 */
@Tag("Model-tier")
public class MovieTest {
	@Test
	public void testCtor () {
		// Setup
		int expected_id = 99;
		String expected_name = "Star Wars";

		// Invoke
		Movie movie = new Movie(expected_id, expected_name);

		// Analyze
		assertEquals(expected_id, movie.getId());
		assertEquals(expected_name, movie.getMovie());
	}

	@Test
	public void testName () {
		// Setup
		int id = 99;
		String name = "Star Wars IV";
		Movie movie = new Movie(id, name);

		String expected_name = "Star Wars V";

		// Invoke
		movie.setMovie(expected_name);

		// Analyze
		assertEquals(expected_name, movie.getMovie());
	}

	@Test
	public void testToString () {
		// Setup
		int id = 99;
		String name = "Star Wars";
		String expected_string = String.format(Movie.STRING_FORMAT, id, name);
		Movie movie = new Movie(id, name);

		// Invoke
		String actual_string = movie.toString();

		// Analyze
		assertEquals(expected_string, actual_string);
	}
}
