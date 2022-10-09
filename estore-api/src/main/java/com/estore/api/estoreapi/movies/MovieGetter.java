package com.estore.api.estoreapi.movies;

import com.estore.api.estoreapi.movies.model.Movie;
import com.estore.api.estoreapi.movies.persistence.MovieDAO;
import org.springframework.stereotype.Component;

import java.io.IOException;

/**
 * Get a Movie object from a MovieDAO object with its id.
 */
@Component
public class MovieGetter {
	/** Object to retrieve Movie objects from. */
	private MovieDAO movieDAO;

	/**
	 * Create a new MovieGetter object with the given MovieDAO object.
	 *
	 * @param movieDAO The MovieDAO object to retrieve movies from.
	 */
	public MovieGetter (MovieDAO movieDAO) {
		this.movieDAO = movieDAO;
	}

	/**
	 * Get a Movie object with the given id.
	 *
	 * @param id The id of the movie to get
	 * @return Movie object with given id
	 */
	public Movie getMovie (int id) {
		try {
			return movieDAO.getMovie(id);
		} catch (IOException e) {
			e.printStackTrace();
			System.exit(69);
		}
		return null;
	}
}
