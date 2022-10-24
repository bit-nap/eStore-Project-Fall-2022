package com.estore.api.estoreapi.sodas.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.logging.Logger;

/**
 * Represents a soda.
 *
 * @author Group 3C, The Code Monkeys
 */
public class Soda {
	// Package private for tests - Prof
	static final String STRING_FORMAT = "Soda [id=%d, name=%s]";

	/** TODO: Add description of the purpose of Logger, once it's actually used. */
	private static final Logger LOG = Logger.getLogger(Soda.class.getName());

	/** The id of this soda. */
	@JsonProperty("id") private int id;
	/** The name of this soda. */
	@JsonProperty("name") private String name;
	/** The path to the poster for this soda. */
	@JsonProperty("poster") private String poster;

	/**
	 * Create a Soda object with the given information
	 *
	 * @param id        The id of this soda
	 * @param name     	The name of the soda
	 * @param poster    The path to the poster for this soda.
	 *                  <p>
	 *                  {@literal @}JsonProperty is used in serialization and deserialization
	 *                  of the JSON object to the Java object in mapping the fields.  If a field
	 *                  is not provided in the JSON object, the Java field gets the default Java
	 *                  value, i.e. 0 for int
	 */
	public Soda (@JsonProperty("id") int id, @JsonProperty("name") String name, @JsonProperty("poster") String poster) {
		this.id = id;
		this.name = name;
		this.poster = poster;
	}

	/**
	 * @return The id of this soda
	 */
	public int getId () {
		return id;
	}

	/**
	 * @return The name of this soda
	 */
	public String getTitle () {
		return name;
	}

	/**
	 * @return The path to the poster for this soda.
	 */
	public String getPoster () {
		return poster;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String toString () {
		return String.format(STRING_FORMAT, id, name);
	}
}
