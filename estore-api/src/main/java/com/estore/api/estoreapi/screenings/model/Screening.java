package com.estore.api.estoreapi.screenings.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.logging.Logger;

/**
 * Represents a screening of a movie.
 *
 * @author Group 3C, The Code Monkeys
 */
public class Screening {
	/** TODO: Add description of the purpose of Logger, once it's actually used. */
	private static final Logger LOG = Logger.getLogger(Screening.class.getName());

	// Package private for tests - Prof
	static final String STRING_FORMAT = "Screening [id=%d, movie=%s]";

	/** The id of this screening. */
	@JsonProperty("id") private int id;
	/** The name of the movie being shown at this screening. */
	@JsonProperty("movie") private String movie;

	/**
	 * Create a Screening object with the given id and movie name.
	 *
	 * @param id    The id of this screening.
	 * @param movie The name of the movie being shown at this screening.
	 *              <p>
	 *              {@literal @}JsonProperty is used in serialization and deserialization
	 *              of the JSON object to the Java object in mapping the fields.  If a field
	 *              is not provided in the JSON object, the Java field gets the default Java
	 *              value, i.e. 0 for int
	 */
	public Screening (@JsonProperty("id") int id, @JsonProperty("movie") String movie) {
		this.id = id;
		this.movie = movie;
	}

	/**
	 * @return The id of this screening.
	 */
	public int getId () {
		return id;
	}

	/**
	 * Set the name of the movie being shown at this screening.
	 * <p>
	 * Necessary for JSON object to perform Java object deserialization.
	 * <p>
	 * I think this is what was originally meant. - Oscar
	 *
	 * @param movie The name of the movie being shown at this screening.
	 */
	public void setMovie (String movie) {
		this.movie = movie;
	}

	/**
	 * @return The name of the movie being shown at this screening.
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
