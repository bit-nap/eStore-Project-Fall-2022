package com.estore.api.estoreapi.sodas.persistence;

import com.estore.api.estoreapi.sodas.model.Soda;

import java.io.IOException;

/**
 * Defines the interface for soda object persistence.
 *
 * @author Group 3C, The Code Monkeys
 */
public interface SodaDAO {
	/**
	 * Creates and saves a {@linkplain Soda Soda}.
	 *
	 * @param Soda {@linkplain Soda Soda} object to be created and saved<br>
	 *              The id of the Soda object is ignored and a new unique id is assigned
	 * @return new {@link Soda Soda} if successful, false otherwise
	 * @throws IOException if an issue with underlying storage
	 */
	Soda createSoda (Soda Soda) throws IOException;

	/**
	 * Updates and saves a {@linkplain Soda Soda}.
	 *
	 * @param Soda {@link Soda Soda} object to be updated and saved
	 * @return updated {@link Soda Soda} if successful, null if
	 * {@link Soda Soda} could not be found
	 * @throws IOException if underlying storage cannot be accessed
	 */
	Soda updateSoda (Soda Soda) throws IOException;

	/**
	 * Deletes a {@linkplain Soda Soda} with the given id.
	 *
	 * @param id The id of the {@link Soda Soda}
	 * @return true if the {@link Soda Soda} was deleted<br>
	 * false if Soda with the given id does not exist
	 * @throws IOException if underlying storage cannot be accessed
	 */
	boolean deleteSoda (int id) throws IOException;

	/**
	 * Retrieves a {@linkplain Soda Soda} with the given id.
	 *
	 * @param id The id of the {@link Soda Soda} to get.
	 * @return a {@link Soda Soda} object with the matching id.<br>
	 * null if no {@link Soda Soda} with a matching id is found.
	 * @throws IOException if an issue with underlying storage
	 */
	Soda getSoda (int id) throws IOException;

	/**
	 * Retrieves all {@linkplain Soda soda}.
	 *
	 * @return An array of {@link Soda soda} objects, may be empty
	 * @throws IOException if an issue with underlying storage
	 */
	Soda[] getSodas () throws IOException;

	/**
	 * Finds all {@linkplain Soda sodas} whose soda title contains the given text.
	 *
	 * @param text The text to match against
	 * @return An array of {@link Soda sodas} whose soda title contains the given text, may be empty
	 * @throws IOException if an issue with underlying storage
	 */
	Soda[] findSodas (String text) throws IOException;
}
