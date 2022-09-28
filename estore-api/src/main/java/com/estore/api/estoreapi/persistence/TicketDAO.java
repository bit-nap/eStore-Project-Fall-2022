package com.estore.api.estoreapi.persistence;

import com.estore.api.estoreapi.model.Ticket;

import java.io.IOException;

/**
 * Defines the interface for Hero object persistence
 *
 * @author SWEN Faculty
 */
public interface TicketDAO {
	/**
	 * Retrieves all {@linkplain Movie movies}
	 *
	 * @return An array of {@link Movie movies} objects, may be empty
	 * @throws IOException if an issue with underlying storage
	 */
	Ticket[] getTickets () throws IOException;

	/**
	 * Finds all {@linkplain Movie heroes} whose name contains the given text
	 *
	 * @param containsText The text to match against
	 * @return An array of {@link Movie heroes} whose nemes contains the given text, may be empty
	 * @throws IOException if an issue with underlying storage
	 */
	Ticket[] findTickets (String containsText) throws IOException;

	/**
	 * Retrieves a {@linkplain Movie hero} with the given id
	 *
	 * @param id The id of the {@link Movie hero} to get
	 * @return a {@link Movie hero} object with the matching id
	 * <br>
	 * null if no {@link Movie hero} with a matching id is found
	 * @throws IOException if an issue with underlying storage
	 */
	Ticket getTicket (int id) throws IOException;

	/**
	 * Creates and saves a {@linkplain Movie hero}
	 *
	 * @param hero {@linkplain Movie hero} object to be created and saved
	 *             <br>
	 *             The id of the hero object is ignored and a new uniqe id is assigned
	 * @return new {@link Movie hero} if successful, false otherwise
	 * @throws IOException if an issue with underlying storage
	 */
	Ticket createTicket (Ticket hero) throws IOException;

	/**
	 * Updates and saves a {@linkplain Movie hero}
	 *
	 * @param {@link Movie hero} object to be updated and saved
	 * @return updated {@link Movie hero} if successful, null if
	 * {@link Movie hero} could not be found
	 * @throws IOException if underlying storage cannot be accessed
	 */
	Ticket updateTicket (Ticket hero) throws IOException;

	/**
	 * Deletes a {@linkplain Movie hero} with the given id
	 *
	 * @param id The id of the {@link Movie hero}
	 * @return true if the {@link Movie hero} was deleted
	 * <br>
	 * false if hero with the given id does not exist
	 * @throws IOException if underlying storage cannot be accessed
	 */
	boolean deleteTicket (int id) throws IOException;
}
