package com.estore.api.estoreapi.orders.model;

/**
 * Represents a customer order. Fields cannot be changed once created.
 *
 * @author Group 3C, The Code Monkeys
 */
public class Order {
	/** The id of the Screening this order was placed for. */
	private final int screeningId;
	/** The number of tickets purchased. */
	private final int tickets;
	/** An array of popcorn bags purchased, seperated by size: [small, medium, large]. */
	private final int[] popcorn;
	/** An array of sodas purchased, seperated by size: [small, medium, large]. */
	private final int[] soda;

	/**
	 * Create an Order object with the given information.
	 *
	 * @param tickets Number of tickets purchased
	 * @param popcorn Array of popcorn bags purchased
	 * @param soda Array of sodas purchased
	 */
	public Order(int screeningId, int tickets, int[] popcorn, int[] soda) {
		this.screeningId = screeningId;
		this.tickets = tickets;
		this.popcorn = popcorn;
		this.soda = soda;
	}

	/**
	 * @return The id of the Screening this order was placed for
	 */
	public int getScreeningId () {
		return screeningId;
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
