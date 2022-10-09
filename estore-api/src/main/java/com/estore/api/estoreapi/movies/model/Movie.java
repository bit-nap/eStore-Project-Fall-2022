package com.estore.api.estoreapi.movies.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.logging.Logger;

/**
 * Represents a movie.
 *
 * @author Group 3C, The Code Monkeys
 */
public class Movie {
	// Package private for tests - Prof
	static final String STRING_FORMAT = "Movie [id=%d, title=%s, runtime=%s, mpaRating=%s, year=%d]";

	/** TODO: Add description of the purpose of Logger, once it's actually used. */
	private static final Logger LOG = Logger.getLogger(Movie.class.getName());

	/** The id of this movie. */
	@JsonProperty("id") private int id;
	/** The title of the movie. */
	@JsonProperty("title") private String title;
	/** The runtime of the movie. */
	@JsonProperty("runtime") private String runtime;
	/** The Motion Pictures Association (MPA) rating of the movie. */
	@JsonProperty("mpaRating") private String mpaRating;
	/** The year the movie was released. */
	@JsonProperty("year") private int year;

	/**
	 * Create a Movie object with the given information
	 *
	 * @param id        The id of this movie
	 * @param title     The title of the movie
	 * @param runtime   The runtime of the movie
	 * @param mpaRating The Motion Pictures Association (MPA) rating of the movie
	 * @param year      The year the movie was released
	 *                  <p>
	 *                  {@literal @}JsonProperty is used in serialization and deserialization
	 *                  of the JSON object to the Java object in mapping the fields.  If a field
	 *                  is not provided in the JSON object, the Java field gets the default Java
	 *                  value, i.e. 0 for int
	 */
	public Movie (@JsonProperty("id") int id, @JsonProperty("title") String title,
	              @JsonProperty("runtime") String runtime, @JsonProperty("mpaRating") String mpaRating,
	              @JsonProperty("year") int year) {
		this.id = id;
		this.title = title;
		this.runtime = runtime;
		this.mpaRating = mpaRating;
		this.year = year;
	}

	/**
	 * @return The id of this movie
	 */
	public int getId () {
		return id;
	}

	/**
	 * @return The title of the movie
	 */
	public String getTitle () {
		return title;
	}

	/**
	 * @return The runtime of the movie
	 */
	public String getRuntime () {
		return runtime;
	}

	/**
	 * @return The Motion Pictures Association (MPA) rating of the movie
	 */
	public String getMpaRating () {
		return mpaRating;
	}

	/**
	 * @return The year the movie was released
	 */
	public int getYear () {
		return year;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String toString () {
		return String.format(STRING_FORMAT, id, title, runtime, mpaRating, year);
	}
}
