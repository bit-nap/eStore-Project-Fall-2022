package com.estore.api.estoreapi.orders.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Arrays;

/**
 * Represents a customer order. Fields cannot be changed once created.
 *
 * @author Group 3C, The Code Monkeys
 */
public class Order {
	// Package private for tests - Prof
	static final String STRING_FORMAT = "Order [id=%d, screeningId=%d, accountId=%d, tickets=%d," +
		"popcornSmall=%d, popcornMedium=%d, popcornLarge=%d, sodaSmall=%d, sodaMedium=%d, sodaLarge=%d, seats=%s]";

	/** The id of this order. */
	@JsonProperty("id") private final int id;

	/** The id of the Screening this order was placed for. */
	@JsonProperty("screeningId") private final int screeningId;
	/** The id of the user Account that placed this order. */
	@JsonProperty("accountId") private final int accountId;

	/** The number of tickets purchased. */
	@JsonProperty("tickets") private final int tickets;
	/** An array of popcorn bags purchased, seperated by size: [small, medium, large]. */
	@JsonProperty("popcorn") private final int[] popcorn;
	/** An array of sodas purchased, seperated by size: [small, medium, large]. */
	@JsonProperty("soda") private final int[] soda;

	/**
	 * An array of strings representing the coordinates of the seats reserved for each ticket purchased in this order.
	 * Part of our 10% feature.
	 */
	@JsonProperty("seats") private final String[] seats;

	/**
	 * Create an Order object with the given information.
	 *
	 * @param id          The id of this order
	 * @param screeningId The id of the Screening this order was placed for
	 * @param accountId   The id of the user Account that placed this order
	 * @param tickets     Number of tickets purchased
	 * @param popcorn     Array of popcorn bags purchased
	 * @param soda        Array of sodas purchased
	 * @param seats       Array of seats reserved
	 */
	public Order (@JsonProperty("id") int id, @JsonProperty("screeningId") int screeningId, @JsonProperty("accountId") int accountId,
	              @JsonProperty("tickets") int tickets, @JsonProperty("popcorn") int[] popcorn, @JsonProperty("soda") int[] soda,
	              @JsonProperty("seats") String[] seats) {
		this.id = id;
		this.screeningId = screeningId;
		this.accountId = accountId;
		this.tickets = tickets;
		this.popcorn = popcorn;
		this.soda = soda;
		this.seats = seats;
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

	/**
	 * @return An array of seats reserved for each ticket purchased
	 */
	public String[] getSeats () {
		return seats;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String toString () {
		return String.format(STRING_FORMAT, id, screeningId, accountId, tickets,
		                     popcorn[0], popcorn[1], popcorn[2], soda[0], soda[1], soda[2], Arrays.toString(seats));
	}
}
