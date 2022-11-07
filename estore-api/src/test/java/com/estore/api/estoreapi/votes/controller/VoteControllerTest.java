package com.estore.api.estoreapi.votes.controller;

import com.estore.api.estoreapi.votes.model.*;
import com.estore.api.estoreapi.votes.persistence.*;

import net.bytebuddy.agent.VirtualMachine.ForHotSpot.Connection.Response;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

/**
 * The test for the VoteController class
 */
@Tag("Controller-Tier")
public class VoteControllerTest {
	private VoteController voteController;
	private VoteDao mockVoteDao;

	@BeforeEach
	public void setupVoteController () {
		mockVoteDao = mock(voteDao.class);
		voteController = new VoteController(mockVoteDao);
	}

	/**
	 * Method to test if getting a known vote works
	 * @throws IOException if something is wrong with http request
	 */
	@Test
	public void testGetVote () throws IOException {
		// setup
		Vote vote = new Vote(100, "TestMovie", 10);
		//when the same movie name is possed in, our mock DAO will return the vote object
		when(mockVoteDao.getVote(vote.getMovieName())).thenReturn(vote);

		// invoke
		ResponseEntity<Vote> response = voteController.getVote(vote.getMovieName);

		// analyze
		assertEquals(HttpStatus.OK, response.getStatusCode());
		assertEquals(vote, response.getBody());
	}

	/**
	 * Method to test if getting a vote that is known to not exist works
	 * @throws IOException if something is wrong with http request
	 */
	@Test
	public void testGetVoteNotFound () throws IOException {
		// setup
		String name = "random";
		// when the same movie name is passed in, our mock DAO will return null, simulating no vote found
		when(mockVoteDao.getVote(name)).thenReturn(null);

		// invoke
		ResponseEntity<Vote> response = voteController.getVote(name);

		// analyze
		assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
	}

	/**
	 * Method to test if handling an IO exception for a get request works
	 * @throws Exception if something is wrong with http request
	 */
	@Test
	public void testGetMovieHandleException () throws Exception {
		// setup
		String name = "random";
		// when getVote is called on the mock vote DAO, throw an IOException
		doThrow(new IOException()).when(mockVoteDao).getVote(name);

		// invoke
		ResponseEntity<Vote> response = voteController.getVote(name);

		// analyze
		assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
	}

	/**
	 * Method to test if getting a list of votes works
	 * @throws IOException if something goes wrong with the http request
	 */
	@Test
	public void testGetVotes () throws IOException {
		// New list of votes
		Vote[] votes = new Vote[3];
		votes[0] = new Vote(100, "Star Wars: Episode IV – A New Hope", 10);
		votes[1] = new Vote(101, "Star Wars: Episode V – The Empire Strikes Back", 50);
		votes[2] = new Vote(102, "Star Wars: Episode VI - Return of the Jedi", 200);
		// When getVotes is called, return the list of votes from above
		when(mockVoteDao.getVotes()).thenReturn(votes);

		// invoke
		ResponseEntity<Vote[]> response = voteController.getVotes();

		// analyze
		assertEquals(HttpStatus.OK, response.getStatusCode());
		assertEquals(votes, response.getBody());
	}

	/**
	 * Test to make sure the exception is handled when getVotes throws one
	 * @throws IOException if something goes wrong with the http request
	 */
	@Test
	public void testGetVotesHandleException () throws IOException {
		// Throw an exception when the get votes method is called
		doThrow(new IOException()).when(mockVoteDao).getVotes();

		// invoke
		ResponseEntity<Votes[]> response = voteController.getVotes();

		// analyze
		assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
	}

	/**
	 * Test to make sure a vote object can be created successfully
	 * @throws IOException if something goes wrong with the http request
	 */
	@Test
	public void testCreateVote () throws IOException {
		// setup
		Vote vote = new Vote(100, "Star Wars: Episode IV – A New Hope", 100);
		// when createVote is called, return true simulating successful creating and save
		when(mockVoteDao.createVote(vote)).thenReturn(vote);

		// invoke
		ResponseEntity<Vote> response = voteController.createVote(vote);

		// analyze
		assertEquals(HttpStatus.CREATED, response.getStatusCode());
		assertEquals(vote, response.getBody());
	}

