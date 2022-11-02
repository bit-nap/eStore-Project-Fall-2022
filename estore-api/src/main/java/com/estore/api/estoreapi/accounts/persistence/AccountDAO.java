package com.estore.api.estoreapi.accounts.persistence;

import com.estore.api.estoreapi.accounts.model.Account;

import java.io.IOException;

/**
 * Defines the interface for account object persistence.
 *
 * @author Group 3C, The Code Monkeys
 */
public interface AccountDAO {
	/**
	 * Creates and saves a {@linkplain Account Account}.
	 *
	 * @param Account {@linkplain Account Account} object to be created and saved<br>
	 *                The id of the Account object is ignored and a new unique id is assigned
	 * @return new {@link Account Account} if successful, false otherwise
	 * @throws IOException if an issue with underlying storage
	 */
	Account createAccount (Account Account) throws IOException;

	/**
	 * Updates and saves a {@linkplain Account Account}.
	 *
	 * @param Account {@link Account Account} object to be updated and saved
	 * @return updated {@link Account Account} if successful, null if
	 * {@link Account Account} could not be found
	 * @throws IOException if underlying storage cannot be accessed
	 */
	Account updateAccount (Account Account) throws IOException;

	/**
	 * Deletes a {@linkplain Account Account} with the given id.
	 *
	 * @param id The id of the {@link Account Account}
	 * @return true if the {@link Account Account} was deleted<br>
	 * false if Account with the given id does not exist
	 * @throws IOException if underlying storage cannot be accessed
	 */
	boolean deleteAccount (int id) throws IOException;

	/**
	 * Retrieves a {@linkplain Account Account} with the given id.
	 *
	 * @param id The id of the {@link Account Account} to get.
	 * @return a {@link Account Account} object with the matching id.<br>
	 * null if no {@link Account Account} with a matching id is found.
	 * @throws IOException if an issue with underlying storage
	 */
	Account getAccount (int id) throws IOException;

	/**
	 * Retrieves all {@linkplain Account accounts}.
	 *
	 * @return An array of {@link Account account} objects, may be empty
	 * @throws IOException if an issue with underlying storage
	 */
	Account[] getAccounts () throws IOException;
}
