package com.estore.api.estoreapi.vote.model;

import com.estore.api.estoreapi.movies.MovieGetter;
import com.estore.api.estoreapi.movies.model.Movie;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Objects;
import java.util.logging.Logger;

/**
 * Represents a Vote of a movie.
 *
 * @author Group 3C, The Code Monkeys
 */
public class Vote implements Comparable<Vote> {
	/** TODO: Add description of the purpose of Logger, once it's actually used. */
	private static final Logger LOG = Logger.getLogger(Vote.class.getName());

	// Package private for tests - Prof
	static final String STRING_FORMAT = "Vote [id=%d, movieName=%s, howManyVotes=%d]";

	/** The id of this Vote. */
	@JsonProperty("id") private int id;
	@JsonProperty("movieName") private String movieName;
	@JsonProperty("howManyVotes") private int howManyVotes;

	/**
	 * Create a Vote object with an id, a movie name, and a number of votes.
	 *
	 * @param id			The id of the movie being voted on
	 * @param movieName		The name of the movie that is being voted on
	 * @param howManyVotes 	The number of votes the movie has
	 */
	@JsonCreator
	public Vote (@JsonProperty("id") int id, @JsonProperty("movieName") String movieName,
	                  @JsonProperty("howManyVotes") int howManyVotes) {
		this.id = id;
		this.movieName = movieName;
		this.howManyVotes = howManyVotes;
	}

	/**
	 * Create a Vote object with an id, a movie name, and a number of votes.
	 *
	 * @param id			The id of the movie being voted on
	 * @param movieName		The name of the movie that is being voted on
	 * @param howManyVotes 	The number of votes the movie has
	 */
	public Vote (@JsonProperty("id") int id, @JsonProperty("movieName") String movieName,
	                  @JsonProperty("howManyVotes") int howManyVotes) {
		this(id, movieName, howManyVote);
	}

	/**
	 * Update this vote's name.
	 *
	 * @param movieName The name of the movie that is being voted on
	 */
	public void setMovieName (int movieName) {
		this.movieName = movieName;
	}

	/**
	 * Check if this Vote's movie contains the given text in its title.
	 * Assumes this Vote's movieGetter field has been correctly instantiated.
	 *
	 * @param text Text to search within this Vote's movie.
	 * @return True if given text is found in the movie title, else False
	 */
	public boolean movieTitleContains (String text) {
		return movie.titleContains(text);
	}

	/**
	 * Check if this movies name is the same as the given movie
	 *
	 * @param movieName Movie name to compare to
	 * @return True if this movie has the given movie name, else False
	 */
	public boolean movieNamesEqual (String movieName) {
		return this.movieName.equals(movieName);
	}

	/**
	 * @return The id of this Vote
	 */
	public int getId () {
		return id;
	}

	/**
	 * @return The name of the movie being voted on
	 */
	public String getMovieName () {
		return movieName;
	}

	/**
	 * @return The number votes a movie has
	 */
	public int getHowManyVotes () {
		return howManyVote;
	}

	/**
	 * Compare the given Vote object to this Vote object, by comparing their movie name field.
	 *
	 * @param o Vote object to compare to
	 * @return a negative integer if this < o,<br>
	 * zero if this == o,<br>
	 * a positive integer if this > o
	 */
	@Override
	public int compareTo (Vote o) {
		return this.movieName.compareTo(o.movieName);
	}

	/**
	 * Check if this Vote object equals the given object. If they are both Vote objects, compare their movie names.
	 *
	 * @param other Vote to compare to
	 * @return True if this and other Vote have the same movie name else False
	 */
	@Override
	public boolean equals (Object other) {
		if (this == other) return true;
		if (other == null || getClass() != other.getClass()) return false;
		Vote vote = (Vote) other;
		return movieName.equals(vote.movieName);
	}

	@Override public int hashCode () {
		return Objects.hash(id, howManyVote);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String toString () {
		return String.format(STRING_FORMAT, id, movieName, howManyVote);
	}
}
