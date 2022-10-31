package com.estore.api.estoreapi.orders.model;

import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * The unit test suite for the Order class
 *
 * @author Group 3C, The Code Monkeys
 */
@Tag("Model-tier")
public class OrderTest {
	@Test
	public void testScreeningIdIs () {
		int id = 99;
		int screeningId = 1;
		int accountId = 1;
		int tickets = 3;
		int[] popcorn = new int[]{ 2, 0, 1 };
		int[] soda = new int[]{ 1, 1, 1 };

		// Invoke
		Order order = new Order(id, screeningId, accountId, tickets, popcorn, soda);
		assertTrue(order.screeningIdIs(screeningId));
	}

	@Test
	public void testAccountIdIs () {
		int id = 99;
		int screeningId = 1;
		int accountId = 1;
		int tickets = 3;
		int[] popcorn = new int[]{ 2, 0, 1 };
		int[] soda = new int[]{ 1, 1, 1 };

		// Invoke
		Order order = new Order(id, screeningId, accountId, tickets, popcorn, soda);
		assertTrue(order.accountIdIs(accountId));
	}

	@Test
	public void testCtor () {
		// Setup
		int id = 99;
		int screeningId = 1;
		int accountId = 1;
		int tickets = 3;
		int[] popcorn = new int[]{ 2, 0, 1 };
		int[] soda = new int[]{ 1, 1, 1 };

		// Invoke
		Order order = new Order(id, screeningId, accountId, tickets, popcorn, soda);

		// Analyze
		assertEquals(id, order.getId());
		assertEquals(screeningId, order.getScreeningId());
		assertEquals(accountId, order.getAccountId());
		assertEquals(tickets, order.getTickets());
		assertArrayEquals(popcorn, order.getPopcorn());
		assertArrayEquals(soda, order.getSoda());
	}

	@Test
	public void testToString () {
		// Setup
		int id = 99;
		int screeningId = 1;
		int accountId = 1;
		int tickets = 3;
		int[] popcorn = new int[]{ 2, 0, 1 };
		int[] soda = new int[]{ 1, 1, 1 };
		String expected_string = String.format(Order.STRING_FORMAT, id, screeningId, accountId, tickets,
		                                       popcorn[0], popcorn[1], popcorn[2], soda[0], soda[1], soda[2]);

		// Invoke
		Order order = new Order(id, screeningId, accountId, tickets, popcorn, soda);
		String actual_string = order.toString();

		// Analyze
		assertEquals(expected_string, actual_string);
	}
}
