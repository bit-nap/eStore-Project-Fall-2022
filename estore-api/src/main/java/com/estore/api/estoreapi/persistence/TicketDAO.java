package com.estore.api.estoreapi.persistence;

import com.estore.api.estoreapi.model.Ticket;

import java.io.IOException;

/**
 * Defines the interface for ticket object persistence.
 */
public interface TicketDAO {
	/**
	 * Retrieves all {@linkplain Ticket tickets}.
	 *
	 * @return An array of {@link Ticket ticket} objects, may be empty
	 * @throws IOException if an issue with underlying storage
	 */
	Ticket[] getTickets () throws IOException;

	/**
	 * Finds all {@linkplain Ticket tickets} whose ticket holder name contains the given text.
	 *
	 * @param containsText The text to match against
	 * @return An array of {@link Ticket tickets} whose ticket holder name contains the given text, may be empty
	 * @throws IOException if an issue with underlying storage
	 */
	Ticket[] findTickets (String containsText) throws IOException;

	/**
	 * Retrieves a {@linkplain Ticket ticket} with the given id.
	 *
	 * @param id The id of the {@link Ticket ticket} to get.
	 * @return a {@link Ticket ticket} object with the matching id.
	 * <br>
	 * null if no {@link Ticket ticket} with a matching id is found.
	 * @throws IOException if an issue with underlying storage
	 */
	Ticket getTicket (int id) throws IOException;

	/**
	 * Creates and saves a {@linkplain Ticket ticket}.
	 *
	 * @param ticket {@linkplain Ticket ticket} object to be created and saved
	 *               <br>
	 *               The id of the ticket object is ignored and a new unique id is assigned
	 * @return new {@link Ticket ticket} if successful, false otherwise
	 * @throws IOException if an issue with underlying storage
	 */
	Ticket createTicket (Ticket ticket) throws IOException;

	/**
	 * Updates and saves a {@linkplain Ticket ticket}.
	 *
	 * @param ticket {@link Ticket ticket} object to be updated and saved
	 * @return updated {@link Ticket ticket} if successful, null if
	 * {@link Ticket ticket} could not be found
	 * @throws IOException if underlying storage cannot be accessed
	 */
	Ticket updateTicket (Ticket ticket) throws IOException;

	/**
	 * Deletes a {@linkplain Ticket ticket} with the given id.
	 *
	 * @param id The id of the {@link Ticket ticket}
	 * @return true if the {@link Ticket ticket} was deleted
	 * <br>
	 * false if ticket with the given id does not exist
	 * @throws IOException if underlying storage cannot be accessed
	 */
	boolean deleteTicket (int id) throws IOException;
}
