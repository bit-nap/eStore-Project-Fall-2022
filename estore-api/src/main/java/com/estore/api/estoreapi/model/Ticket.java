package com.estore.api.estoreapi.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.logging.Logger;

/**
 * Represents a Ticket entity
 *
 */
public class Ticket {
	private static final Logger LOG = Logger.getLogger(Ticket.class.getName());

	// Package private for tests
	static final String STRING_FORMAT = "Ticket [movieId=%i, name=%s, time=%s]";

    // Properties of the Ticket (Refine domain model)
	@JsonProperty("movie") private int movieId;
	@JsonProperty("name") private String name;
    // Time could be a numeric value using util.getTime()
    // Otherwise assume screening time string is defined for each movie
    @JsonProperty("time") private String time;


	/**
	 * Create a ticket with the given movie, name, and time
	 *
	 * @param movieId The name of the movie
	 * @param name The name of the person that owns the ticket
     * @param time The time of the screening
	 *             <p>
	 *             {@literal @}JsonProperty is used in serialization and deserialization
	 *             of the JSON object to the Java object in mapping the fields.  If a field
	 *             is not provided in the JSON object, the Java field gets the default Java
	 *             value, i.e. 0 for int
	 */
	public Ticket (@JsonProperty("movieId") int movieId, @JsonProperty("name") String name, @JsonProperty("time") String time) {
		this.movieId = movieId;
		this.name = name;
        this.time = time;
	}

	/**
	 * Retrieves the id of the Ticket
	 *
	 * @return The id of the Ticket
	 */
	public int getId () {return movieId;}

	/**
	 * Sets the name of the Ticket - necessary for JSON object to Java object deserialization
	 *
	 * @param name The name of the Ticket
	 */
	public void setName (String name) {this.name = name;}

	/**
	 * Retrieves the name of the Ticket
	 *
	 * @return The name of the Ticket
	 */
	public String getName () {return name;}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String toString () {
		return String.format(STRING_FORMAT, movieId, name, time);
	}
}
