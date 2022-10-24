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
	static final String STRING_FORMAT = "Soda [id=%d, title=%s, runtime=%s, mpaRating=%s, year=%d]";

	/** TODO: Add description of the purpose of Logger, once it's actually used. */
	private static final Logger LOG = Logger.getLogger(Soda.class.getName());

	/** The id of this soda. */
	@JsonProperty("id") private int id;
	/** The title of this soda. */
	@JsonProperty("title") private String title;
	/** The path to the poster for this soda. */
	@JsonProperty("poster") private String poster;
	/** The runtime of this soda. */
	@JsonProperty("runtime") private int runtime;
	/** The Motion Pictures Association (MPA) rating of this soda. */
	@JsonProperty("mpaRating") private String mpaRating;
	/** The year this soda was released. */
	@JsonProperty("year") private int year;

	/**
	 * Create a Soda object with the given information
	 *
	 * @param id        The id of this soda
	 * @param title     The title of the soda
	 * @param poster    The path to the poster for this soda.
	 * @param runtime   The runtime of the soda
	 * @param mpaRating The Motion Pictures Association (MPA) rating of the soda
	 * @param year      The year the soda was released
	 *                  <p>
	 *                  {@literal @}JsonProperty is used in serialization and deserialization
	 *                  of the JSON object to the Java object in mapping the fields.  If a field
	 *                  is not provided in the JSON object, the Java field gets the default Java
	 *                  value, i.e. 0 for int
	 */
	public Soda (@JsonProperty("id") int id, @JsonProperty("title") String title, @JsonProperty("poster") String poster,
	              @JsonProperty("runtime") int runtime, @JsonProperty("mpaRating") String mpaRating, @JsonProperty("year") int year) {
		this.id = id;
		this.title = title;
		this.poster = poster;
		this.runtime = runtime;
		this.mpaRating = mpaRating;
		this.year = year;
	}

	/**
	 * @return The id of this soda
	 */
	public int getId () {
		return id;
	}

	/**
	 * @return The title of this soda
	 */
	public String getTitle () {
		return title;
	}

	/**
	 * @return The path to the poster for this soda.
	 */
	public String getPoster () {
		return poster;
	}

	/**
	 * @return The runtime of this soda
	 */
	public int getRuntime () {
		return runtime;
	}

	/**
	 * @return The Motion Pictures Association (MPA) rating of this soda
	 */
	public String getMpaRating () {
		return mpaRating;
	}

	/**
	 * @return The year this soda was released
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
