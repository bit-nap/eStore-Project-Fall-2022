package com.estore.api.estoreapi.orders.persistence;

import com.estore.api.estoreapi.orders.model.Order;
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
 * Implements the functionality for JSON file-based persistence for Orders.<p>
 * {@literal @}Component Spring annotation instantiates a single instance of this
 * class and injects the instance into other classes as needed
 *
 * @author Group 3C, The Code Monkeys
 */
@Component
public class OrderJSONDAO implements OrderDAO {
	/** A local cache of Order objects, to avoid reading from file each time. */
	Map<Integer, Order> orders;

	/** TODO: Add description of the purpose of Logger, once it's actually used. */
	private static final Logger LOG = Logger.getLogger(OrderJSONDAO.class.getName());

	/** The next id to assign to a new order. */
	private static int nextId;

	/** Name of the file to read and write to. */
	private final String filename;
	/** Provides conversion between Java Order and JSON Order objects. */
	private final ObjectMapper objectMapper;

	/**
	 * Creates a Data Access Object for JSON-based Orders.
	 *
	 * @param filename     Filename to read from and write to
	 * @param objectMapper Provides JSON Object to/from Java Object serialization and deserialization
	 * @throws IOException when file cannot be accessed or read from
	 */
	public OrderJSONDAO (@Value("${orders.file}") String filename, ObjectMapper objectMapper) throws IOException {
		this.filename = filename;
		this.objectMapper = objectMapper;
		load();  // load the orders from the file
	}

	/**
	 * Generates the next id for a new {@linkplain Order order}.
	 *
	 * @return The next order id
	 */
	private synchronized static int nextId () {
		int id = nextId;
		++nextId;
		return id;
	}

	/**
	 * Generates an array of {@linkplain Order orders} from the tree map.
	 *
	 * @return The array of {@link Order orders}, may be empty
	 */
	private Order[] getOrdersArray () {
		ArrayList<Order> orderArrayList = new ArrayList<>(orders.values());
		Order[] orderArray = new Order[orderArrayList.size()];
		orderArrayList.toArray(orderArray);
		return orderArray;
	}

	/**
	 * Generates an array of {@linkplain Order orders} from the tree map for any
	 * {@linkplain Order orders} that has the given screening id.
	 *
	 * @param screeningId The screening id to find within a {@link Order orders} order.<p>
	 * @return The array of {@link Order orders}, may be empty
	 */
	private Order[] getScreeningOrdersArray (int screeningId) {
		ArrayList<Order> orderArrayList = new ArrayList<>();

		for (Order order : orders.values()) {
			if (order.screeningIdIs(screeningId)) {
				orderArrayList.add(order);
			}
		}

		Order[] orderArray = new Order[orderArrayList.size()];
		orderArrayList.toArray(orderArray);
		return orderArray;
	}

	/**
	 * Generates an array of {@linkplain Order orders} from the tree map for any
	 * {@linkplain Order orders} that has the given account id.
	 *
	 * @param accountId The account id to find within a {@link Order orders} order.<p>
	 * @return The array of {@link Order orders}, may be empty
	 */
	private Order[] getAccountOrdersArray (int accountId) {
		ArrayList<Order> orderArrayList = new ArrayList<>();

		for (Order order : orders.values()) {
			if (order.accountIdIs(accountId)) {
				orderArrayList.add(order);
			}
		}

		Order[] orderArray = new Order[orderArrayList.size()];
		orderArrayList.toArray(orderArray);
		return orderArray;
	}

	/**
	 * Saves the {@linkplain Order orders} from the map into the file as an array of JSON objects.
	 *
	 * @return true if the {@link Order orders} were written successfully
	 * @throws IOException when file cannot be accessed or written to
	 */
	private boolean save () throws IOException {
		Order[] orderArray = getOrdersArray();

		// Serializes the Java Objects to JSON objects into the file,
		// writeValue will throw an IOException if there is an issue with or reading from the file
		objectMapper.writeValue(new File(filename), orderArray);
		return true;
	}

	/**
	 * Loads {@linkplain Order orders} from the JSON file into the map.<br>
	 * Also sets this object's nextId to one more than the greatest id found in the file.
	 *
	 * @return true if the file was read successfully
	 * @throws IOException when file cannot be accessed or read from
	 */
	private boolean load () throws IOException {
		orders = new TreeMap<>();
		nextId = 0;

		// Deserializes the JSON objects from the file into an array of orders,
		// readValue will throw an IOException if there's an issue with or reading from the file
		Order[] orderArray = objectMapper.readValue(new File(filename), Order[].class);

		// Add each order to the tree map and keep track of the greatest id
		for (Order order : orderArray) {
			orders.put(order.getId(), order);
			if (order.getId() > nextId) {
				nextId = order.getId();
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
	public Order createOrder (Order order) throws IOException {
		synchronized (orders) {
			// We create a new order object because the id field is immutable, and we need to assign the next unique id
			Order newOrder = new Order(nextId(), order.getScreeningId(), order.getAccountId(), order.getTickets(), order.getPopcorn(),
			                           order.getSoda());
			orders.put(newOrder.getId(), newOrder);
			save(); // may throw an IOException
			return newOrder;
		}
	}

	/**
	 * * {@inheritDoc}
	 */
	@Override
	public boolean deleteOrder (int id) throws IOException {
		synchronized (orders) {
			if (orders.containsKey(id)) {
				orders.remove(id);
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
	public Order getOrder (int id) {
		synchronized (orders) {
			if (orders.containsKey(id)) {
				return orders.get(id);
			} else {
				return null;
			}
		}
	}

	/**
	 * * {@inheritDoc}
	 */
	@Override
	public Order[] getOrders () {
		synchronized (orders) {
			return getOrdersArray();
		}
	}

	/**
	 * * {@inheritDoc}
	 */
	@Override
	public Order[] findScreeningOrders (int screeningId) {
		synchronized (orders) {
			return getScreeningOrdersArray(screeningId);
		}
	}

	/**
	 * * {@inheritDoc}
	 */
	@Override
	public Order[] findAccountOrders (int accountId) {
		synchronized (orders) {
			return getAccountOrdersArray(accountId);
		}
	}
}
