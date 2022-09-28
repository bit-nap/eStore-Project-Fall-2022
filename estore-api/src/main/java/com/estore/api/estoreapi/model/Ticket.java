package com.estore.api.estoreapi.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.logging.Logger;

/**
 * Represents a ticket to the screening of a movie.
 */
public class Ticket {
	/** TODO: Add description of the purpose of Logger, once it's actually used. */
	private static final Logger LOG = Logger.getLogger(Ticket.class.getName());

	// Package private for tests - Prof
	static final String STRING_FORMAT = "Ticket [id=%d, movie=%s]";

	/** The id of this ticket. */
	@JsonProperty("id") private int id;
	/**
	 * The name of the movie being shown at the screening this ticket belongs to.
	 * Should be changed to some sort of Movie class. - Oscar
	 */
	@JsonProperty("movie") private String movie;

	/**
	 * Create a ticket with the given id and movie name.
	 *
	 * @param id    The id of this ticket.
	 * @param movie The name of the movie being shown at the screening this ticket belongs to.
	 *              <p>
	 *              {@literal @}JsonProperty is used in serialization and deserialization
	 *              of the JSON object to the Java object in mapping the fields.  If a field
	 *              is not provided in the JSON object, the Java field gets the default Java
	 *              value, i.e. 0 for int
	 */
	public Ticket (@JsonProperty("id") int id, @JsonProperty("movie") String movie) {
		this.id = id;
		this.movie = movie;
	}

	/**
	 * @return The id of this ticket.
	 */
	public int getId () {
		return id;
	}

	/**
	 * Set the name of the movie being shown at the screening this ticket belongs to.
	 * <p>
	 * Necessary for JSON object to perform Java object deserialization.
	 * <p>
	 * I think this is what was originally meant. - Oscar
	 *
	 * @param movie The name of the movie being shown at the screening this ticket belongs to.
	 */
	public void setMovie (String movie) {
		this.movie = movie;
	}

	/**
	 * @return The name of the movie being shown at the screening this ticket belongs to.
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
