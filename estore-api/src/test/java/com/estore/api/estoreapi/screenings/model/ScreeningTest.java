package com.estore.api.estoreapi.screenings.model;

import com.estore.api.estoreapi.movies.MovieGetter;
import com.estore.api.estoreapi.movies.model.Movie;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalTime;

import static org.junit.jupiter.api.Assertions.assertEquals;
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
	 *
	 * @throws IOException if screeningFileDAO cannot read from fake file
	 */
	@BeforeEach
	public void setupScreeningJSONDAO () throws IOException {
		mockMovieGetter = mock(MovieGetter.class);
		testMovie = new Movie(104, "Star Wars: Episode IV – A New Hope", "1:45", "PG", 1977);
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
		Screening screening = new Screening(mockMovieGetter, id, movieId, tickets, date, time);

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
		Screening screening = new Screening(mockMovieGetter, id, movieId, tickets, date, time);

		// Invoke
		int newMovieId = 105;
		Movie newMovie = new Movie(newMovieId, "Star Wars: Episode V – The Empire Strikes Back", "2:04", "PG", 1980);
		when(mockMovieGetter.getMovie(newMovieId)).thenReturn(newMovie);
		screening.setMovieId(newMovieId);

		// Analyze
		assertEquals(newMovieId, screening.getMovieId());
		assertEquals(newMovie, screening.getMovie());
	}

	@Test
	public void testToString () {
		// Setup
		int id = 99;
		int movieId = 104;
		int tickets = 10;
		LocalDate date = LocalDate.parse("2023-01-17");
		LocalTime time = LocalTime.parse("18:00");
		Screening screening = new Screening(mockMovieGetter, id, movieId, tickets, date, time);
		String expected_string = String.format(Screening.STRING_FORMAT, id, movieId, tickets, date, time);

		// Invoke
		String actual_string = screening.toString();

		// Analyze
		assertEquals(expected_string, actual_string);
	}
}
