package com.estore.api.estoreapi.accounts.model;

import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Unit tests for the Account Model
 *
 * @author Group 3C, The Code Monkeys
 */
@Tag("Model-tier")
class AccountTest {
	/**
	 * Tests the Account constructor to make sure a proper Account object
	 * is instantiated with the proper arguments. This also tests the get
	 * methods in the Account class.
	 */
	@Test
	void testConstructor () {
		// Arrange
		int id = 14;
		String username = "admin";
		String password = "password";

		// Invoke
		Account testAccount = new Account(id, username, password);

		// Analyze
		assertEquals(id, testAccount.getId());
		assertEquals(username, testAccount.getUsername());
		assertEquals(password, testAccount.getPassword());
	}

	@Test
	void testSetName () {
		// Arrange
		Account testAccount = new Account(16, "oldUsername", "password");

		// Invoke
		testAccount.setName("newUsername");

		// Analyze
		assertEquals("newUsername", testAccount.getUsername());
	}

	@Test
	void testToString () {
		// Arrange
		int id = 14;
		String username = "admin";
		String password = "password";
		String expected_string = String.format(Account.STRING_FORMAT, id, username, password);

		// Invoke
		Account testAccount = new Account(id, username, password);

		// Analyze
		assertEquals(expected_string, testAccount.toString());
	}
}
