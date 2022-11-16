package com.estore.api.estoreapi.orders.controller;

import com.estore.api.estoreapi.orders.model.Order;
import com.estore.api.estoreapi.orders.persistence.OrderDAO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.*;

/**
 * Test the OrderController class.
 *
 * @author Group 3C, The Code Monkeys
 */
@Tag("Controller-Tier")
class OrderControllerTest {
	private OrderController orderController;
	private OrderDAO mockOrderDao;

	/**
	 * Before a test, create a new OrderController object and inject a mock Order DAO.
	 */
	@BeforeEach
	void setupOrderController () {
		mockOrderDao = mock(OrderDAO.class);
		orderController = new OrderController(mockOrderDao);
	}

	@Test
	void testGetOrder () throws IOException {
		// setup
		Order order = new Order(1, 1, 1, 3, new int[]{ 2, 0, 1 }, new int[]{ 1, 1, 1 }, new String[]{ "a1", "a2", "a3" });
		// when the same id is passed in, our mock order DAO will return the Order object
		when(mockOrderDao.getOrder(order.getId())).thenReturn(order);

		// invoke
		ResponseEntity<Order> response = orderController.getOrder(order.getId());

		// analyze
		assertEquals(HttpStatus.OK, response.getStatusCode());
		assertEquals(order, response.getBody());
	}

	@Test
	void testGetOrderNotFound () throws Exception {
		// setup
		int orderId = 99;
		// when the same id is passed in, our mock order DAO will return null, simulating no order found
		when(mockOrderDao.getOrder(orderId)).thenReturn(null);

		// invoke
		ResponseEntity<Order> response = orderController.getOrder(orderId);

		// analyze
		assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
	}

	@Test
	void testGetOrderHandleException () throws Exception {
		// setup
		int orderId = 99;
		// when getOrder is called on the mock order DAO, throw an IOException
		doThrow(new IOException()).when(mockOrderDao).getOrder(orderId);

		// invoke
		ResponseEntity<Order> response = orderController.getOrder(orderId);

		// analyze
		assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
	}

	/**
	 * Method to test if getting all the orders works
	 *
	 * @throws Exception if something goes wrong with the http request
	 */
	@Test
	void testGetOrders () throws Exception {
		// New list of orders
		Order[] orders = new Order[3];
		orders[0] = new Order(1, 1, 1, 3, new int[]{ 2, 0, 1 }, new int[]{ 1, 1, 1 }, new String[]{ "a1", "a2", "a3" });
		orders[1] = new Order(2, 2, 1, 1, new int[]{ 0, 0, 1 }, new int[]{ 0, 0, 1 }, new String[]{ "a1" });
		orders[2] = new Order(3, 3, 1, 2, new int[]{ 2, 0, 0 }, new int[]{ 0, 1, 1 }, new String[]{ "a1", "a2" });
		// When getOrders is called, return the list of orders from above
		when(mockOrderDao.getOrders()).thenReturn(orders);

		ResponseEntity<Order[]> response = orderController.getOrders();

		assertEquals(HttpStatus.OK, response.getStatusCode());
		assertEquals(orders, response.getBody());
	}

	@Test
	void testGetEmptyOrders () throws Exception {
		// When getOrders is called, return null
		when(mockOrderDao.getOrders()).thenReturn(null);
		// Get NOT_FOUND response from OrderController
		ResponseEntity<Order[]> response = orderController.getOrders();

		assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
		assertNull(response.getBody());
	}

	/**
	 * Test to make sure the exception is handled when getOrders throws one
	 *
	 * @throws Exception if something goes wrong with Http request
	 */
	@Test
	void testGetOrdersHandleException () throws Exception {
		// Throw an exception when the get orders method is called
		doThrow(new IOException()).when(mockOrderDao).getOrders();

		ResponseEntity<Order[]> response = orderController.getOrders();

		assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
	}

	@Test
	void testCreateOrder () throws IOException {
		// setup
		Order order = new Order(1, 1, 1, 3, new int[]{ 2, 0, 1 }, new int[]{ 1, 1, 1 }, new String[]{ "a1", "a2", "a3" });
		// when createOrder is called, return true simulating successful creation and save
		when(mockOrderDao.createOrder(order)).thenReturn(order);

		// invoke
		ResponseEntity<Order> response = orderController.createOrder(order);

		// analyze
		assertEquals(HttpStatus.CREATED, response.getStatusCode());
		assertEquals(order, response.getBody());
	}

	@Test
	void testCreateOrderFailed () throws IOException {
		// setup
		Order order = new Order(1, 1, 1, 3, new int[]{ 2, 0, 1 }, new int[]{ 1, 1, 1 }, new String[]{ "a1", "a2", "a3" });
		// when createOrder is called, return false simulating failed creation and save
		when(mockOrderDao.createOrder(order)).thenReturn(null);

		// invoke
		ResponseEntity<Order> response = orderController.createOrder(order);

		// analyze
		assertEquals(HttpStatus.CONFLICT, response.getStatusCode());
	}

