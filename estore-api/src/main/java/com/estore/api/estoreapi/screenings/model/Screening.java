package com.estore.api.estoreapi.screenings.model;

import com.estore.api.estoreapi.movies.MovieGetter;
import com.estore.api.estoreapi.movies.model.Movie;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.logging.Logger;

/**
 * Represents a screening of a movie.
 *
 * @author Group 3C, The Code Monkeys
 */
public class Screening {
	/** The total number of tickets available for any screening. */
	public static final int TOTAL_TICKETS = 40;
	/** TODO: Add description of the purpose of Logger, once it's actually used. */
	private static final Logger LOG = Logger.getLogger(Screening.class.getName());

	// Package private for tests - Prof
	static final String STRING_FORMAT = "Screening [id=%d, movieId=%s, ticketsRemaining=%s, date=%s, time=%s]";

	/** The id of this screening. */
	@JsonProperty("id") private int id;
	/** The id of the Movie being shown at this screening. */
	@JsonProperty("movieId") private int movieId;
	/** The number of tickets remaining for this screening. */
	@JsonProperty("ticketsRemaining") private int ticketsRemaining;
	/** The date of this screening. */
	@JsonProperty("date") private LocalDate date;
	/** The time of this screening. */
	@JsonProperty("time") private LocalTime time;

	/** The MovieGetter object used to set this object's movie field. */
	@JsonIgnore MovieGetter movieGetter;
	/** The Movie being shown at this screening. */
	@JsonIgnore private Movie movie;

	/**
	 * Create a Screening object with the given id and movie name. The {@linkplain #setMovieGetter} method should be called
	 * immediately after this constructor call to set this object's movie field.
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
	                  @JsonProperty("ticketsRemaining") int ticketsRemaining, @JsonProperty("date") LocalDate date,
	                  @JsonProperty("time") LocalTime time) {
		this.id = id;
		this.movieId = movieId;
		this.ticketsRemaining = ticketsRemaining;
		this.date = date;
		this.time = time;
	}

	/**
	 * Create a Screening object with the given information.
	 *
	 * @param movieGetter      The MovieGetter object used to set this object's movie field
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
	public Screening (@JsonProperty("id") int id, @JsonProperty("movieId") int movieId,
	                  @JsonProperty("ticketsRemaining") int ticketsRemaining, @JsonProperty("date") LocalDate date,
	                  @JsonProperty("time") LocalTime time, MovieGetter movieGetter) {
		this(id, movieId, ticketsRemaining, date, time);
		setMovieGetter(movieGetter);
	}

	/**
	 * Set this object's MovieGetter.
	 *
	 * @param movieGetter The MovieGetter object used to set this object's movie field.
	 */
	public void setMovieGetter (MovieGetter movieGetter) {
		this.movieGetter = movieGetter;
		this.movie = this.movieGetter.getMovie(movieId);
	}

	/**
	 * Update this screening's movie.
	 *
	 * @param movieId The id of the Movie being shown at this screening
	 */
	public void setMovieId (int movieId) {
		this.movieId = movieId;
		this.movie = movieGetter.getMovie(movieId);
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
	public LocalDate getDate () {
		return date;
	}

	/**
	 * @return The time of this screening
	 */
	public LocalTime getTime () {
		return time;
	}

	/**
	 * @return The Movie being shown at this screening
	 */
	public Movie getMovie () {
		return movie;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String toString () {
		return String.format(STRING_FORMAT, id, movieId, ticketsRemaining, date, time);
	}
}
