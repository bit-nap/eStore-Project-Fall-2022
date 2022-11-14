package com.estore.api.estoreapi.suggestions.persistence;

import com.estore.api.estoreapi.suggestions.model.Suggestion;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

/**
 * Test the Suggestion File DAO class
 *
 * @author Group 3C, The Code Monkeys
 */
@Tag("Persistence-tier")
public class SuggestionJSONDAOTest {
	SuggestionJSONDAO suggestionFileDAO;
	Suggestion[] testSuggestions;
	ObjectMapper mockObjectMapper;

	/**
	 * Before each test, we will create and inject a Mock Object Mapper to
	 * isolate the tests from the underlying file
	 *
	 * @throws IOException if suggestionFileDAO cannot read from fake file
	 */
	@BeforeEach
	public void setupSuggestionJSONDAO () throws IOException {
		mockObjectMapper = mock(ObjectMapper.class);
		testSuggestions = new Suggestion[3];
		testSuggestions[0] = new Suggestion(104, "Star Wars: Episode IV – A New Hope", 77);
		testSuggestions[1] = new Suggestion(105, "Star Wars: Episode V – The Empire Strikes Back", 80);
		testSuggestions[2] = new Suggestion(106, "Star Wars: Episode VI - Return of the Jedi", 83);

		// When the object mapper is supposed to read from the file the mock object mapper will return the suggestion array above
		when(mockObjectMapper.readValue(new File("mao-zedongs-little-red-book.epub"), Suggestion[].class)).thenReturn(testSuggestions);
		suggestionFileDAO = new SuggestionJSONDAO("mao-zedongs-little-red-book.epub", mockObjectMapper);
	}

	@Test
	public void testGetSuggestions () {
		// Invoke
		Suggestion[] suggestions = suggestionFileDAO.getSuggestions();

		// Analyze
		assertEquals(suggestions.length, testSuggestions.length);
		for (int i = 0; i < testSuggestions.length; ++i) {
			assertEquals(suggestions[i], testSuggestions[i]);
		}
	}

	@Test
	public void testGetSuggestion () {
		// Invoke
		Suggestion suggestion = suggestionFileDAO.getSuggestion(104);

		// Analyze
		assertEquals(suggestion, testSuggestions[0]);
	}

	@Test
	public void testDeleteSuggestion () {
		// Invoke
		boolean result = assertDoesNotThrow(() -> suggestionFileDAO.deleteSuggestion(104), "Unexpected exception thrown");

		// Analyze
		assertTrue(result);
		// We check the internal tree map size against the length of the test suggestions array - 1 (because of the delete function call)
		// Because suggestions attribute of SuggestionJSONDAO is package private we can access it directly
		assertEquals(suggestionFileDAO.suggestions.size(), testSuggestions.length - 1);
	}

	@Test
	public void testCreateSuggestion () {
		// Setup
		Suggestion suggestion = new Suggestion(107, "Star Wars: The Force Awakens", 15);

		// Invoke
		Suggestion result = assertDoesNotThrow(() -> suggestionFileDAO.createSuggestion(suggestion), "Unexpected exception thrown");

		// Analyze
		assertNotNull(result);
		Suggestion actual = suggestionFileDAO.getSuggestion(suggestion.getId());
		assertEquals(actual.getId(), suggestion.getId());
		assertEquals(actual.getMovieTitle(), suggestion.getMovieTitle());
		assertEquals(actual.getVotes(), suggestion.getVotes());
	}

	@Test
	public void testUpdateSuggestion () {
		// Setup
		Suggestion suggestion = new Suggestion(104, "Star Wars: The Force Awakens", 15);

		// Invoke
		Suggestion result = assertDoesNotThrow(() -> suggestionFileDAO.updateSuggestion(suggestion), "Unexpected exception thrown");

		// Analyze
		assertNotNull(result);
		Suggestion actual = suggestionFileDAO.getSuggestion(suggestion.getId());
		assertEquals(actual, suggestion);
	}

	@Test
	public void testSaveException () throws IOException {
		doThrow(new IOException()).when(mockObjectMapper).writeValue(any(File.class), any(Suggestion[].class));

		Suggestion suggestion = new Suggestion(107, "Star Wars: The Force Awakens", 15);

		assertThrows(IOException.class, () -> suggestionFileDAO.createSuggestion(suggestion), "IOException not thrown");
	}

	@Test
	public void testGetSuggestionNotFound () {
		// Invoke
		Suggestion suggestion = suggestionFileDAO.getSuggestion(103);

		// Analyze
		assertNull(suggestion);
	}

	@Test
	public void testDeleteSuggestionNotFound () {
		// Invoke
		boolean result = assertDoesNotThrow(() -> suggestionFileDAO.deleteSuggestion(103), "Unexpected exception thrown");

		// Analyze
		assertFalse(result);
		assertEquals(suggestionFileDAO.suggestions.size(), testSuggestions.length);
	}

	@Test
	public void testUpdateSuggestionNotFound () {
		// Setup
		Suggestion suggestion = new Suggestion(107, "Star Wars: The Force Awakens", 15);

		// Invoke
		Suggestion result = assertDoesNotThrow(() -> suggestionFileDAO.updateSuggestion(suggestion), "Unexpected exception thrown");

		// Analyze
		assertNull(result);
	}

	@Test
	public void testConstructorException () throws IOException {
		// Setup
		ObjectMapper mockObjectMapper = mock(ObjectMapper.class);
		// We want to simulate with a Mock Object Mapper that an exception was raised during JSON object deserialization into Java objects
		// When the Mock Object Mapper readValue method is called from the SuggestionJSONDAO load method, an IOException is raised
		doThrow(new IOException()).when(mockObjectMapper).readValue(new File("mao-zedongs-little-red-book.epub"), Suggestion[].class);

		// Invoke & Analyze
		assertThrows(IOException.class, () -> new SuggestionJSONDAO("mao-zedongs-little-red-book.epub", mockObjectMapper), "IOException not thrown");
	}
}
