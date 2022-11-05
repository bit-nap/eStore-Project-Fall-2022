package com.estore.api.estoreapi.vote.persistence;

import com.estore.api.estoreapi.vote.model.Vote;

import java.io.IOException;

/**
 * Defines the interface for Vote object persistence.
 *
 * @author Group 3C, The Code Monkeys
 */
public interface VoteDAO {
	/**
	 * Creates and saves a {@linkplain Vote Vote}.
	 *
	 * @param Vote {@linkplain Vote Vote} object to be created and saved<br>
	 *                  The id of the Vote object is ignored and a new unique id is assigned
	 * @return new {@link Vote Vote} if successful, false otherwise
	 * @throws IOException if an issue with underlying storage
	 */
	Vote createVote (Vote Vote) throws IOException;

	/**
	 * Updates and saves a {@linkplain Vote Vote}.
	 *
	 * @param Vote {@link Vote Vote} object to be updated and saved
	 * @return updated {@link Vote Vote} if successful, null if
	 * {@link Vote Vote} could not be found
	 * @throws IOException if underlying storage cannot be accessed
	 */
	Vote updateVote (Vote Vote) throws IOException;

	/**
	 * Deletes a {@linkplain Vote Vote} with the given id.
	 *
	 * @param id The id of the {@link Vote Vote}
	 * @return true if the {@link Vote Vote} was deleted<br>
	 * false if Vote with the given id does not exist
	 * @throws IOException if underlying storage cannot be accessed
	 */
	boolean deleteVote (int id) throws IOException;

	/**
	 * Retrieves all {@linkplain Vote Vote}.
	 *
	 * @return An array of {@link Vote Vote} objects, may be empty
	 * @throws IOException if an issue with underlying storage
	 */
	Vote[] getVotes () throws IOException;

	/**
	 * Finds all {@linkplain Vote Votes} whose movie name contains the given text.
	 *
	 * @param text The text to match against
	 * @return An {@linkplain Vote vote} if the text matches one of the movie names
	 * @throws IOException if an issue with underlying storage
	 */
	Vote findVote (String text) throws IOException;
}
