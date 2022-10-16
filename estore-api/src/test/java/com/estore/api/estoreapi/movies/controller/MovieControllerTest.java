package com.estore.api.estoreapi.movies.controller;

import com.estore.api.estoreapi.movies.model.Movie;
import com.estore.api.estoreapi.movies.persistence.MovieDAO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

/**
 * Test the MovieController class.
 *
 * @author Group 3C, The Code Monkeys
 */
@Tag("Controller-Tier")
public class MovieControllerTest {
	private MovieController movieController;
	private MovieDAO mockMovieDao;

	/**
	 * Before a test, create a new MovieController object and inject a mock Movie DAO.
	 */
	@BeforeEach
	public void setupMovieController () {
		mockMovieDao = mock(MovieDAO.class);
		movieController = new MovieController(mockMovieDao);
	}

	@Test
	public void testGetMovie () throws IOException {
		// setup
		Movie movie = new Movie(104, "Star Wars: Episode IV – A New Hope", "death/star/plans.jpg", 105, "PG", 1977);
		// when the same id is passed in, our mock movie DAO will return the Movie object
		when(mockMovieDao.getMovie(movie.getId())).thenReturn(movie);

		// invoke
		ResponseEntity<Movie> response = movieController.getMovie(movie.getId());

		// analyze
		assertEquals(HttpStatus.OK, response.getStatusCode());
		assertEquals(movie, response.getBody());
	}

	@Test
	public void testGetMovieNotFound () throws Exception {
		// setup
		int movieId = 99;
		// when the same id is passed in, our mock movie DAO will return null, simulating no movie found
		when(mockMovieDao.getMovie(movieId)).thenReturn(null);

		// invoke
		ResponseEntity<Movie> response = movieController.getMovie(movieId);

		// analyze
		assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
	}

	@Test
	public void testGetMovieHandleException () throws Exception {
		// setup
		int movieId = 99;
		// when getMovie is called on the mock movie DAO, throw an IOException
		doThrow(new IOException()).when(mockMovieDao).getMovie(movieId);

		// invoke
		ResponseEntity<Movie> response = movieController.getMovie(movieId);

		// analyze
		assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
	}

	/**
	 * Method to test if getting all of the movies works
	 *
	 * @throws Exception if something goes wrong with the http request
	 */
	@Test
	public void testGetMovies () throws Exception {
		// New list of movies
		Movie[] movies = new Movie[3];
		movies[0] = new Movie(104, "Star Wars: Episode IV – A New Hope", "death/star/plans.jpg", 105, "PG", 1977);
		movies[1] = new Movie(105, "Star Wars: Episode V – The Empire Strikes Back", "death/star/2/plans.jpg", 124, "PG", 1980);
		movies[2] = new Movie(106, "Star Wars: Episode VI - Return of the Jedi", "death/star/3/plans.jpg", 131, "PG", 1983);
		// When getMovies is called, return the list of movies from above
		when(mockMovieDao.getMovies()).thenReturn(movies);

		ResponseEntity<Movie[]> response = movieController.getMovies();

		assertEquals(HttpStatus.OK, response.getStatusCode());
		assertEquals(movies, response.getBody());
	}

	/**
	 * Test to make sure the exception is handled when getMovies throws one
	 *
	 * @throws Exception if something goes wrong with Http request
	 */
	@Test
	public void testGetMoviesHandleException () throws Exception {
		// Throw an exception when the get movies method is called
		doThrow(new IOException()).when(mockMovieDao).getMovies();

		ResponseEntity<Movie[]> response = movieController.getMovies();

		assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
	}

	@Test
	public void testCreateMovie () throws IOException {
		// setup
		Movie movie = new Movie(104, "Star Wars: Episode IV – A New Hope", "death/star/plans.jpg", 105, "PG", 1977);
		// when createMovie is called, return true simulating successful creation and save
		when(mockMovieDao.createMovie(movie)).thenReturn(movie);

		// invoke
		ResponseEntity<Movie> response = movieController.createMovie(movie);

		// analyze
		assertEquals(HttpStatus.CREATED, response.getStatusCode());
		assertEquals(movie, response.getBody());
	}

	@Test
	public void testCreateMovieFailed () throws IOException {
		// setup
		Movie movie = new Movie(104, "Star Wars: Episode IV – A New Hope", "death/star/plans.jpg", 105, "PG", 1977);
		// when createMovie is called, return false simulating failed creation and save
		when(mockMovieDao.createMovie(movie)).thenReturn(null);

		// invoke
		ResponseEntity<Movie> response = movieController.createMovie(movie);

		// analyze
		assertEquals(HttpStatus.CONFLICT, response.getStatusCode());
	}

