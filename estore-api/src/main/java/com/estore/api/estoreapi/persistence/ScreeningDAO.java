package com.estore.api.estoreapi.persistence;

import com.estore.api.estoreapi.model.Screening;

import java.io.IOException;

/**
 * Defines the interface for screening object persistence.
 *
 * @author Group 3C, The Code Monkeys
 */
public interface ScreeningDAO {
	/**
	 * Retrieves all {@linkplain Screening screenings}.
	 *
	 * @return An array of {@link Screening screening} objects, may be empty
	 * @throws IOException if an issue with underlying storage
	 */
	Screening[] getScreenings () throws IOException;

	/**
	 * Finds all {@linkplain Screening screenings} whose movie title contains the given text.
	 *
	 * @param text The text to match against
	 * @return An array of {@link Screening screenings} whose movie title contains the given text, may be empty
	 * @throws IOException if an issue with underlying storage
	 */
	Screening[] findScreenings (String text) throws IOException;

	/**
	 * Retrieves a {@linkplain Screening screening} with the given id.
	 *
	 * @param id The id of the {@link Screening screening} to get.
	 * @return a {@link Screening screening} object with the matching id.
	 * <br>
	 * null if no {@link Screening screening} with a matching id is found.
	 * @throws IOException if an issue with underlying storage
	 */
	Screening getScreening (int id) throws IOException;

	/**
	 * Creates and saves a {@linkplain Screening screening}.
	 *
	 * @param screening {@linkplain Screening screening} object to be created and saved
	 *                  <br>
	 *                  The id of the screening object is ignored and a new unique id is assigned
	 * @return new {@link Screening screening} if successful, false otherwise
	 * @throws IOException if an issue with underlying storage
	 */
	Screening createScreening (Screening screening) throws IOException;

	/**
	 * Updates and saves a {@linkplain Screening screening}.
	 *
	 * @param screening {@link Screening screening} object to be updated and saved
	 * @return updated {@link Screening screening} if successful, null if
	 * {@link Screening screening} could not be found
	 * @throws IOException if underlying storage cannot be accessed
	 */
	Screening updateScreening (Screening screening) throws IOException;

	/**
	 * Deletes a {@linkplain Screening screening} with the given id.
	 *
	 * @param id The id of the {@link Screening screening}
	 * @return true if the {@link Screening screening} was deleted
	 * <br>
	 * false if screening with the given id does not exist
	 * @throws IOException if underlying storage cannot be accessed
	 */
	boolean deleteScreening (int id) throws IOException;
}
