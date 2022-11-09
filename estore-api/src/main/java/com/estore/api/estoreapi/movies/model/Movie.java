package com.estore.api.estoreapi.movies.model;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Represents a movie.
 *
 * @author Group 3C, The Code Monkeys
 */
public class Movie {
	// Package private for tests - Prof
	static final String STRING_FORMAT = "Movie [id=%d, title=%s, runtime=%s, mpaRating=%s, year=%d]";

	/** The id of this movie. */
	@JsonProperty("id") private int id;
	/** The title of this movie. */
	@JsonProperty("title") private String title;
	/** The path to the poster for this movie. */
	@JsonProperty("poster") private String poster;
	/** The runtime of this movie. */
	@JsonProperty("runtime") private int runtime;
	/** The Motion Pictures Association (MPA) rating of this movie. */
	@JsonProperty("mpaRating") private String mpaRating;
	/** The year this movie was released. */
	@JsonProperty("year") private int year;

	/**
	 * Create a Movie object with the given information
	 *
	 * @param id        The id of this movie
	 * @param title     The title of the movie
	 * @param poster    The path to the poster for this movie.
	 * @param runtime   The runtime of the movie
	 * @param mpaRating The Motion Pictures Association (MPA) rating of the movie
	 * @param year      The year the movie was released
	 *                  <p>
	 *                  {@literal @}JsonProperty is used in serialization and deserialization
	 *                  of the JSON object to the Java object in mapping the fields.  If a field
	 *                  is not provided in the JSON object, the Java field gets the default Java
	 *                  value, i.e. 0 for int
	 */
	public Movie (@JsonProperty("id") int id, @JsonProperty("title") String title, @JsonProperty("poster") String poster,
	              @JsonProperty("runtime") int runtime, @JsonProperty("mpaRating") String mpaRating, @JsonProperty("year") int year) {
		this.id = id;
		this.title = title;
		this.poster = poster;
		this.runtime = runtime;
		this.mpaRating = mpaRating;
		this.year = year;
	}

	/**
	 * Check if this movie's title contains the given text. Not case-sensitive.
	 *
	 * @param text Text to search within this screening's movie.
	 * @return True if given text is found in the movie title, else False
	 */
	public boolean titleContains (String text) {
		String lowerCaseTitle = title.toLowerCase();
		String lowerCaseText = text.toLowerCase();
		return lowerCaseTitle.contains(lowerCaseText);
	}

	/**
	 * @return The id of this movie
	 */
	public int getId () {
		return id;
	}

	/**
	 * @return The title of this movie
	 */
	public String getTitle () {
		return title;
	}

	/**
	 * @return The path to the poster for this movie.
	 */
	public String getPoster () {
		return poster;
	}

	/**
	 * @return The runtime of this movie
	 */
	public int getRuntime () {
		return runtime;
	}

	/**
	 * @return The Motion Pictures Association (MPA) rating of this movie
	 */
	public String getMpaRating () {
		return mpaRating;
	}

	/**
	 * @return The year this movie was released
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