	@Test
	public void testCreateMovieHandleException () throws IOException {
		// setup
		Movie movie = new Movie(104, "Star Wars: Episode IV – A New Hope", "death/star/plans.jpg", 105, "PG", 1977);

		// when createMovie is called, throw an IOException
		doThrow(new IOException()).when(mockMovieDao).createMovie(movie);

		// invoke
		ResponseEntity<Movie> response = movieController.createMovie(movie);

		// analyze
		assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
	}

	@Test
	public void testSearchMovies () throws IOException {
		// Setup
		String searchString = "Star Wars";
		Movie[] foundMovies = new Movie[3];
		foundMovies[0] = new Movie(104, "Star Wars: Episode IV – A New Hope", "death/star/plans.jpg", 105, "PG", 1977);
		foundMovies[1] = new Movie(105, "Star Wars: Episode V – The Empire Strikes Back", "death/star/2/plans.jpg", 124, "PG", 1980);
		foundMovies[2] = new Movie(106, "Star Wars: Episode VI - Return of the Jedi", "death/star/3/plans.jpg", 131, "PG", 1983);

		// When findMovies is called with the search string, return the three movies above
		when(mockMovieDao.findMovies(searchString)).thenReturn(foundMovies);

		// Invoke
		ResponseEntity<Movie[]> response = movieController.searchMovies(searchString);

		// Analyze
		assertEquals(HttpStatus.OK, response.getStatusCode());
		assertEquals(foundMovies, response.getBody());
	}

	@Test
	public void testSearchMoviesHandleException () throws IOException {
		// Setup
		String searchString = "an";
		// When createMovie is called on the Mock Movie DAO, throw an IOException
		doThrow(new IOException()).when(mockMovieDao).findMovies(searchString);

		// Invoke
		ResponseEntity<Movie[]> response = movieController.searchMovies(searchString);

		// Analyze
		assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
	}

	@Test
	public void testUpdateMovie () throws IOException {
		// Setup
		Movie movie = new Movie(104, "Star Wars: Episode IV – A New Hope", "death/star/plans.jpg", 105, "PG", 1977);
		// when updateMovie is called, return true simulating successful update and save
		when(mockMovieDao.updateMovie(movie)).thenReturn(movie);

		// Invoke
		ResponseEntity<Movie> response = movieController.updateMovie(movie);

		// Analyze
		assertEquals(HttpStatus.OK, response.getStatusCode());
		assertEquals(movie, response.getBody());
	}

	@Test
	public void testUpdateMovieExceptionNotFound () throws IOException {
		// Setup
		Movie movie = new Movie(104, "Star Wars: Episode IV – A New Hope", "death/star/plans.jpg", 105, "PG", 1977);
		// when updateMovie is called, return true simulating successful update and save
		when(mockMovieDao.updateMovie(movie)).thenReturn(null);

		// Invoke
		ResponseEntity<Movie> response = movieController.updateMovie(movie);

		// Analyze
		assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
	}

	@Test
	public void testUpdateMovieHandleException () throws IOException {
		// Setup
		Movie movie = new Movie(104, "Star Wars: Episode IV – A New Hope", "death/star/plans.jpg", 105, "PG", 1977);
		// When updateMovie is called on the Mock Movie DAO, throw an IOException
		doThrow(new IOException()).when(mockMovieDao).updateMovie(movie);

		// Invoke
		ResponseEntity<Movie> response = movieController.updateMovie(movie);

		// Analyze
		assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
	}

	@Test
	public void testDeleteMovie () throws IOException { // deleteMovie may throw IOException
		// Setup
		int movieId = 99;
		// when deleteMovie is called return true, simulating successful deletion
		when(mockMovieDao.deleteMovie(movieId)).thenReturn(true);

		// Invoke
		ResponseEntity<Movie> response = movieController.deleteMovie(movieId);

		// Analyze
		assertEquals(HttpStatus.OK, response.getStatusCode());
	}

	@Test
	public void testDeleteMovieNotFound () throws IOException { // deleteMovie may throw IOException
		// Setup
		int movieId = 99;
		// when deleteMovie is called return false, simulating failed deletion
		when(mockMovieDao.deleteMovie(movieId)).thenReturn(false);

		// Invoke
		ResponseEntity<Movie> response = movieController.deleteMovie(movieId);

		// Analyze
		assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
	}

	@Test
	public void testDeleteMovieHandleException () throws IOException { // deleteMovie may throw IOException
		// Setup
		int movieId = 99;
		// When deleteMovie is called on the Mock Movie DAO, throw an IOException
		doThrow(new IOException()).when(mockMovieDao).deleteMovie(movieId);

		// Invoke
		ResponseEntity<Movie> response = movieController.deleteMovie(movieId);

		// Analyze
		assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
	}
}
