package com.estore.api.estoreapi.screenings.persistence;

import com.estore.api.estoreapi.movies.MovieGetter;
import com.estore.api.estoreapi.screenings.model.Screening;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;
import java.util.*;
import java.util.logging.Logger;

/**
 * Implements the functionality for JSON file-based persistence for Screenings.<p>
 * {@literal @}Component Spring annotation instantiates a single instance of this
 * class and injects the instance into other classes as needed
 *
 * @author Group 3C, The Code Monkeys
 */
@Component
public class ScreeningJSONDAO implements ScreeningDAO {
	/** A local cache of Screening objects, to avoid reading from file each time. */
	Map<Integer, Screening> screenings;

	/** TODO: Add description of the purpose of Logger, once it's actually used. */
	private static final Logger LOG = Logger.getLogger(ScreeningJSONDAO.class.getName());

	/** The next id to assign to a new screening. */
	private static int nextId;

	/** Name of the file to read and write to. */
	private final String filename;
	/** Provides conversion between Java Screening and JSON Screening objects. */
	private final ObjectMapper objectMapper;
	/** Provides Screening objects with Movie objects based on their movieId field. */
	private final MovieGetter movieGetter;

	/**
	 * Creates a Data Access Object for JSON-based Screenings.
	 *
	 * @param filename     Filename to read from and write to
	 * @param objectMapper Provides JSON Object to/from Java Object serialization and deserialization
	 * @throws IOException when file cannot be accessed or read from
	 */
	public ScreeningJSONDAO (@Value("${screenings.file}") String filename, ObjectMapper objectMapper, MovieGetter movieGetter) throws IOException {
		this.filename = filename;
		this.objectMapper = objectMapper;
		this.movieGetter = movieGetter;
		load();  // load the screenings from the file
	}

	/**
	 * Generates the next id for a new {@linkplain Screening screening}.
	 *
	 * @return The next screening id
	 */
	private synchronized static int nextId () {
		int id = nextId;
		++nextId;
		return id;
	}

	/**
	 * Generates an array of {@linkplain Screening screenings} from the tree map.
	 *
	 * @return The array of {@link Screening screenings}, may be empty
	 */
	private Screening[] getScreeningsArray () {
		return getScreeningsArray(null);
	}

	/**
	 * Generates an array of {@linkplain Screening screenings} from the tree map for any
	 * {@linkplain Screening screenings} that contains the movie title specified by text argument,
	 * sorted by their Screening date and time.
	 *
	 * @param text The text to find within a {@link Screening screenings} screening<p>
	 *             If text is null, the array contains all of the {@linkplain Screening screenings} in the tree map.
	 * @return The array of {@link Screening screenings}, may be empty
	 */
	private Screening[] getScreeningsArray (String text) {
		List<Screening> screeningArrayList = new ArrayList<>();

		for (Screening screening : screenings.values()) {
			if (text == null || screening.movieTitleContains(text)) {
				screeningArrayList.add(screening);
			}
		}

		Screening[] screeningArray = new Screening[screeningArrayList.size()];
		Collections.sort(screeningArrayList);
		screeningArrayList.toArray(screeningArray);
		return screeningArray;
	}

	/**
	 * Generates an array of {@linkplain Screening screenings} from the tree map for any
	 * {@linkplain Screening screenings} that is screening the movie specified by movieId argument,
	 * sorted by their Screening date and time.
	 *
	 * @param movieId The movie to find within all {@link Screening screenings}
	 * @return The array of {@link Screening screenings}, may be empty
	 */
	private Screening[] getScreeningsArrayForMovie (int movieId) {
		List<Screening> screeningArrayList = new ArrayList<>();

		for (Screening screening : screenings.values()) {
			if (screening.movieIdIs(movieId)) {
				screeningArrayList.add(screening);
			}
		}

		Screening[] screeningArray = new Screening[screeningArrayList.size()];
		Collections.sort(screeningArrayList);
		screeningArrayList.toArray(screeningArray);
		return screeningArray;
	}

	/**
	 * Saves the {@linkplain Screening screenings} from the map into the file as an array of JSON objects.
	 *
	 * @return true if the {@link Screening screenings} were written successfully
	 * @throws IOException when file cannot be accessed or written to
	 */
	private boolean save () throws IOException {
		Screening[] screeningArray = getScreeningsArray();

		// Serializes the Java Objects to JSON objects into the file,
		// writeValue will throw an IOException if there is an issue with or reading from the file
		objectMapper.writeValue(new File(filename), screeningArray);
		return true;
	}

	/**
	 * Loads {@linkplain Screening screenings} from the JSON file into the map.<br>
	 * Also sets this object's nextId to one more than the greatest id found in the file.
	 *
	 * @return true if the file was read successfully
	 * @throws IOException when file cannot be accessed or read from
	 */
	private boolean load () throws IOException {
		screenings = new TreeMap<>();
		nextId = 0;

		// Deserializes the JSON objects from the file into an array of screenings,
		// readValue will throw an IOException if there's an issue with or reading from the file
		Screening[] screeningArray = objectMapper.readValue(new File(filename), Screening[].class);

		// Add each screening to the tree map and keep track of the greatest id
		for (Screening screening : screeningArray) {
			screening.setMovieGetter(movieGetter);
			screenings.put(screening.getId(), screening);
			if (screening.getId() > nextId) {
				nextId = screening.getId();
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
	public Screening createScreening (Screening screening) throws IOException {
		synchronized (screenings) {
			// We create a new screening object because the id field is immutable, and we need to assign the next unique id
			Screening newScreening = new Screening(nextId(), screening.getMovieId(), screening.getTicketsRemaining(), screening.getDate(),
			                                       screening.getTime());
			newScreening.setMovieGetter(movieGetter);
			screenings.put(newScreening.getId(), newScreening);
			save(); // may throw an IOException
			return newScreening;
		}
	}

	/**
	 * * {@inheritDoc}
	 */
	@Override
	public Screening updateScreening (Screening screening) throws IOException {
		synchronized (screenings) {
			if (!screenings.containsKey(screening.getId())) {
				return null;  // screening does not exist
			}

			screenings.put(screening.getId(), screening);
			save(); // may throw an IOException
			return screening;
		}
	}

	/**
	 * * {@inheritDoc}
	 */
	@Override
	public boolean deleteScreening (int id) throws IOException {
		synchronized (screenings) {
			if (screenings.containsKey(id)) {
				screenings.remove(id);
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
	public Screening getScreening (int id) {
		synchronized (screenings) {
			if (screenings.containsKey(id)) {
				return screenings.get(id);
			} else {
				return null;
			}
		}
	}

	/**
	 * * {@inheritDoc}
	 */
	@Override
	public Screening[] getScreenings () {
		synchronized (screenings) {
			return getScreeningsArray();
		}
	}

	/**
	 * * {@inheritDoc}
	 */
	@Override
	public Screening[] findScreenings (String text) {
		synchronized (screenings) {
			return getScreeningsArray(text);
		}
	}

	/**
	 * * {@inheritDoc}
	 */
	@Override
	public Screening[] findScreeningsForMovie (int movieId) {
		synchronized (screenings) {
			return getScreeningsArrayForMovie(movieId);
		}
	}
}
