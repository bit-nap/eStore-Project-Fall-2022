package com.estore.api.estoreapi.screenings.persistence;

import com.estore.api.estoreapi.screenings.model.Screening;

import java.io.IOException;

/**
 * Defines the interface for screening object persistence.
 *
 * @author Group 3C, The Code Monkeys
 */
public interface ScreeningDAO {
	/**
	 * Creates and saves a {@linkplain Screening Screening}.
	 *
	 * @param screening {@linkplain Screening Screening} object to be created and saved<br>
	 *                  The id of the Screening object is ignored and a new unique id is assigned
	 * @return new {@link Screening Screening} if successful, false otherwise
	 * @throws IOException if an issue with underlying storage
	 */
	Screening createScreening (Screening screening) throws IOException;

	/**
	 * Updates and saves a {@linkplain Screening Screening}.
	 *
	 * @param screening {@link Screening Screening} object to be updated and saved
	 * @return updated {@link Screening Screening} if successful, null if
	 * {@link Screening Screening} could not be found
	 * @throws IOException if underlying storage cannot be accessed
	 */
	Screening updateScreening (Screening screening) throws IOException;

	/**
	 * Deletes a {@linkplain Screening Screening} with the given id.
	 *
	 * @param id The id of the {@link Screening Screening}
	 * @return true if the {@link Screening Screening} was deleted<br>
	 * false if Screening with the given id does not exist
	 * @throws IOException if underlying storage cannot be accessed
	 */
	boolean deleteScreening (int id) throws IOException;

	/**
	 * Retrieves a {@linkplain Screening Screening} with the given id.
	 *
	 * @param id The id of the {@link Screening Screening} to get.
	 * @return a {@link Screening Screening} object with the matching id.<br>
	 * null if no {@link Screening Screening} with a matching id is found.
	 * @throws IOException if an issue with underlying storage
	 */
	Screening getScreening (int id) throws IOException;

	/**
	 * Retrieves all {@linkplain Screening screening}.
	 *
	 * @return An array of {@link Screening screening} objects, may be empty
	 * @throws IOException if an issue with underlying storage
	 */
	Screening[] getScreenings () throws IOException;

	/**
	 * Finds all {@linkplain Screening screenings} whose movie id matches the given id.
	 *
	 * @param movieId The movie id to match against
	 * @return An array of {@link Screening screenings} that are screening the given movie, by the given movieId, may be empty
	 * @throws IOException if an issue with underlying storage
	 */
	Screening[] findScreeningsForMovie (int movieId) throws IOException;
}