	/**
	 * Test if creating the movie with a failure works properly
	 * @throws IOException if something goes wrong with the http request
	 */
	@Test
	public void testCreteVoteFailed () throws IOException {
		// setup
		Vote vote = new Vote(100, "Star Wars: Episode IV – A New Hope", 100);
		// when createVote is called, return false simulating failed creation and save
		when(mockVoteDao.createVote(vote)).thenReturn(null);

		// invoke
		ResponseEntity<Vote> response = voteController.createVote(vote);

		// analyze
		assertEquals(HttpStatus.CONFLICT, repsonse.getStatusCode());
	}

	/**
	 * Test if handling an IOException when creating a new vote works
	 * @throws IOExcpetion if something goes wrong with the http request
	 */
	@Test
	public void testCreateVoteHandledException () throws IOException {
		// setup
		Vote vote = new Vote(100, "Star Wars: Episode IV – A New Hope", 100);
		// when createVote is called, throw an exception
		doThrow(new IOException()).when(mockVoteDao).createVote(vote);

		//invoke
		ResponseEntity<Vote> response = voteController.createVote(vote);

		// analyze
		assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
	}

	/**
	 * Test if updating a vote works successfully
	 * @throws IOException if something goes wrong with the http request
	 */
	@Test
	public void testUpdateVote () throws IOException {
		// setup
		Vote vote = new Vote(100, "Star Wars: Episode IV – A New Hope", 100);
		// when updateVote is called, return true simulating successful update and save
		when(mockVoteDao.updateVote(vote)).thenReturn(vote);

		// invoke
		ResponseEntity<Vote> response = voteController.updateVote(vote);

		// analyze
		assertEquals(HttpStatus.OK, response.getStatusCode());
		assertEquals(vote, response.getBody());
	}

	/**
	 * Test if not finding the exception when creating a vote works as intended
	 * @throws IOException if something goes wrong with the http request
	 */
	@Test
	public void testUpdateVoteExceptionNotFound () throws IOException {
		// setup
		Vote vote = new Vote(100, "Star Wars: Episode IV – A New Hope", 100);
		// when updateVote is called, return false simulating successful update and save
		when(mockVoteDao.updateVote(vote)).thenReturn(null);

		// invoke
		ResponseEntity<Vote> response = voteController.updateVote(vote);

		// analyze
		assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
	}

	/**
	 * Test if handling the IOException when updating a vote works
	 * @throws IOException if something goes wrong with the http request
	 */
	@Test
	public void testUpdateVoteHandleException () throws IOException {
		// setup
		Vote vote = new Vote(100, "Star Wars: Episode IV – A New Hope", 100);
		// when updateVote is called on the mock dao, throw an IOException
		doThrow(new IOException()).when(mockVoteDao).updateVote(vote);

		// invoke
		ResponseEntity<Vote> response = voteController.updateVote(vote);

		// analyze
		assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getBody());
	}

	/**
	 * Test if deleting a vote works as intended
	 * @throws IOException if something goes wrong with the http request
	 */
	@Test
	public void testDeleteVote () throws IOException {
		// Setup
		String name = "Random";
		// when deleteVote is called return true, simulating successful deletion
		when(mockVoteDao.deleteVote(name)).thenReturn(true);

		// Invoke
		ResponseEntity<Vote> response = voteController.deleteVote(name);

		// Analyze
		assertEquals(HttpStatus.OK, response.getStatusCode());
	}

	/**
	 * Test if deleting without handling the exception works as intended
	 * @throws IOException if something goes wrong with the http request
	 */
	@Test
	public void testDeleteVoteNotFound () throws IOException {
		// Setup
		String name = "Random";
		// when deleteVote is called return false, simulating failed deletion
		when(mockVoteDao.deleteVote(name)).thenReturn(false);

		// Invoke
		ResponseEntity<Vote> response = voteController.deleteVote(name);

		// Analyze
		assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
	}

	/**
	 * Test if deleting with handling the exception works as inteded
	 * @throws IOException if something goes wrong with the http request
	 */
	@Test
	public void testDeleteVoteHandleException () throws IOException {
		// Setup
		String name = "Random";
		// When deleteVote is called on the Mock Vote DAO, throw an IOException
		doThrow(new IOException()).when(mockVoteDao).deleteVote(name);

		// Invoke
		ResponseEntity<Vote> response = voteController.deleteVote(name);

		// Analyze
		assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
	}
}
