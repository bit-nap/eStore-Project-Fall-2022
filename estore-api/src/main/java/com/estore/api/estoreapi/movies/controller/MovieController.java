package com.estore.api.estoreapi.movies.controller;

import com.estore.api.estoreapi.movies.model.Movie;
import com.estore.api.estoreapi.movies.persistence.MovieDAO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Handles the REST API requests for a Movie object.
 * <p>
 * {@literal @}RestController Spring annotation identifies this class as a REST API
 * method handler to the Spring framework
 *
 * @author Group 3C, The Code Monkeys
 */

@RestController
@RequestMapping("movies")
public class MovieController {
	/** TODO: Add description of the purpose of Logger, once it's actually used. */
	private static final Logger LOG = Logger.getLogger(MovieController.class.getName());
	/** The MovieDAO object this Controller interacts with to get Movie objects. */
	private MovieDAO movieDao;

	/**
	 * Creates a REST API controller to respond to Movie requests.
	 *
	 * @param movieDao The {@link MovieDAO Movie Data Access Object} to perform CRUD operations
	 *                 <br>
	 *                 This dependency is injected by the Spring Framework
	 */
	public MovieController (MovieDAO movieDao) {
		this.movieDao = movieDao;
	}

	/**
	 * Responds to the GET request for a {@linkplain Movie movie} with the given id.
	 *
	 * @param id The id used to locate a {@link Movie movie}
	 * @return ResponseEntity with {@link Movie movie} object and HTTP status of OK if found<br>
	 * ResponseEntity with HTTP status of NOT_FOUND if not found<br>
	 * ResponseEntity with HTTP status of INTERNAL_SERVER_ERROR otherwise
	 */
	@GetMapping("/{id}")
	public ResponseEntity<Movie> getMovie (@PathVariable int id) {
		LOG.info("GET /movies/" + id);
		try {
			// Try to get the movie based on the id entered by the user
			Movie movie = movieDao.getMovie(id);
			if (movie != null) {
				return new ResponseEntity<Movie>(movie, HttpStatus.OK);
			} else {
				return new ResponseEntity<Movie>(HttpStatus.NOT_FOUND);
			}
		} catch (IOException e) {
			LOG.log(Level.SEVERE, e.getLocalizedMessage());
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	/**
	 * Responds to the GET request for all {@linkplain Movie movies}.
	 *
	 * @return ResponseEntity with array of {@link Movie movie} objects (may be empty) and
	 * HTTP status of OK<br>
	 * ResponseEntity with HTTP status of INTERNAL_SERVER_ERROR otherwise
	 */
	@GetMapping("")
	public ResponseEntity<Movie[]> getMovie () {
		LOG.info("GET /movies/");
		try {
			// Try and get a list of all the movies from the system
			Movie[] movies = movieDao.getMovies();
			if (movies != null) {
				return new ResponseEntity<Movie[]>(movies, HttpStatus.OK);
			} else {
				return new ResponseEntity<>(HttpStatus.NOT_FOUND);
			}
		} catch (IOException e) {
			LOG.log(Level.SEVERE, e.getLocalizedMessage());
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	/**
	 * Responds to the GET request for all {@linkplain Movie movies} whose movie title
	 * contains the given text.
	 *
	 * @param title A String which contains the text used to find the {@link Movie movie} to a movie
	 * @return ResponseEntity with array of {@link Movie movie} objects (may be empty) and
	 * HTTP status of OK<br>
	 * ResponseEntity with HTTP status of INTERNAL_SERVER_ERROR otherwise
	 */
	@GetMapping("/")
	public ResponseEntity<Movie[]> searchMovies (@RequestParam String title) {
		LOG.info("GET /movies/?title=" + title);
		try {
			Movie[] foundMovies = movieDao.findMovies(title);
			/*
			 * If movieDao.findMovies() fails, an IOException is thrown. Assume function
			 * passed successfully and return the movie array even if it is empty. Which
			 * returns a null list
			 */
			return new ResponseEntity<Movie[]>(foundMovies, HttpStatus.OK);
		} catch (IOException e) {
			LOG.log(Level.SEVERE, e.getLocalizedMessage());
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	/**
	 * Creates a {@linkplain Movie movie} with the provided movie object.
	 *
	 * @param movie The {@link Movie movie} to create
	 * @return ResponseEntity with created {@link Movie movie} object and HTTP status of CREATED<br>
	 * ResponseEntity with HTTP status of CONFLICT if {@link Movie movie} object already exists<br>
	 * ResponseEntity with HTTP status of INTERNAL_SERVER_ERROR otherwise
	 */
	@PostMapping("")
	public ResponseEntity<Movie> createMovie (@RequestBody Movie movie) {
		LOG.info("POST /movies/" + movie);

		try {
			Movie newMovie = movieDao.createMovie(movie);
			if (newMovie != null) {
				return new ResponseEntity<>(newMovie, HttpStatus.CREATED);
			} else {
				return new ResponseEntity<>(HttpStatus.CONFLICT);
			}
		} catch (IOException e) {
			LOG.log(Level.SEVERE, e.getLocalizedMessage());
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	/**
	 * Updates the {@linkplain Movie movie} with the provided {@linkplain Movie movie} object, if it
	 * exists.
	 *
	 * @param movie The {@link Movie movie} to update
	 * @return ResponseEntity with updated {@link Movie movie} object and HTTP status of OK if updated<br>
	 * ResponseEntity with HTTP status of NOT_FOUND if not found<br>
	 * ResponseEntity with HTTP status of INTERNAL_SERVER_ERROR otherwise
	 */
	@PutMapping("")
	public ResponseEntity<Movie> updateMovie (@RequestBody Movie movie) {
		LOG.info("PUT /movies/" + movie);
		try {
			Movie _movie = movieDao.updateMovie(movie);
			if (_movie != null) {
				return new ResponseEntity<Movie>(_movie, HttpStatus.OK);
			} else {
				return new ResponseEntity<>(HttpStatus.NOT_FOUND);
			}
		} catch (Exception e) {
			LOG.log(Level.SEVERE, e.getLocalizedMessage());
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	/**
	 * Deletes a {@linkplain Movie movie} with the given id.
	 *
	 * @param id The id of the {@link Movie movie} to deleted
	 * @return ResponseEntity HTTP status of OK if deleted<br>
	 * ResponseEntity with HTTP status of NOT_FOUND if not found<br>
	 * ResponseEntity with HTTP status of INTERNAL_SERVER_ERROR otherwise
	 */
	@DeleteMapping("/{id}")
	public ResponseEntity<Movie> deleteMovie (@PathVariable int id) {
		LOG.info("DELETE /movies/" + id);
		try {
			if (movieDao.deleteMovie(id)) {
				return new ResponseEntity<>(HttpStatus.OK);
			} else {
				return new ResponseEntity<>(HttpStatus.NOT_FOUND);
			}
		} catch (IOException e) {
			LOG.log(Level.SEVERE, e.getLocalizedMessage());
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
}
