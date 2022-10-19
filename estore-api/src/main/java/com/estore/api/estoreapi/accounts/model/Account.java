package com.estore.api.estoreapi.accounts.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.logging.Logger;

/**
 * Represents an account that may be logged in to
 *
 * @author Group 3C, The Code Monkeys
 */
public class Account {
	static final String STRING_FORMAT = "Account [username=%s, password=%s]";

	/** TODO: Add description of the purpose of Logger, once it's actually used. */
	private static final Logger LOG = Logger.getLogger(Account.class.getName());

	/** The username of this account */
	@JsonProperty("username") private String username;
	/** The password of this account (in plaintext) */
	@JsonProperty("password") private String password;

	/**
	 * Create an Account with a username and password
	 *
	 * @param username	The username of the account
	 * @param password	The password of the account
	 */
	public Account(@JsonProperty("username") String username, @JsonProperty("password") String password) {
		this.username = username;
		this.password = password;
	}

	/**
	 * @return The username of this Account
	 */
	public String getUsername() {
		return username;
	}

	/**
	 * @return The password of this Account in plaintext
	 * This is a huge security risk especially for a public method but it's OK :)
	 */
	public String getPassword() {
		return password;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String toString() {
		return String.format(STRING_FORMAT, username, password);
	}
}
