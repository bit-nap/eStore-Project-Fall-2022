package com.estore.api.estoreapi.suggestions.persistence;

import com.estore.api.estoreapi.suggestions.model.Suggestion;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;
import java.util.TreeMap;
import java.util.logging.Logger;

/**
 * Implements the functionality for JSON file-based persistence for Suggestions.<p>
 * {@literal @}Component Spring annotation instantiates a single instance of this
 * class and injects the instance into other classes as needed
 *
 * @author Group 3C, The Code Monkeys
 */
@Component
public class SuggestionJSONDAO implements SuggestionDAO {
	/** A local cache of Suggestion objects, to avoid reading from file each time. */
	Map<Integer, Suggestion> suggestions;

	/** TODO: Add description of the purpose of Logger, once it's actually used. */
	private static final Logger LOG = Logger.getLogger(SuggestionJSONDAO.class.getName());

	/** The next id to assign to a new suggestion. */
	private static int nextId;

	/** Name of the file to read and write to. */
	private final String filename;
	/** Provides conversion between Java Suggestion and JSON Suggestion objects. */
	private final ObjectMapper objectMapper;

	/**
	 * Creates a Data Access Object for JSON-based Suggestions.
	 *
	 * @param filename     Filename to read from and write to
	 * @param objectMapper Provides JSON Object to/from Java Object serialization and deserialization
	 * @throws IOException when file cannot be accessed or read from
	 */
	public SuggestionJSONDAO (@Value("${suggestions.file}") String filename, ObjectMapper objectMapper) throws IOException {
		this.filename = filename;
		this.objectMapper = objectMapper;
		load();  // load the suggestions from the file
	}

	/**
	 * Generates the next id for a new {@linkplain Suggestion suggestion}.
	 *
	 * @return The next suggestion id
	 */
	private synchronized static int nextId () {
		int id = nextId;
		++nextId;
		return id;
	}

	/**
	 * Generates an array of {@linkplain Suggestion suggestions} from the tree map.
	 *
	 * @return The array of {@link Suggestion suggestions}, may be empty
	 */
	private Suggestion[] getSuggestionsArray () {
		ArrayList<Suggestion> suggestionArrayList = new ArrayList<>(suggestions.values());
		Suggestion[] suggestionArray = new Suggestion[suggestionArrayList.size()];
		suggestionArrayList.toArray(suggestionArray);
		return suggestionArray;
	}

	/**
	 * Saves the {@linkplain Suggestion suggestions} from the map into the file as an array of JSON objects.
	 *
	 * @return true if the {@link Suggestion suggestions} were written successfully
	 * @throws IOException when file cannot be accessed or written to
	 */
	private boolean save () throws IOException {
		Suggestion[] suggestionArray = getSuggestionsArray();

		// Serializes the Java Objects to JSON objects into the file,
		// writeValue will throw an IOException if there is an issue with or reading from the file
		objectMapper.writeValue(new File(filename), suggestionArray);
		return true;
	}

	/**
	 * Loads {@linkplain Suggestion suggestions} from the JSON file into the map.<br>
	 * Also sets this object's nextId to one more than the greatest id found in the file.
	 *
	 * @return true if the file was read successfully
	 * @throws IOException when file cannot be accessed or read from
	 */
	private boolean load () throws IOException {
		suggestions = new TreeMap<>();
		nextId = 0;

		// Deserializes the JSON objects from the file into an array of suggestions,
		// readValue will throw an IOException if there's an issue with or reading from the file
		Suggestion[] suggestionArray = objectMapper.readValue(new File(filename), Suggestion[].class);

		// Add each suggestion to the tree map and keep track of the greatest id
		for (Suggestion suggestion : suggestionArray) {
			suggestions.put(suggestion.getId(), suggestion);
			if (suggestion.getId() > nextId) {
				nextId = suggestion.getId();
			}
		}
		// Make the next id one greater than the maximum from the file
		++nextId;
		return true;
	}

	/**
	 * * {@inheritDoc}
	 */
	@Override
	public Suggestion createSuggestion (Suggestion suggestion) throws IOException {
		synchronized (suggestions) {
			// We create a new suggestion object because the id field is immutable, and we need to assign the next unique id
			Suggestion newSuggestion = new Suggestion(nextId(), suggestion.getMovieTitle(), suggestion.getVotes());
			suggestions.put(newSuggestion.getId(), newSuggestion);
			save(); // may throw an IOException
			return newSuggestion;
		}
	}

	/**
	 * * {@inheritDoc}
	 */
	@Override
	public Suggestion updateSuggestion (Suggestion suggestion) throws IOException {
		synchronized (suggestions) {
			if (!suggestions.containsKey(suggestion.getId())) {
				return null;  // suggestion does not exist
			}

			suggestions.put(suggestion.getId(), suggestion);
			save(); // may throw an IOException
			return suggestion;
		}
	}

	/**
	 * * {@inheritDoc}
	 */
	@Override
	public boolean deleteSuggestion (int id) throws IOException {
		synchronized (suggestions) {
			if (suggestions.containsKey(id)) {
				suggestions.remove(id);
				return save();
			} else {
				return false;
			}
		}
	}

	/**
	 * * {@inheritDoc}
	 */
	@Override
	public Suggestion getSuggestion (int id) {
		synchronized (suggestions) {
			if (suggestions.containsKey(id)) {
				return suggestions.get(id);
			} else {
				return null;
			}
		}
	}

	/**
	 * * {@inheritDoc}
	 */
	@Override
	public Suggestion[] getSuggestions () {
		synchronized (suggestions) {
			return getSuggestionsArray();
		}
	}
}
