package com.estore.api.estoreapi.movies.persistence;

import com.estore.api.estoreapi.movies.model.Movie;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;
import java.util.TreeMap;

/**
 * Implements the functionality for JSON file-based persistence for Movies.<p>
 * {@literal @}Component Spring annotation instantiates a single instance of this
 * class and injects the instance into other classes as needed
 *
 * @author Group 3C, The Code Monkeys
 */
@Component
public class MovieJSONDAO implements MovieDAO {
	/** A local cache of Movie objects, to avoid reading from file each time. */
	Map<Integer, Movie> movies;

	/** The next id to assign to a new movie. */
	private static int nextId;

	/** Name of the file to read and write to. */
	private final String filename;
	/** Provides conversion between Java Movie and JSON Movie objects. */
	private final ObjectMapper objectMapper;

	/**
	 * Creates a Data Access Object for JSON-based Movies.
	 *
	 * @param filename     Filename to read from and write to
	 * @param objectMapper Provides JSON Object to/from Java Object serialization and deserialization
	 * @throws IOException when file cannot be accessed or read from
	 */
	public MovieJSONDAO (@Value("${movies.file}") String filename, ObjectMapper objectMapper) throws IOException {
		this.filename = filename;
		this.objectMapper = objectMapper;
		load();  // load the movies from the file
	}

	/**
	 * Generates the next id for a new {@linkplain Movie movie}.
	 *
	 * @return The next movie id
	 */
	private synchronized static int nextId () {
		int id = nextId;
		++nextId;
		return id;
	}

	/**
	 * Generates an array of {@linkplain Movie movies} from the tree map.
	 *
	 * @return The array of {@link Movie movies}, may be empty
	 */
	private Movie[] getMoviesArray () {
		return getMoviesArray(null);
	}

	/**
	 * Generates an array of {@linkplain Movie movies} from the tree map for any
	 * {@linkplain Movie movies} that contains the movie title specified by text argument.
	 *
	 * @param text The text to find within a {@link Movie movies} movie<p>
	 *             If text is null, the array contains all of the {@linkplain Movie movies} in the tree map.
	 * @return The array of {@link Movie movies}, may be empty
	 */
	private Movie[] getMoviesArray (String text) {
		ArrayList<Movie> movieArrayList = new ArrayList<>();

		for (Movie movie : movies.values()) {
			if (text == null || movie.titleContains(text)) {
				movieArrayList.add(movie);
			}
		}

		Movie[] movieArray = new Movie[movieArrayList.size()];
		movieArrayList.toArray(movieArray);
		return movieArray;
	}

	/**
	 * Saves the {@linkplain Movie movies} from the map into the file as an array of JSON objects.
	 *
	 * @return true if the {@link Movie movies} were written successfully
	 * @throws IOException when file cannot be accessed or written to
	 */
	private boolean save () throws IOException {
		Movie[] movieArray = getMoviesArray();

		// Serializes the Java Objects to JSON objects into the file,
		// writeValue will throw an IOException if there is an issue with or reading from the file
		objectMapper.writeValue(new File(filename), movieArray);
		return true;
	}

	/**
	 * Loads {@linkplain Movie movies} from the JSON file into the map.<br>
	 * Also sets this object's nextId to one more than the greatest id found in the file.
	 *
	 * @return true if the file was read successfully
	 * @throws IOException when file cannot be accessed or read from
	 */
	private boolean load () throws IOException {
		movies = new TreeMap<>();
		nextId = 0;

		// Deserializes the JSON objects from the file into an array of movies,
		// readValue will throw an IOException if there's an issue with or reading from the file
		Movie[] movieArray = objectMapper.readValue(new File(filename), Movie[].class);

		// Add each movie to the tree map and keep track of the greatest id
		for (Movie movie : movieArray) {
			movies.put(movie.getId(), movie);
			if (movie.getId() > nextId) {
				nextId = movie.getId();
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
	public Movie createMovie (Movie movie) throws IOException {
		synchronized (movies) {
			// We create a new movie object because the id field is immutable, and we need to assign the next unique id
			Movie newMovie = new Movie(nextId(), movie.getTitle(), movie.getPoster(), movie.getRuntime(), movie.getMpaRating(), movie.getYear());
			movies.put(newMovie.getId(), newMovie);
			save(); // may throw an IOException
			return newMovie;
		}
	}

	/**
	 * * {@inheritDoc}
	 */
	@Override
	public Movie updateMovie (Movie movie) throws IOException {
		synchronized (movies) {
			if (!movies.containsKey(movie.getId())) {
				return null;  // movie does not exist
			}

			movies.put(movie.getId(), movie);
			save(); // may throw an IOException
			return movie;
		}
	}

	/**
	 * * {@inheritDoc}
	 */
	@Override
	public boolean deleteMovie (int id) throws IOException {
		synchronized (movies) {
			if (movies.containsKey(id)) {
				movies.remove(id);
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
	public Movie getMovie (int id) {
		synchronized (movies) {
			if (movies.containsKey(id)) {
				return movies.get(id);
			} else {
				return null;
			}
		}
	}

	/**
	 * * {@inheritDoc}
	 */
	@Override
	public Movie[] getMovies () {
		synchronized (movies) {
			return getMoviesArray();
		}
	}

	/**
	 * * {@inheritDoc}
	 */
	@Override
	public Movie[] findMovies (String text) {
		synchronized (movies) {
			return getMoviesArray(text);
		}
	}
}
