package com.estore.api.estoreapi.sodas.persistence;

import com.estore.api.estoreapi.sodas.model.Soda;
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
 * Implements the functionality for JSON file-based persistence for Sodas.<p>
 * {@literal @}Component Spring annotation instantiates a single instance of this
 * class and injects the instance into other classes as needed
 *
 * @author Group 3C, The Code Monkeys
 */
@Component
public class SodaJSONDAO implements SodaDAO {
	/** A local cache of Soda objects, to avoid reading from file each time. */
	Map<Integer, Soda> sodas;

	/** TODO: Add description of the purpose of Logger, once it's actually used. */
	private static final Logger LOG = Logger.getLogger(SodaJSONDAO.class.getName());

	/** The next id to assign to a new soda. */
	private static int nextId;

	/** Name of the file to read and write to. */
	private final String filename;
	/** Provides conversion between Java Soda and JSON Soda objects. */
	private final ObjectMapper objectMapper;

	/**
	 * Creates a Data Access Object for JSON-based Sodas.
	 *
	 * @param filename     Filename to read from and write to
	 * @param objectMapper Provides JSON Object to/from Java Object serialization and deserialization
	 * @throws IOException when file cannot be accessed or read from
	 */
	public SodaJSONDAO (@Value("${sodas.file}") String filename, ObjectMapper objectMapper) throws IOException {
		this.filename = filename;
		this.objectMapper = objectMapper;
		load();  // load the sodas from the file
	}

	/**
	 * Generates the next id for a new {@linkplain Soda soda}.
	 *
	 * @return The next soda id
	 */
	private synchronized static int nextId () {
		int id = nextId;
		++nextId;
		return id;
	}

	/**
	 * Generates an array of {@linkplain Soda sodas} from the tree map.
	 *
	 * @return The array of {@link Soda sodas}, may be empty
	 */
	private Soda[] getSodasArray () {
		return getSodasArray(null);
	}

	/**
	 * Generates an array of {@linkplain Soda sodas} from the tree map for any
	 * {@linkplain Soda sodas} that contains the soda name specified by text argument.
	 *
	 * @param text The text to find within a {@link Soda sodas} soda<p>
	 *             If text is null, the array contains all of the {@linkplain Soda sodas} in the tree map.
	 * @return The array of {@link Soda sodas}, may be empty
	 */
	private Soda[] getSodasArray (String text) {
		ArrayList<Soda> sodaArrayList = new ArrayList<>();

		for (Soda soda : sodas.values()) {
			if (text == null || soda.getTitle().contains(text)) {
				sodaArrayList.add(soda);
			}
		}

		Soda[] sodaArray = new Soda[sodaArrayList.size()];
		sodaArrayList.toArray(sodaArray);
		return sodaArray;
	}

	/**
	 * Saves the {@linkplain Soda sodas} from the map into the file as an array of JSON objects.
	 *
	 * @return true if the {@link Soda sodas} were written successfully
	 * @throws IOException when file cannot be accessed or written to
	 */
	private boolean save () throws IOException {
		Soda[] sodaArray = getSodasArray();

		// Serializes the Java Objects to JSON objects into the file,
		// writeValue will throw an IOException if there is an issue with or reading from the file
		objectMapper.writeValue(new File(filename), sodaArray);
		return true;
	}

	/**
	 * Loads {@linkplain Soda sodas} from the JSON file into the map.<br>
	 * Also sets this object's nextId to one more than the greatest id found in the file.
	 *
	 * @return true if the file was read successfully
	 * @throws IOException when file cannot be accessed or read from
	 */
	private boolean load () throws IOException {
		sodas = new TreeMap<>();
		nextId = 0;

		// Deserializes the JSON objects from the file into an array of sodas,
		// readValue will throw an IOException if there's an issue with or reading from the file
		Soda[] sodaArray = objectMapper.readValue(new File(filename), Soda[].class);

		// Add each soda to the tree map and keep track of the greatest id
		for (Soda soda : sodaArray) {
			sodas.put(soda.getId(), soda);
			if (soda.getId() > nextId) {
				nextId = soda.getId();
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
	public Soda createSoda (Soda soda) throws IOException {
		synchronized (sodas) {
			// We create a new soda object because the id field is immutable, and we need to assign the next unique id
			Soda newSoda = new Soda(nextId(), soda.getTitle(), soda.getPoster());
			sodas.put(newSoda.getId(), newSoda);
			save(); // may throw an IOException
			return newSoda;
		}
	}

	/**
	 * * {@inheritDoc}
	 */
	@Override
	public Soda updateSoda (Soda soda) throws IOException {
		synchronized (sodas) {
			if (!sodas.containsKey(soda.getId())) {
				return null;  // soda does not exist
			}

			sodas.put(soda.getId(), soda);
			save(); // may throw an IOException
			return soda;
		}
	}

	/**
	 * * {@inheritDoc}
	 */
	@Override
	public boolean deleteSoda (int id) throws IOException {
		synchronized (sodas) {
			if (sodas.containsKey(id)) {
				sodas.remove(id);
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
	public Soda getSoda (int id) {
		synchronized (sodas) {
			if (sodas.containsKey(id)) {
				return sodas.get(id);
			} else {
				return null;
			}
		}
	}

	/**
	 * * {@inheritDoc}
	 */
	@Override
	public Soda[] getSodas () {
		synchronized (sodas) {
			return getSodasArray();
		}
	}

	/**
	 * * {@inheritDoc}
	 */
	@Override
	public Soda[] findSodas (String text) {
		synchronized (sodas) {
			return getSodasArray(text);
		}
	}
}
