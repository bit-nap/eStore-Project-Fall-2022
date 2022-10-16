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
		int id = 99;
		String title = "Star Wars: Episode IV – A New Hope";
		String poster = "death/star/plans.jpg";
		int runtime = 105;
		String mpaRating = "PG";
		int year = 1977;

		// Invoke
		Movie movie = new Movie(id, title, poster, runtime, mpaRating, year);

		// Analyze
		assertEquals(id, movie.getId());
		assertEquals(title, movie.getTitle());
		assertEquals(poster, movie.getPoster());
		assertEquals(runtime, movie.getRuntime());
		assertEquals(mpaRating, movie.getMpaRating());
		assertEquals(year, movie.getYear());
	}

	@Test
	public void testToString () {
		// Setup
		int id = 99;
		String title = "Star Wars: Episode IV – A New Hope";
		String poster = "death/star/plans.jpg";
		int runtime = 105;
		String mpaRating = "PG";
		int year = 1977;
		String expected_string = String.format(Movie.STRING_FORMAT, id, title, runtime, mpaRating, year);

		// Invoke
		Movie movie = new Movie(id, title, poster, runtime, mpaRating, year);
		String actual_string = movie.toString();

		// Analyze
		assertEquals(expected_string, actual_string);
	}
}
