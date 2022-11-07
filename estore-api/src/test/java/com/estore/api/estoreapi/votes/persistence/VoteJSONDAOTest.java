package com.estore.api.estoreapi.votes.persistence;

import com.estore.api.estoreapi.votes.model.Vote;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.mockito.listeners.MockitoListener;

import java.io.File;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

/**
 * Test for the Vote DAO class
 */
@Tag("Persistence-tier")
public class VoteJSONDAOTest {
	VoteJSONDAO voteFileDAO;
	Vote[] testVotes;
	ObjectMapper mockObjectMapper;

	/**
	 * Before each test, create and inject a mock object mapper to isolaste the tests from the underlying file
	 * @throws IOException
	 */
	@BeforeEach
	public void setupVoteJSONDAO () throws IOException {
		mockObjectMapper = mock(ObjectMapper.class);
		testVotes = new Vote[3];
		testVotes[0] = new Vote(100, "Star Wars: Episode IV – A New Hope", 10);
		testVotes[1] = new Vote(101, "Star Wars: Episode V – The Empire Strikes Back", 200);
		testVotes[2] = new Vote(201, "Star Wars: Episode VI - Return of the Jedi", 30);

		// when the object mapper is supposed to read fomr the file, the mock object mapper will return the vote array above
		when(mockObjectMapper.readValue(new File("mao-zedongs-little-red-book.epub"), Vote[].class)).thenReturn(testVotes);
		voteFileDAO = new VoteJSONDAO("mao-zedongs-little-red-book.epub", mockObjectMapper);
	}

	/**
	 * Test if getting the votes works as intended
	 */
	@Test
	public void testGetVotes () {
		// Invoke
		Vote[] votes = votesFileDAO.getVotes();

		// Analyze
		assertEquals(votes.length, testVotes.length);
		for (int i = 0; i < testVotes.length; ++i) {
			assertEquals(votes[i], testVotes[i]);
		}
	}

	@Test
	public void testGetVote () {
		// Invoke
		Vote vote = voteFileDAO.getVote("Star Wars: Episode IV – A New Hope");

		// Analyze
		assertEquals(vote, testVotes[0]);
	}

	@Test
	public void testDeleteVote () {
		// Invoke
		boolean result = assertDoesNotThrow(() -> voteFileDAO.deleteVote("Star Wars: Episode IV – A New Hope"), "Unexpected exception thrown");

		// Analyze
		assertTrue(result);
		// We check the internal tree map size against the length of the test votes array - 1 (because of the delete function call)
		// Because votes attribute of voteJSONDAO is package private we can access it directly
		assertEquals(voteFileDAO.votes.size(), testVotes.length - 1);
	}

	@Test
	public void testCreateVote () {
		// Setup
		Vote vote = new Vote(107, "Star Wars: The Force Awakens", 10);

		// Invoke
		Vote result = assertDoesNotThrow(() -> voteFileDAO.createVote(vote), "Unexpected exception thrown");

		// Analyze
		assertNotNull(result);
		Vote actual = voteFileDAO.getVote(vote.getId());
		assertEquals(actual.getId(), vote.getId());
		assertEquals(actual.getvoteName(), vote.getvoteName());
		assertEquals(actual.getHowManyVotes(), vote.getHowManyVotes());
	}

	@Test
	public void testUpdateVote () {
		// Setup
		Vote vote = new Vote(104, "Star Wars: The Force Awakens", 100);

		// Invoke
		Vote result = assertDoesNotThrow(() -> voteFileDAO.updateVote(vote), "Unexpected exception thrown");

		// Analyze
		assertNotNull(result);
		Vote actual = voteFileDAO.getVote(vote.getId());
		assertEquals(actual, vote);
	}

	@Test
	public void testSaveException () throws IOException {
		doThrow(new IOException()).when(mockObjectMapper).writeValue(any(File.class), any(Vote[].class));

		Vote vote = new Vote(107, "Star Wars: The Force Awakens", 150);

		assertThrows(IOException.class, () -> voteFileDAO.createVote(vote), "IOException not thrown");
	}

	@Test
	public void testGetVoteNotFound () {
		// Invoke
		Vote vote = voteFileDAO.getVote("Star Wars: The Force Awakens");

		// Analyze
		assertNull(vote);
	}

	@Test
	public void testDeleteVoteNotFound () {
		// Invoke
		boolean result = assertDoesNotThrow(() -> voteFileDAO.deleteVote("Star Wars: The Force Awakens"), "Unexpected exception thrown");

		// Analyze
		assertFalse(result);
		assertEquals(voteFileDAO.votes.size(), testVotes.length);
	}

	@Test
	public void testUpdateVoteNotFound () {
		// Setup
		Vote vote = new Vote(103, "Star Wars: The Force Awakens", 30);

		// Invoke
		Vote result = assertDoesNotThrow(() -> voteFileDAO.updateVote(vote), "Unexpected exception thrown");

		// Analyze
		assertNull(result);
	}

	@Test
	public void testConstructorException () throws IOException {
		// Setup
		ObjectMapper mockObjectMapper = mock(ObjectMapper.class);
		// We want to simulate with a Mock Object Mapper that an exception was raised during JSON object deserialization into Java objects
		// When the Mock Object Mapper readValue method is called from the voteJSONDAO load method, an IOException is raised
		doThrow(new IOException()).when(mockObjectMapper).readValue(new File("mao-zedongs-little-red-book.epub"), Vote[].class);

		// Invoke & Analyze
		assertThrows(IOException.class, () -> new VoteJSONDAO("mao-zedongs-little-red-book.epub", mockObjectMapper), "IOException not thrown");
	}
}
