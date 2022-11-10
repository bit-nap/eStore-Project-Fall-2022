package com.estore.api.estoreapi.suggestions.model;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Class representing a suggestion for a movie.
 * Part of our 10% feature.
 *
 * @author Group 3C, The Code Monkeys
 */
public class Suggestion {
	// Package private for tests - Prof
	static final String STRING_FORMAT = "Suggestion [id=%d, movieTitle=%s, votes=%d]";

	/** The id of this suggestion. */
	@JsonProperty("id") private int id;
	/** The title of the suggested movie. */
	@JsonProperty("movieTitle") private String movieTitle;
	/** The number of votes for this suggested movie. */
	@JsonProperty("votes") private int votes;

	public Suggestion (@JsonProperty("id") int id, @JsonProperty("movieTitle") String movieTitle, @JsonProperty("votes") int votes) {
		this.id = id;
		this.movieTitle = movieTitle;
		this.votes = votes;
	}

	/**
	 * @return The id of this suggestion.
	 */
	public int getId () {
		return id;
	}

	/**
	 * @return The title of the suggested movie.
	 */
	public String getMovieTitle () {
		return movieTitle;
	}

	/**
	 * @return The number of votes for this suggested movie.
	 */
	public int getVotes () {
		return votes;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String toString () {
		return String.format(STRING_FORMAT, id, movieTitle, votes);
	}

}
