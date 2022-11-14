package com.estore.api.estoreapi.accounts.model;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Represents an account that may be logged in to
 *
 * @author Group 3C, The Code Monkeys
 */
public class Account {
	static final String STRING_FORMAT = "Account [id=%d, username=%s, password=%s]";

	/** The id of this account */
	@JsonProperty("id") private int id;
	/** The username of this account */
	@JsonProperty("username") private String username;
	/** The password of this account (in plaintext) */
	@JsonProperty("password") private String password;

	/**
	 * Create an Account with a username and password
	 *
	 * @param id 		The id of the account
	 * @param username	The username of the account
	 * @param password	The password of the account
	 */
	public Account(@JsonProperty("id") int id, @JsonProperty("username") String username, @JsonProperty("password") String password) {
		this.id = id;
		this.username = username;
		this.password = password;
	}

	/**
	 * @return The id of this Account
	 */
	public int getId() {
		return id;
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
	 * Set the username of the account. Used in change username of account functionality later on.
	 *
	 * @param name the String that will be set as the username
	 */
	public void setName(String name) {
		this.username = name;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String toString() {
		return String.format(STRING_FORMAT, id, username, password);
	}
}
