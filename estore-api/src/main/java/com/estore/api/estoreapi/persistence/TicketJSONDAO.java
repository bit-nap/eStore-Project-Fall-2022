package com.estore.api.estoreapi.persistence;

import com.estore.api.estoreapi.model.Ticket;
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
 * Implements the functionality for JSON file-based peristance for Heroes
 * <p>
 * {@literal @}Component Spring annotation instantiates a single instance of this
 * class and injects the instance into other classes as needed
 *
 * @author SWEN Faculty
 */
@Component
public class TicketJSONDAO implements TicketDAO {
	private static final Logger LOG = Logger.getLogger(TicketJSONDAO.class.getName());
	Map<Integer, Ticket> tickets;   // Provides a local cache of the hero objects
	// so that we don't need to read from the file
	// each time
	private ObjectMapper objectMapper;  // Provides conversion between Hero
	// objects and JSON text format written
	// to the file
	private static int nextId;  // The next Id to assign to a new hero
	private String filename;    // Filename to read from and write to

	/**
	 * Creates a Hero File Data Access Object
	 *
	 * @param filename     Filename to read from and write to
	 * @param objectMapper Provides JSON Object to/from Java Object serialization and deserialization
	 * @throws IOException when file cannot be accessed or read from
	 */
	public TicketJSONDAO (@Value("${heroes.file}") String filename, ObjectMapper objectMapper) throws IOException {
		this.filename = filename;
		this.objectMapper = objectMapper;
		load();  // load the heroes from the file
	}

	/**
	 * Generates the next id for a new {@linkplain Movie hero}
	 *
	 * @return The next id
	 */
	private synchronized static int nextId () {
		int id = nextId;
		++nextId;
		return id;
	}

	/**
	 * Generates an array of {@linkplain Movie heroes} from the tree map
	 *
	 * @return The array of {@link Movie heroes}, may be empty
	 */
	private Ticket[] getHeroesArray () {
		return getTicketsArray(null);
	}

	/**
	 * Generates an array of {@linkplain Movie heroes} from the tree map for any
	 * {@linkplain Movie heroes} that contains the text specified by containsText
	 * <br>
	 * If containsText is null, the array contains all of the {@linkplain Movie heroes}
	 * in the tree map
	 *
	 * @return The array of {@link Movie heroes}, may be empty
	 */
	private Ticket[] getHeroesArray (String containsText) { // if containsText == null, no filter
		ArrayList<Ticket> ticketArrayList = new ArrayList<>();

		for (Ticket ticket : tickets.values()) {
			if (containsText == null || ticket.getName().contains(containsText)) {
				ticketArrayList.add(ticket);
			}
		}

		Ticket[] heroArray = new Ticket[ticketArrayList.size()];
		ticketArrayList.toArray(heroArray);
		return heroArray;
	}

	/**
	 * Saves the {@linkplain Movie heroes} from the map into the file as an array of JSON objects
	 *
	 * @return true if the {@link Movie heroes} were written successfully
	 * @throws IOException when file cannot be accessed or written to
	 */
	private boolean save () throws IOException {
		Ticket[] heroArray = getHeroesArray();

		// Serializes the Java Objects to JSON objects into the file
		// writeValue will thrown an IOException if there is an issue
		// with the file or reading from the file
		objectMapper.writeValue(new File(filename), heroArray);
		return true;
	}

	/**
	 * Loads {@linkplain Movie heroes} from the JSON file into the map
	 * <br>
	 * Also sets next id to one more than the greatest id found in the file
	 *
	 * @return true if the file was read successfully
	 * @throws IOException when file cannot be accessed or read from
	 */
	private boolean load () throws IOException {
		tickets = new TreeMap<>();
		nextId = 0;

		// Deserializes the JSON objects from the file into an array of heroes
		// readValue will throw an IOException if there's an issue with the file
		// or reading from the file
		Ticket[] ticketArray = objectMapper.readValue(new File(filename), Ticket[].class);

		// Add each hero to the tree map and keep track of the greatest id
		for (Ticket ticket : ticketArray) {
			tickets.put(ticket.getId(), ticket);
			if (ticket.getId() > nextId) {
				nextId = ticket.getId();
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
	public Ticket[] getTickets () {
		synchronized (tickets) {
			return getHeroesArray();
		}
	}

	/**
	 * * {@inheritDoc}
	 */
	@Override
	public Ticket[] findTickets (String containsText) {
		synchronized (tickets) {
			return getHeroesArray(containsText);
		}
	}

	/**
	 * * {@inheritDoc}
	 */
	@Override
	public Ticket getTicket (int id) {
		synchronized (tickets) {
			if (tickets.containsKey(id)) {
				return tickets.get(id);
			} else {
				return null;
			}
		}
	}

	/**
	 * * {@inheritDoc}
	 */
	@Override
	public Ticket createHero (Ticket ticket) throws IOException {
		synchronized (tickets) {
			// We create a new hero object because the id field is immutable
			// and we need to assign the next unique id
			Ticket newTicket = new Ticket(nextId(), ticket.getName());
			ticket.put(newTicket.getId(), newTicket);
			save(); // may throw an IOException
			return newTicket;
		}
	}

	/**
	 * * {@inheritDoc}
	 */
	@Override
	public Ticket updateHero (Ticket hero) throws IOException {
		synchronized (tickets) {
			if (tickets.containsKey(hero.getId()) == false) {
				return null;  // hero does not exist
			}

			tickets.put(hero.getId(), hero);
			save(); // may throw an IOException
			return hero;
		}
	}

	/**
	 * * {@inheritDoc}
	 */
	@Override
	public boolean deleteHero (int id) throws IOException {
		synchronized (tickets) {
			if (tickets.containsKey(id)) {
				tickets.remove(id);
				return save();
			} else {
				return false;
			}
		}
	}
}
