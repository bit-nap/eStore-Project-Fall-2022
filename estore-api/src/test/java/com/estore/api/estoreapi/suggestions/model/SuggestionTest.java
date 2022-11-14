package com.estore.api.estoreapi.suggestions.model;

import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * The unit test suite for the Suggestion class
 *
 * @author Group 3C, The Code Monkeys
 */
@Tag("Model-tier")
public class SuggestionTest {
	@Test
	public void testCtor () {
		// Setup
		int id = 99;
		String movieTitle = "Star Wars: Episode IV – A New Hope";
		int votes = 77;

		// Invoke
		Suggestion suggestion = new Suggestion(id, movieTitle, votes);

		// Analyze
		assertEquals(id, suggestion.getId());
		assertEquals(movieTitle, suggestion.getMovieTitle());
		assertEquals(votes, suggestion.getVotes());
	}

	@Test
	public void testToString () {
		// Setup
		// Setup
		int id = 99;
		String movieTitle = "Star Wars: Episode IV – A New Hope";
		int votes = 77;

		// Invoke
		String expected_string = String.format(Suggestion.STRING_FORMAT, id, movieTitle, votes);

		// Invoke
		Suggestion suggestion = new Suggestion(id, movieTitle, votes);
		String actual_string = suggestion.toString();

		// Analyze
		assertEquals(expected_string, actual_string);
	}
}
