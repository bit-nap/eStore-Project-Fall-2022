package com.estore.api.estoreapi.screenings.model;

import com.estore.api.estoreapi.movies.MovieGetter;
import com.estore.api.estoreapi.movies.model.Movie;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * The unit test suite for the Screening class
 *
 * @author Group 3C, The Code Monkeys
 */
@Tag("Model-tier")
public class ScreeningTest {
	private MovieGetter mockMovieGetter;
	private Movie testMovie;

	/**
	 * Before each test, we will create and inject a Mock Object Mapper to
	 * isolate the tests from the underlying file
	 */
	@BeforeEach
	public void setupScreeningJSONDAO () {
		mockMovieGetter = mock(MovieGetter.class);
		testMovie = new Movie(104, "Star Wars: Episode IV – A New Hope", "death/star/plans.jpg", 105, "PG", 1977);
		when(mockMovieGetter.getMovie(104)).thenReturn(testMovie);
	}

	@Test
	public void testCtor () {
		// Setup
		int id = 99;
		int movieId = 104;
		int tickets = 10;
		LocalDate date = LocalDate.parse("2023-01-17");
		LocalTime time = LocalTime.parse("18:00");

		// Invoke
		Screening screening = new Screening(id, movieId, tickets, date, time, mockMovieGetter);

		// Analyze
		assertEquals(id, screening.getId());
		assertEquals(movieId, screening.getMovieId());
		assertEquals(testMovie, screening.getMovie());
		assertEquals(tickets, screening.getTicketsRemaining());
		assertEquals(date, screening.getDate());
		assertEquals(time, screening.getTime());
	}

	@Test
	public void testUpdateMovieId () {
		// Setup
		int id = 99;
		int movieId = 104;
		int tickets = 10;
		LocalDate date = LocalDate.parse("2023-01-17");
		LocalTime time = LocalTime.parse("18:00");
		Screening screening = new Screening(id, movieId, tickets, date, time, mockMovieGetter);

		// Invoke
		int newMovieId = 105;
		Movie newMovie = new Movie(newMovieId, "Star Wars: Episode V – The Empire Strikes Back", "death/star/2/plans.jpg", 124, "PG", 1980);
		when(mockMovieGetter.getMovie(newMovieId)).thenReturn(newMovie);
		screening.setMovieId(newMovieId);

		// Analyze
		assertEquals(newMovieId, screening.getMovieId());
		assertEquals(newMovie, screening.getMovie());
	}

	@Test
	public void testMovieIdIs () {
		Screening screening = new Screening(99, 104, 10, LocalDate.parse("2023-01-17"), LocalTime.parse("18:00"), mockMovieGetter);
		assertTrue(screening.movieIdIs(104));
		assertFalse(screening.movieIdIs(99));
	}

	@Test
	public void testCompareTo () {
		Screening o1 = new Screening(101, 104, 6, LocalDate.parse("2023-01-16"), LocalTime.parse("16:00"), mockMovieGetter);
		Screening o2 = new Screening(103, 104, 8, LocalDate.parse("2023-01-17"), LocalTime.parse("16:00"), mockMovieGetter);
		Screening o3 = new Screening(101, 104, 6, LocalDate.parse("2023-01-17"), LocalTime.parse("18:00"), mockMovieGetter);
		Screening o4 = new Screening(102, 104, 0, LocalDate.parse("2023-01-18"), LocalTime.parse("16:00"), mockMovieGetter);
		Screening o5 = new Screening(103, 104, 8, LocalDate.parse("2023-01-20"), LocalTime.parse("16:00"), mockMovieGetter);
		Screening o6 = new Screening(103, 104, 8, LocalDate.parse("2023-01-20"), LocalTime.parse("16:00"), mockMovieGetter);
		Screening o7 = new Screening(102, 104, 0, LocalDate.parse("2024-01-17"), LocalTime.parse("20:00"), mockMovieGetter);
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
	public void testToString () {
		// Setup
		int id = 99;
		int movieId = 104;
		int tickets = 10;
		LocalDate date = LocalDate.parse("2023-01-17");
		LocalTime time = LocalTime.parse("18:00");
		Screening screening = new Screening(id, movieId, tickets, date, time, mockMovieGetter);
		String expected_string = String.format(Screening.STRING_FORMAT, id, movieId, tickets, date, time);

		// Invoke
		String actual_string = screening.toString();

		// Analyze
		assertEquals(expected_string, actual_string);
	}
}
