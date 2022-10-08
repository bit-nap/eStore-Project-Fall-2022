package com.estore.api.estoreapi.movies.persistence;

import com.estore.api.estoreapi.movies.model.Movie;

import java.io.IOException;

/**
 * Defines the interface for movie object persistence.
 *
 * @author Group 3C, The Code Monkeys
 */
public interface MovieDAO {
	/**
	 * Retrieves all {@linkplain Movie movie}.
	 *
	 * @return An array of {@link Movie movie} objects, may be empty
	 * @throws IOException if an issue with underlying storage
	 */
	Movie[] getMovies () throws IOException;

	/**
	 * Finds all {@linkplain Movie movies} whose movie title contains the given text.
	 *
	 * @param text The text to match against
	 * @return An array of {@link Movie movies} whose movie title contains the given text, may be empty
	 * @throws IOException if an issue with underlying storage
	 */
	Movie[] findMovies (String text) throws IOException;

	/**
	 * Retrieves a {@linkplain Movie Movie} with the given id.
	 *
	 * @param id The id of the {@link Movie Movie} to get.
	 * @return a {@link Movie Movie} object with the matching id.
	 * <br>
	 * null if no {@link Movie Movie} with a matching id is found.
	 * @throws IOException if an issue with underlying storage
	 */
	Movie getMovie (int id) throws IOException;

	/**
	 * Creates and saves a {@linkplain Movie Movie}.
	 *
	 * @param Movie {@linkplain Movie Movie} object to be created and saved
	 *                  <br>
	 *                  The id of the Movie object is ignored and a new unique id is assigned
	 * @return new {@link Movie Movie} if successful, false otherwise
	 * @throws IOException if an issue with underlying storage
	 */
	Movie createMovie (Movie Movie) throws IOException;

	/**
	 * Updates and saves a {@linkplain Movie Movie}.
	 *
	 * @param Movie {@link Movie Movie} object to be updated and saved
	 * @return updated {@link Movie Movie} if successful, null if
	 * {@link Movie Movie} could not be found
	 * @throws IOException if underlying storage cannot be accessed
	 */
	Movie updateMovie (Movie Movie) throws IOException;

	/**
	 * Deletes a {@linkplain Movie Movie} with the given id.
	 *
	 * @param id The id of the {@link Movie Movie}
	 * @return true if the {@link Movie Movie} was deleted
	 * <br>
	 * false if Movie with the given id does not exist
	 * @throws IOException if underlying storage cannot be accessed
	 */
	boolean deleteMovie (int id) throws IOException;
}
