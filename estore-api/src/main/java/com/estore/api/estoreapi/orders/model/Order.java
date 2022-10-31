package com.estore.api.estoreapi.orders.model;

/**
 * Represents a customer order. Fields cannot be changed once created.
 *
 * @author Group 3C, The Code Monkeys
 */
public class Order {
	/** The id of this order. */
	private final int id;

	/** The id of the Screening this order was placed for. */
	private final int screeningId;
	/** The id of the user Account that placed this order. */
	private final int accountId;

	/** The number of tickets purchased. */
	private final int tickets;
	/** An array of popcorn bags purchased, seperated by size: [small, medium, large]. */
	private final int[] popcorn;
	/** An array of sodas purchased, seperated by size: [small, medium, large]. */
	private final int[] soda;

	/**
	 * Create an Order object with the given information.
	 *
	 * @param id          The id of this order
	 * @param screeningId The id of the Screening this order was placed for
	 * @param accountId   The id of the user Account that placed this order
	 * @param tickets     Number of tickets purchased
	 * @param popcorn     Array of popcorn bags purchased
	 * @param soda        Array of sodas purchased
	 */
	public Order (int id, int screeningId, int accountId, int tickets, int[] popcorn, int[] soda) {
		this.id = id;
		this.screeningId = screeningId;
		this.accountId = accountId;
		this.tickets = tickets;
		this.popcorn = popcorn;
		this.soda = soda;
	}

	/**
	 * Was this order placed for the Screening with the given id?
	 *
	 * @param id The Screening id to compare to
	 * @return True if this order's screening id is the same as the given id, else False
	 */
	public boolean screeningIdIs (int id) {
		return screeningId == id;
	}

	/**
	 * Was this order placed by the Account with the given id?
	 *
	 * @param id The Account id to compare to
	 * @return True if this order's account id is the same as the given id, else False
	 */
	public boolean accountIdIs (int id) {
		return accountId == id;
	}

	/**
	 * @return The id of this order
	 */
	public int getId () {
		return id;
	}

	/**
	 * @return The id of the Screening this order was placed for
	 */
	public int getScreeningId () {
		return screeningId;
	}

	/**
	 * @return The id of the user Account that placed this order
	 */
	public int getAccountId () {
		return accountId;
	}

	/**
	 * @return The number of tickets purchased
	 */
	public int getTickets () {
		return tickets;
	}

	/**
	 * @return An array of popcorn bags purchased, seperated by size: [small, medium, large]
	 */
	public int[] getPopcorn () {
		return popcorn;
	}

	/**
	 * @return An array of sodas purchased, seperated by size: [small, medium, large]
	 */
	public int[] getSoda () {
		return soda;
	}
}
