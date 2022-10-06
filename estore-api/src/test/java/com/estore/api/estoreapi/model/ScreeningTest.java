package com.estore.api.estoreapi.model;

import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * The unit test suite for the Screening class
 *
 * @author SWEN Faculty
 */
@Tag("Model-tier")
public class ScreeningTest {
	@Test
	public void testCtor () {
		// Setup
		int expected_id = 99;
		String expected_name = "Star Wars";

		// Invoke
		Screening screening = new Screening(expected_id, expected_name);

		// Analyze
		assertEquals(expected_id, screening.getId());
		assertEquals(expected_name, screening.getMovie());
	}

	@Test
	public void testName () {
		// Setup
		int id = 99;
		String name = "Star Wars IV";
		Screening screening = new Screening(id, name);

		String expected_name = "Star Wars V";

		// Invoke
		screening.setMovie(expected_name);

		// Analyze
		assertEquals(expected_name, screening.getMovie());
	}

	@Test
	public void testToString () {
		// Setup
		int id = 99;
		String name = "Star Wars";
		String expected_string = String.format(Screening.STRING_FORMAT, id, name);
		Screening screening = new Screening(id, name);

		// Invoke
		String actual_string = screening.toString();

		// Analyze
		assertEquals(expected_string, actual_string);
	}
}
