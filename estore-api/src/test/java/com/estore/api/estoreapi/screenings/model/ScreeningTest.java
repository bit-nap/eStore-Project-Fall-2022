package com.estore.api.estoreapi.screenings.model;

import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * The unit test suite for the Screening class
 *
 * @author Group 3C, The Code Monkeys
 */
@Tag("Model-tier")
class ScreeningTest {
	@Test
	void testCtor () {
		// Setup
		int id = 99;
		int movieId = 104;
		int tickets = 10;
		String date = "01/17/2023";
		String time = "18:00";
		boolean[][] seats = { { false, false, false, false, false }, { false, false, true, true, true },
			{ true, true, true, true, true }, { true, true, true, true, true } };

		// Invoke
		Screening screening = new Screening(id, movieId, tickets, date, time, seats);

		// Analyze
		assertEquals(id, screening.getId());
		assertEquals(movieId, screening.getMovieId());
		assertEquals(tickets, screening.getTicketsRemaining());
		assertEquals(date, screening.getDate());
		assertEquals(time, screening.getTime());
	}

	@Test
	void testUpdateMovieId () {
		// Setup
		int id = 99;
		int movieId = 104;
		int tickets = 10;
		String date = "01/17/2023";
		String time = "18:00";
		boolean[][] seats = { { false, false, false, false, false }, { false, false, true, true, true },
			{ true, true, true, true, true }, { true, true, true, true, true } };
		Screening screening = new Screening(id, movieId, tickets, date, time, seats);

		// Invoke
		int newMovieId = 105;
		screening.setMovieId(newMovieId);

		// Analyze
		assertEquals(newMovieId, screening.getMovieId());
	}

	@Test
	void testMovieIdIs () {
		boolean[][] seats = { { false, false, false, false, false }, { false, false, true, true, true },
			{ true, true, true, true, true }, { true, true, true, true, true } };
		Screening screening = new Screening(99, 104, 10, "01/17/2023", "18:00", seats);
		assertTrue(screening.movieIdIs(104));
		assertFalse(screening.movieIdIs(99));
	}

	@Test
	void testCompareTo () {
		boolean[][] seats = { { false, false, false, false }, { false, false, false, false } };
		Screening o1 = new Screening(101, 104, 6, "01/16/2023", "16:00", seats);
		Screening o2 = new Screening(103, 104, 8, "01/17/2023", "16:00", seats);
		Screening o3 = new Screening(101, 104, 6, "01/17/2023", "18:00", seats);
		Screening o4 = new Screening(102, 104, 0, "01/17/2023", "20:00", seats);
		Screening o5 = new Screening(102, 104, 0, "01/18/2023", "16:00", seats);
		Screening o6 = new Screening(103, 104, 8, "01/20/2023", "16:00", seats);
		Screening o7 = new Screening(103, 104, 8, "01/20/2023", "16:00", seats);
		List<Screening> sortedList = new ArrayList<>();  // arraylist with manually sorted Screenings
		sortedList.add(o1);
		sortedList.add(o2);
		sortedList.add(o3);
		sortedList.add(o4);
		sortedList.add(o5);
		sortedList.add(o6);
		sortedList.add(o7);
		List<Screening> screeningList = new ArrayList<>();  // arraylist to sort
		screeningList.add(o6);
		screeningList.add(o2);
		screeningList.add(o1);
		screeningList.add(o4);
		screeningList.add(o7);
		screeningList.add(o5);
		screeningList.add(o3);
		Collections.sort(screeningList);  // sort using the Screening#compareTo method
		for (int i = 0; i < screeningList.size(); i++) {
			assertEquals(sortedList.get(i), screeningList.get(i));
		}
	}

	@Test
	void testEquals () {
		boolean[][] seats = { { false, false, false, false }, { false, false, false, false } };
		Screening original = new Screening(101, 104, 6, "01/16/2023", "16:00", seats);

		assertEquals(original, original);
		assertNotEquals(original, null);
		assertNotEquals(original, "not a Screening object");
		Screening diffTime = new Screening(102, 104, 8, "01/16/2023", "18:00", seats);
		Screening diffDate = new Screening(103, 104, 8, "01/17/2023", "18:00", seats);
		Screening sameDateAndTime = new Screening(104, 104, 8, "01/16/2023", "16:00", seats);
		assertNotEquals(original, diffTime);
		assertNotEquals(original, diffDate);
		assertEquals(original, sameDateAndTime);
	}

	@Test
	void testToString () {
		// Setup
		int id = 99;
		int movieId = 104;
		int tickets = 10;
		String date = "01/17/2023";
		String time = "18:00";
		boolean[][] seats = { { false, false, false, false }, { false, false, false, false } };
		Screening screening = new Screening(id, movieId, tickets, date, time, seats);
		String expected_string = String.format(Screening.STRING_FORMAT, id, movieId, tickets, date, time);

		// Invoke
		String actual_string = screening.toString();

		// Analyze
		assertEquals(expected_string, actual_string);
	}
}
