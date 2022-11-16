package com.estore.api.estoreapi.orders.persistence;

import com.estore.api.estoreapi.orders.model.Order;

import java.io.IOException;

/**
 * Defines the interface for order object persistence.
 *
 * @author Group 3C, The Code Monkeys
 */
public interface OrderDAO {
	/**
	 * Creates and saves a {@linkplain Order Order}.
	 *
	 * @param order {@linkplain Order Order} object to be created and saved<br>
	 *              The id of the Order object is ignored and a new unique id is assigned
	 * @return new {@link Order Order} if successful, false otherwise
	 * @throws IOException if an issue with underlying storage
	 */
	Order createOrder (Order order) throws IOException;

	/**
	 * Deletes a {@linkplain Order Order} with the given id.
	 *
	 * @param id The id of the {@link Order Order}
	 * @return true if the {@link Order Order} was deleted<br>
	 * false if Order with the given id does not exist
	 * @throws IOException if underlying storage cannot be accessed
	 */
	boolean deleteOrder (int id) throws IOException;

	/**
	 * Retrieves a {@linkplain Order Order} with the given id.
	 *
	 * @param id The id of the {@link Order Order} to get.
	 * @return a {@link Order Order} object with the matching id.<br>
	 * null if no {@link Order Order} with a matching id is found.
	 * @throws IOException if an issue with underlying storage
	 */
	Order getOrder (int id) throws IOException;

	/**
	 * Retrieves all {@linkplain Order orders}.
	 *
	 * @return An array of {@link Order order} objects, may be empty
	 * @throws IOException if an issue with underlying storage
	 */
	Order[] getOrders () throws IOException;

	/**
	 * Finds all {@linkplain Order orders} placed for a given screening.
	 *
	 * @param screeningId The screening id for which to find customer orders.
	 * @return An array of {@link Order orders} whose screening id matches the given number, may be empty
	 * @throws IOException if an issue with underlying storage
	 */
	Order[] findScreeningOrders (int screeningId) throws IOException;

	/**
	 * Finds all {@linkplain Order orders} placed by a given user account.
	 *
	 * @param accountId The account id for which to find customer orders.
	 * @return An array of {@link Order orders} whose account id matches the given number, may be empty
	 * @throws IOException if an issue with underlying storage
	 */
	Order[] findAccountOrders (int accountId) throws IOException;
}