	@Test
	void testCreateOrderHandleException () throws IOException {
		// setup
		Order order = new Order(1, 1, 1, 3, new int[]{ 2, 0, 1 }, new int[]{ 1, 1, 1 }, new String[]{ "a1", "a2", "a3" });

		// when createOrder is called, throw an IOException
		doThrow(new IOException()).when(mockOrderDao).createOrder(order);

		// invoke
		ResponseEntity<Order> response = orderController.createOrder(order);

		// analyze
		assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
	}

	@Test
	void testSearchScreeningOrders () throws IOException {
		// Setup
		int screeningId = 1;
		Order[] foundOrders = new Order[3];
		foundOrders[0] = new Order(1, 1, 1, 3, new int[]{ 2, 0, 1 }, new int[]{ 1, 1, 1 }, new String[]{ "a1", "a2", "a3" });
		foundOrders[1] = new Order(2, 1, 1, 1, new int[]{ 0, 0, 1 }, new int[]{ 0, 0, 1 }, new String[]{ "a1" });
		foundOrders[2] = new Order(3, 1, 1, 2, new int[]{ 2, 0, 0 }, new int[]{ 0, 1, 1 }, new String[]{ "a1", "a2" });

		// When findOrders is called with the search string, return the three orders above
		when(mockOrderDao.findScreeningOrders(screeningId)).thenReturn(foundOrders);

		// Invoke
		ResponseEntity<Order[]> response = orderController.searchOrdersByScreeningId(screeningId);

		// Analyze
		assertEquals(HttpStatus.OK, response.getStatusCode());
		assertEquals(foundOrders, response.getBody());
	}

	@Test
	void testSearchScreeningOrdersHandleException () throws IOException {
		// Setup
		int screeningId = 1;
		// When findOrders is called on the Mock Order DAO, throw an IOException
		doThrow(new IOException()).when(mockOrderDao).findScreeningOrders(screeningId);

		// Invoke
		ResponseEntity<Order[]> response = orderController.searchOrdersByScreeningId(screeningId);

		// Analyze
		assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
	}

	@Test
	void testSearchAccountOrders () throws IOException {
		// Setup
		int accountId = 1;
		Order[] foundOrders = new Order[3];
		foundOrders[0] = new Order(1, 1, 1, 3, new int[]{ 2, 0, 1 }, new int[]{ 1, 1, 1 }, new String[]{ "a1", "a2", "a3" });
		foundOrders[1] = new Order(2, 2, 1, 1, new int[]{ 0, 0, 1 }, new int[]{ 0, 0, 1 }, new String[]{ "a1" });
		foundOrders[2] = new Order(3, 3, 1, 2, new int[]{ 2, 0, 0 }, new int[]{ 0, 1, 1 }, new String[]{ "a1", "a2" });

		// When findOrders is called with the search string, return the three orders above
		when(mockOrderDao.findAccountOrders(accountId)).thenReturn(foundOrders);

		// Invoke
		ResponseEntity<Order[]> response = orderController.searchOrdersByAccountId(accountId);

		// Analyze
		assertEquals(HttpStatus.OK, response.getStatusCode());
		assertEquals(foundOrders, response.getBody());
	}

	@Test
	void testSearchAccountOrdersHandleException () throws IOException {
		// Setup
		int accountId = 1;
		// When findOrders is called on the Mock Order DAO, throw an IOException
		doThrow(new IOException()).when(mockOrderDao).findAccountOrders(accountId);

		// Invoke
		ResponseEntity<Order[]> response = orderController.searchOrdersByAccountId(accountId);

		// Analyze
		assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
	}

	@Test
	void testDeleteOrder () throws IOException { // deleteOrder may throw IOException
		// Setup
		int orderId = 99;
		// when deleteOrder is called return true, simulating successful deletion
		when(mockOrderDao.deleteOrder(orderId)).thenReturn(true);

		// Invoke
		ResponseEntity<Order> response = orderController.deleteOrder(orderId);

		// Analyze
		assertEquals(HttpStatus.OK, response.getStatusCode());
	}

	@Test
	void testDeleteOrderNotFound () throws IOException { // deleteOrder may throw IOException
		// Setup
		int orderId = 99;
		// when deleteOrder is called return false, simulating failed deletion
		when(mockOrderDao.deleteOrder(orderId)).thenReturn(false);

		// Invoke
		ResponseEntity<Order> response = orderController.deleteOrder(orderId);

		// Analyze
		assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
	}

	@Test
	void testDeleteOrderHandleException () throws IOException { // deleteOrder may throw IOException
		// Setup
		int orderId = 99;
		// When deleteOrder is called on the Mock Order DAO, throw an IOException
		doThrow(new IOException()).when(mockOrderDao).deleteOrder(orderId);

		// Invoke
		ResponseEntity<Order> response = orderController.deleteOrder(orderId);

		// Analyze
		assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
	}
}
