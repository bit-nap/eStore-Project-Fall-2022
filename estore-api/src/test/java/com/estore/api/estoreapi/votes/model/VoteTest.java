package com.estore.api.estoreapi.votes.model;

import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.IOException;

import org.junit.jupiter.api.BeforeEach;

/**
 * The unit test suit for the Vote class
 */
@Tag("Model-tier")
public class VoteTest {

	/**
	 * Test if the Vote object was made successfully by checking the get commands for each member
	 */
	@Test
	public void testCtor () {
		int id = 99;
		String movieName = "Star Wars: Episode IV – A New Hope";
		int howManyVotes = 10;

		// invoke
		Vote vote = new Vote(id, movieName, howManyVotes);

		// analyze
		assertEquals(id, vote.getId());
		assertEquals(movieName, vote.getMovieName());
		assertEquals(howManyVotes, vote.getHowManyVotes());
	}

	/**
	 * Test if the toString method works as intended
	 */
	@Test
	public void testToString () {
		int id = 99;
		String movieName = "Star Wars: Episode IV – A New Hope";
		int howManyVotes = 10;
		String expectedString = String.format(Vote.STRING_FORMAT, id, movieName, howManyVotes);

		// invoke
		Vote vote = new Vote(id, movieName, howManyVotes);
		String actualString = vote.toString();

		// analyze
		assertEquals(expectedString, actualString);
	}
}
