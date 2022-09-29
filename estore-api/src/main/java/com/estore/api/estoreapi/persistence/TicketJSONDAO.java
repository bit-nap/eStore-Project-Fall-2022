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
 * Implements the functionality for JSON file-based persistence for Tickets.
 * <p>
 * {@literal @}Component Spring annotation instantiates a single instance of this
 * class and injects the instance into other classes as needed
 *
 * @author Group 3C, The Code Monkeys
 */
@Component
public class TicketJSONDAO implements TicketDAO {
	/** TODO: Add description of the purpose of Logger, once it's actually used. */
	private static final Logger LOG = Logger.getLogger(TicketJSONDAO.class.getName());
	/** A local cache of ticket objects, to avoid reading from file each time. */
	Map<Integer, Ticket> tickets;
	/** Provides conversion between Java Ticket and JSON Ticket objects. */
	private ObjectMapper objectMapper;
	/** The next id to assign to a new ticket. */
	private static int nextId;
	/** Name of the file to read and write to. */
	private String filename;

	/**
	 * Creates a Data Access Object for JSON-based Tickets.
	 *
	 * @param filename     Filename to read from and write to
	 * @param objectMapper Provides JSON Object to/from Java Object serialization and deserialization
	 * @throws IOException when file cannot be accessed or read from
	 */
	public TicketJSONDAO (@Value("${tickets.file}") String filename, ObjectMapper objectMapper) throws IOException {
		this.filename = filename;
		this.objectMapper = objectMapper;
		load();  // load the tickets from the file
	}

	/**
	 * Generates the next id for a new {@linkplain Ticket ticket}.
	 *
	 * @return The next ticket id
	 */
	private synchronized static int nextId () {
		int id = nextId;
		++nextId;
		return id;
	}

	/**
	 * Generates an array of {@linkplain Ticket tickets} from the tree map.
	 *
	 * @return The array of {@link Ticket tickets}, may be empty
	 */
	private Ticket[] getTicketsArray () {
		return getTicketsArray(null);
	}

	/**
	 * Generates an array of {@linkplain Ticket tickets} from the tree map for any
	 * {@linkplain Ticket tickets} that contains the movie title specified by text argument.
	 *
	 * @param text The text to find within a {@link Ticket ticket's} movie
	 *             <p>
	 *             If text is null, the array contains all of the {@linkplain Ticket tickets} in the tree map.
	 * @return The array of {@link Ticket tickets}, may be empty
	 */
	private Ticket[] getTicketsArray (String text) {
		ArrayList<Ticket> ticketArrayList = new ArrayList<>();

		for (Ticket ticket : tickets.values()) {
			if (text == null || ticket.getMovie().contains(text)) {
				ticketArrayList.add(ticket);
			}
		}

		Ticket[] ticketArray = new Ticket[ticketArrayList.size()];
		ticketArrayList.toArray(ticketArray);
		return ticketArray;
	}

	/**
	 * Saves the {@linkplain Ticket tickets} from the map into the file as an array of JSON objects.
	 *
	 * @return true if the {@link Ticket tickets} were written successfully
	 * @throws IOException when file cannot be accessed or written to
	 */
	private boolean save () throws IOException {
		Ticket[] ticketArray = getTicketsArray();

		// Serializes the Java Objects to JSON objects into the file
		// writeValue will throw an IOException if there is an issue
		// with the file or reading from the file
		objectMapper.writeValue(new File(filename), ticketArray);
		return true;
	}

	/**
	 * Loads {@linkplain Ticket tickets} from the JSON file into the map.
	 * <br>
	 * Also sets this object's nextId to one more than the greatest id found in the file.
	 *
	 * @return true if the file was read successfully
	 * @throws IOException when file cannot be accessed or read from
	 */
	private boolean load () throws IOException {
		tickets = new TreeMap<>();
		nextId = 0;

		// Deserializes the JSON objects from the file into an array of tickets
		// readValue will throw an IOException if there's an issue with the file
		// or reading from the file
		Ticket[] ticketArray = objectMapper.readValue(new File(filename), Ticket[].class);

		// Add each ticket to the tree map and keep track of the greatest id
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
			return getTicketsArray();
		}
	}

	/**
	 * * {@inheritDoc}
	 */
	@Override
	public Ticket[] findTickets (String text) {
		synchronized (tickets) {
			return getTicketsArray(text);
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
	public Ticket createTicket (Ticket ticket) throws IOException {
		synchronized (tickets) {
			// We create a new ticket object because the id field is immutable,
			// and we need to assign the next unique id
			Ticket newTicket = new Ticket(nextId(), ticket.getMovie());
			tickets.put(newTicket.getId(), newTicket);
			save(); // may throw an IOException
			return newTicket;
		}
	}

	/**
	 * * {@inheritDoc}
	 */
	@Override
	public Ticket updateTicket (Ticket ticket) throws IOException {
		synchronized (tickets) {
			if (!tickets.containsKey(ticket.getId())) {
				return null;  // ticket does not exist
			}

			tickets.put(ticket.getId(), ticket);
			save(); // may throw an IOException
			return ticket;
		}
	}

	/**
	 * * {@inheritDoc}
	 */
	@Override
	public boolean deleteTicket (int id) throws IOException {
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
