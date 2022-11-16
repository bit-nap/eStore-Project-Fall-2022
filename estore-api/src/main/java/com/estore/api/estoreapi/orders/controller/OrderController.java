package com.estore.api.estoreapi.orders.controller;

import com.estore.api.estoreapi.orders.model.Order;
import com.estore.api.estoreapi.orders.persistence.OrderDAO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Handles the REST API requests for an Order object.<p>
 * {@literal @}RestController Spring annotation identifies this class as a REST API method handler to the Spring framework
 *
 * @author Group 3C, The Code Monkeys
 */

@RestController
@RequestMapping("orders")
public class OrderController {
	/* Logger is used to log to command line the HTTP request performed, or any internal server errors encountered. */
	private static final Logger LOG = Logger.getLogger(OrderController.class.getName());

	/** The OrderDAO object this Controller interacts with to get Order objects. */
	private final OrderDAO orderDao;

	/**
	 * Creates a REST API controller to respond to Order requests.
	 *
	 * @param orderDao The {@link OrderDAO Order Data Access Object} to perform CRUD operations<br>
	 *                 This dependency is injected by the Spring Framework
	 */
	public OrderController (OrderDAO orderDao) {
		this.orderDao = orderDao;
	}

	/**
	 * Creates a {@linkplain Order order} with the provided order object.
	 *
	 * @param order The {@link Order order} to create
	 * @return ResponseEntity with created {@link Order order} object and HTTP status of CREATED<br>
	 * ResponseEntity with HTTP status of CONFLICT if {@link Order order} object already exists<br>
	 * ResponseEntity with HTTP status of INTERNAL_SERVER_ERROR otherwise
	 */
	@PostMapping("")
	public ResponseEntity<Order> createOrder (@RequestBody Order order) {
		LOG.info("POST /orders/" + order);

		try {
			Order newOrder = orderDao.createOrder(order);
			if (newOrder != null) {
				return new ResponseEntity<>(newOrder, HttpStatus.CREATED);
			} else {
				return new ResponseEntity<>(HttpStatus.CONFLICT);
			}
		} catch (IOException e) {
			LOG.log(Level.SEVERE, e.getLocalizedMessage());
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	/**
	 * Deletes a {@linkplain Order order} with the given id.
	 *
	 * @param id The id of the {@link Order order} to be deleted
	 * @return ResponseEntity HTTP status of OK if deleted<br>
	 * ResponseEntity with HTTP status of NOT_FOUND if not found<br>
	 * ResponseEntity with HTTP status of INTERNAL_SERVER_ERROR otherwise
	 */
	@DeleteMapping("/{id}")
	public ResponseEntity<Order> deleteOrder (@PathVariable int id) {
		LOG.info("DELETE /orders/" + id);
		try {
			if (orderDao.deleteOrder(id)) {
				return new ResponseEntity<>(HttpStatus.OK);
			} else {
				return new ResponseEntity<>(HttpStatus.NOT_FOUND);
			}
		} catch (IOException e) {
			LOG.log(Level.SEVERE, e.getLocalizedMessage());
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	/**
	 * Responds to the GET request for a {@linkplain Order order} with the given id.
	 *
	 * @param id The id used to locate a {@link Order order}
	 * @return ResponseEntity with {@link Order order} object and HTTP status of OK if found<br>
	 * ResponseEntity with HTTP status of NOT_FOUND if not found<br>
	 * ResponseEntity with HTTP status of INTERNAL_SERVER_ERROR otherwise
	 */
	@GetMapping("/{id}")
	public ResponseEntity<Order> getOrder (@PathVariable int id) {
		LOG.info("GET /orders/" + id);
		try {
			// Try to get the order based on the id entered by the user
			Order order = orderDao.getOrder(id);
			if (order != null) {
				return new ResponseEntity<>(order, HttpStatus.OK);
			} else {
				return new ResponseEntity<>(HttpStatus.NOT_FOUND);
			}
		} catch (IOException e) {
			LOG.log(Level.SEVERE, e.getLocalizedMessage());
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	/**
	 * Responds to the GET request for all {@linkplain Order orders}.
	 *
	 * @return ResponseEntity with array of {@link Order order} objects (may be empty) and HTTP status of OK<br>
	 * ResponseEntity with HTTP status of INTERNAL_SERVER_ERROR otherwise
	 */
	@GetMapping("")
	public ResponseEntity<Order[]> getOrders () {
		LOG.info("GET /orders/");
		try {
			// Try and get a list of all the orders from the system
			Order[] orders = orderDao.getOrders();
			if (orders != null) {
				return new ResponseEntity<>(orders, HttpStatus.OK);
			} else {
				return new ResponseEntity<>(HttpStatus.NOT_FOUND);
			}
		} catch (IOException e) {
			LOG.log(Level.SEVERE, e.getLocalizedMessage());
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	/**
	 * Responds to the GET request for all {@linkplain Order orders} whose screening id is the given id.
	 * Used to find all orders for a given screening.
	 *
	 * @param screeningId An integer representing the screening id of which an {@link Order order} was placed for.
	 * @return ResponseEntity with array of {@link Order order} objects (may be empty) and HTTP status of OK<br>
	 * ResponseEntity with HTTP status of INTERNAL_SERVER_ERROR otherwise
	 */
	@RequestMapping(value = "/", method = RequestMethod.GET, params = "screeningId")
	public ResponseEntity<Order[]> searchOrdersByScreeningId (@RequestParam("screeningId") int screeningId) {
		LOG.info("GET /orders/?screeningId=" + screeningId);
		try {
			Order[] foundOrders = orderDao.findScreeningOrders(screeningId);
			return new ResponseEntity<>(foundOrders, HttpStatus.OK);
		} catch (IOException e) {
			LOG.log(Level.SEVERE, e.getLocalizedMessage());
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	/**
	 * Responds to the GET request for all {@linkplain Order orders} whose account id is the given id.
	 * Used to find all orders for a given account.
	 *
	 * @param accountId An integer representing the account id which placed an {@link Order order}.
	 * @return ResponseEntity with array of {@link Order order} objects (may be empty) and HTTP status of OK<br>
	 * ResponseEntity with HTTP status of INTERNAL_SERVER_ERROR otherwise
	 */
	@RequestMapping(value = "/", method = RequestMethod.GET, params = "accountId")
	public ResponseEntity<Order[]> searchOrdersByAccountId (@RequestParam("accountId") int accountId) {
		LOG.info("GET /orders/?accountId=" + accountId);
		try {
			Order[] foundOrders = orderDao.findAccountOrders(accountId);
			return new ResponseEntity<>(foundOrders, HttpStatus.OK);
		} catch (IOException e) {
			LOG.log(Level.SEVERE, e.getLocalizedMessage());
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
}
