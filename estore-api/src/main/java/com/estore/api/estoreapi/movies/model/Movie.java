package com.estore.api.estoreapi.movies.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.logging.Logger;

/**
 * Represents a movie.
 *
 * @author Group 3C, The Code Monkeys
 */
public class Movie {
	/** TODO: Add description of the purpose of Logger, once it's actually used. */
	private static final Logger LOG = Logger.getLogger(Movie.class.getName());

	// Package private for tests - Prof
	static final String STRING_FORMAT = "Movie [id=%d, movie=%s]";

	/** The id of this movie. */
	@JsonProperty("id") private int id;
	/** The name of the movie. */
	@JsonProperty("movie") private String movie;

	/**
	 * Create a Movie object with the given id and movie name.
	 *
	 * @param id    The id of this movie.
	 * @param movie The name of the movie.
	 *              <p>
	 *              {@literal @}JsonProperty is used in serialization and deserialization
	 *              of the JSON object to the Java object in mapping the fields.  If a field
	 *              is not provided in the JSON object, the Java field gets the default Java
	 *              value, i.e. 0 for int
	 */
	public Movie (@JsonProperty("id") int id, @JsonProperty("movie") String movie) {
		this.id = id;
		this.movie = movie;
	}

	/**
	 * @return The id of this movie.
	 */
	public int getId () {
		return id;
	}

	/**
	 * Set the name of the movie.
	 *
	 * @param movie The name of the movie.
	 */
	public void setMovie (String movie) {
		this.movie = movie;
	}

	/**
	 * @return The name of the movie.
	 */
	public String getMovie () {
		return movie;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String toString () {
		return String.format(STRING_FORMAT, id, movie);
	}
}
