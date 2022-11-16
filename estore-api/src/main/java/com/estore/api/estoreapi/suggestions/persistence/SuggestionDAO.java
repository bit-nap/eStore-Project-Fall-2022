package com.estore.api.estoreapi.suggestions.persistence;

import com.estore.api.estoreapi.suggestions.model.Suggestion;

import java.io.IOException;

/**
 * Defines the interface for suggestion object persistence.
 *
 * @author Group 3C, The Code Monkeys
 */
public interface SuggestionDAO {
	/**
	 * Creates and saves a {@linkplain Suggestion Suggestion}.
	 *
	 * @param suggestion {@linkplain Suggestion Suggestion} object to be created and saved<br>
	 *                   The id of the Suggestion object is ignored and a new unique id is assigned
	 * @return new {@link Suggestion Suggestion} if successful, false otherwise
	 * @throws IOException if an issue with underlying storage
	 */
	Suggestion createSuggestion (Suggestion suggestion) throws IOException;

	/**
	 * Updates and saves a {@linkplain Suggestion Suggestion}.
	 *
	 * @param suggestion {@link Suggestion Suggestion} object to be updated and saved
	 * @return updated {@link Suggestion Suggestion} if successful, null if
	 * {@link Suggestion Suggestion} could not be found
	 * @throws IOException if underlying storage cannot be accessed
	 */
	Suggestion updateSuggestion (Suggestion suggestion) throws IOException;

	/**
	 * Deletes a {@linkplain Suggestion Suggestion} with the given id.
	 *
	 * @param id The id of the {@link Suggestion Suggestion}
	 * @return true if the {@link Suggestion Suggestion} was deleted<br>
	 * false if Suggestion with the given id does not exist
	 * @throws IOException if underlying storage cannot be accessed
	 */
	boolean deleteSuggestion (int id) throws IOException;

	/**
	 * Retrieves a {@linkplain Suggestion Suggestion} with the given id.
	 *
	 * @param id The id of the {@link Suggestion Suggestion} to get.
	 * @return a {@link Suggestion Suggestion} object with the matching id.<br>
	 * null if no {@link Suggestion Suggestion} with a matching id is found.
	 * @throws IOException if an issue with underlying storage
	 */
	Suggestion getSuggestion (int id) throws IOException;

	/**
	 * Retrieves all {@linkplain Suggestion suggestion}.
	 *
	 * @return An array of {@link Suggestion suggestion} objects, may be empty
	 * @throws IOException if an issue with underlying storage
	 */
	Suggestion[] getSuggestions () throws IOException;
}
