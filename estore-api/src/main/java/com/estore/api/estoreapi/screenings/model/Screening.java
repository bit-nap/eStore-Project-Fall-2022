package com.estore.api.estoreapi.screenings.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Represents a screening of a movie.
 *
 * @author Group 3C, The Code Monkeys
 */
public class Screening implements Comparable<Screening> {
	/** The total number of tickets available for any screening. */
	public static final int TOTAL_TICKETS = 20;

	// Package private for tests - Prof
	static final String STRING_FORMAT = "Screening [id=%d, movieId=%s, ticketsRemaining=%s, date=%s, time=%s, seats]";

	/** The id of this screening. */
	@JsonProperty("id") private int id;
	/** The id of the Movie being shown at this screening. */
	@JsonProperty("movieId") private int movieId;
	/** The number of tickets remaining for this screening. */
	@JsonProperty("ticketsRemaining") private int ticketsRemaining;
	/** The date of this screening. */
	@JsonProperty("date") private String date;
	/** The time of this screening. */
	@JsonProperty("time") private String time;
	/**
	 * The seats and their availability for the screening.
	 * If the seat is already taken the element will be true else false.
	 */
	@JsonProperty("seats") private boolean[][] seats;

	/**
	 * Create a Screening object with the given id and movie name.
	 *
	 * @param id               The id of this screening
	 * @param movieId          The id of the Movie being shown at this screening
	 * @param ticketsRemaining The number of tickets remaining for this screening
	 * @param date             The date of this screening
	 * @param time             The time of this screening
	 *                         <p>
	 *                         {@literal @}JsonProperty is used in serialization and deserialization
	 *                         of the JSON object to the Java object in mapping the fields.  If a field
	 *                         is not provided in the JSON object, the Java field gets the default Java
	 *                         value, i.e. 0 for int
	 */
	@JsonCreator
	public Screening (@JsonProperty("id") int id, @JsonProperty("movieId") int movieId,
	                  @JsonProperty("ticketsRemaining") int ticketsRemaining, @JsonProperty("date") String date,
	                  @JsonProperty("time") String time, @JsonProperty("seats") boolean[][] seats) {
		this.id = id;
		this.movieId = movieId;
		if (ticketsRemaining > TOTAL_TICKETS) {
			// prevents there being more tickets than seats
			this.ticketsRemaining = TOTAL_TICKETS;
		} else {
			this.ticketsRemaining = ticketsRemaining;
		}
		this.date = date;
		this.time = time;
		this.seats = seats;
	}

	/**
	 * Update this screening's movie.
	 *
	 * @param movieId The id of the Movie being shown at this screening
	 */
	public void setMovieId (int movieId) {
		this.movieId = movieId;
	}

	/**
	 * Check if this screening's movie id is the same as the given movie id.
	 *
	 * @param movieId Movie id to compare to
	 * @return True if this screening has the given movie id, else False
	 */
	public boolean movieIdIs (int movieId) {
		return this.movieId == movieId;
	}

	/**
	 * @return The id of this screening
	 */
	public int getId () {
		return id;
	}

	/**
	 * @return The id of the Movie being shown at this screening
	 */
	public int getMovieId () {
		return movieId;
	}

	/**
	 * @return The number of tickets remaining for this screening
	 */
	public int getTicketsRemaining () {
		return ticketsRemaining;
	}

	/**
	 * @return The date of this screening
	 */
	public String getDate () {
		return date;
	}

	/**
	 * @return The time of this screening
	 */
	public String getTime () {
		return time;
	}

	/**
	 * @return The seats and their availability for this screening, false for empty seats
	 */
	public boolean[][] getSeats () {
		return seats;
	}

	/**
	 * Compare the given Screening object to this Screening object, by comparing their date and time fields.
	 *
	 * @param o Screening object to compare to
	 * @return a negative integer if this < o,<br>
	 * zero if this == o,<br>
	 * a positive integer if this > o
	 */
	@Override
	public int compareTo (Screening o) {
		int dateResult = this.date.compareTo(o.date);
		if (dateResult == 0) {
			// only compare times if both Screenings are on the same date
			return this.time.compareTo(o.time);
		}
		return dateResult;
	}

	/**
	 * Check if this Screening object equals the given object. If they are both Screening objects, compare their date and times.
	 *
	 * @param other Screening to compare to
	 * @return True if this and other Screening are at the same date and time else False
	 */
	@Override
	public boolean equals (Object other) {
		if (this == other) return true;
		if (other == null || getClass() != other.getClass()) return false;
		Screening screening = (Screening) other;
		return date.equals(screening.date) && time.equals(screening.time);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String toString () {
		return String.format(STRING_FORMAT, id, movieId, ticketsRemaining, date, time);
	}
}
