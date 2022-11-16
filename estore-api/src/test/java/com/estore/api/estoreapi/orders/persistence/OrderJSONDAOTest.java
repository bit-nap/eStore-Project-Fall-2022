package com.estore.api.estoreapi.orders.persistence;

import com.estore.api.estoreapi.orders.model.Order;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

/**
 * Test the Order JSON DAO class.
 *
 * @author Group 3C, The Code Monkeys
 */
class OrderJSONDAOTest {
	OrderJSONDAO orderJSONDAO;
	Order[] testOrders;
	ObjectMapper mockObjectMapper;

	/**
	 * Before each test, we will create and inject a Mock Object Mapper to
	 * isolate the tests from the underlying file
	 *
	 * @throws IOException if orderFileDAO cannot read from fake file
	 */
	@BeforeEach
	void setupOrderJSONDAO () throws IOException {
		mockObjectMapper = mock(ObjectMapper.class);
		testOrders = new Order[3];
		testOrders[0] = new Order(1, 1, 1, 3, new int[]{ 2, 0, 1 }, new int[]{ 1, 1, 1 }, new String[]{ "a1", "a2", "a3" });
		testOrders[1] = new Order(2, 2, 1, 1, new int[]{ 0, 0, 1 }, new int[]{ 0, 0, 1 }, new String[]{ "a1" });
		testOrders[2] = new Order(3, 3, 1, 2, new int[]{ 2, 0, 0 }, new int[]{ 0, 1, 1 }, new String[]{ "a1", "a2" });

		// When the object mapper is supposed to read from the file the mock object mapper will return the order array above
		when(mockObjectMapper.readValue(new File("mao-zedongs-little-red-book.epub"), Order[].class)).thenReturn(testOrders);
		orderJSONDAO = new OrderJSONDAO("mao-zedongs-little-red-book.epub", mockObjectMapper);
	}

	@Test
	void testGetOrders () {
		// Invoke
		Order[] orders = orderJSONDAO.getOrders();

		// Analyze
		assertEquals(orders.length, testOrders.length);
		for (int i = 0; i < testOrders.length; ++i) {
			assertEquals(orders[i], testOrders[i]);
		}
	}

	@Test
	void testFindScreeningOrders () {
		// Invoke
		Order[] orders = orderJSONDAO.findScreeningOrders(1);

		// Analyze
		assertEquals(orders.length, 1);
		assertEquals(orders[0], testOrders[0]);

		// Invoke
		orders = orderJSONDAO.findScreeningOrders(0);
		assertEquals(orders.length, 0);
	}

	@Test
	void testFindAccountOrders () {
		// Invoke
		Order[] orders = orderJSONDAO.findAccountOrders(1);

		// Analyze
		assertEquals(orders.length, 3);
		assertEquals(orders[0], testOrders[0]);
		assertEquals(orders[1], testOrders[1]);
		assertEquals(orders[2], testOrders[2]);

		// Invoke
		orders = orderJSONDAO.findAccountOrders(0);
		assertEquals(orders.length, 0);
	}

	@Test
	void testGetOrder () {
		// Invoke
		Order order = orderJSONDAO.getOrder(1);

		// Analyze
		assertEquals(order, testOrders[0]);
	}

	@Test
	void testDeleteOrder () {
		// Invoke
		boolean result = assertDoesNotThrow(() -> orderJSONDAO.deleteOrder(1), "Unexpected exception thrown");

		// Analyze
		assertTrue(result);
		// We check the internal tree map size against the length of the test orders array - 1 (because of the delete function call)
		// Because orders attribute of OrderJSONDAO is package private we can access it directly
		assertEquals(orderJSONDAO.orders.size(), testOrders.length - 1);
	}

	@Test
	void testCreateOrder () {
		// Setup
		Order order = new Order(4, 1, 2, 1, new int[]{ 0, 0, 1 }, new int[]{ 0, 0, 1 }, new String[]{ "a1" });
		// Invoke
		Order result = assertDoesNotThrow(() -> orderJSONDAO.createOrder(order), "Unexpected exception thrown");
		// Analyze
		assertNotNull(result);
		Order actual = orderJSONDAO.getOrder(order.getId());
		assertEquals(actual.getId(), order.getId());
		assertEquals(actual.getScreeningId(), order.getScreeningId());
		assertEquals(actual.getAccountId(), order.getAccountId());
		assertEquals(actual.getTickets(), order.getTickets());
		assertArrayEquals(actual.getPopcorn(), order.getPopcorn());
		assertArrayEquals(actual.getSoda(), order.getSoda());
	}

	@Test
	void testSaveException () throws IOException {
		doThrow(new IOException()).when(mockObjectMapper).writeValue(any(File.class), any(Order[].class));

		Order order = new Order(4, 1, 2, 1, new int[]{ 0, 0, 1 }, new int[]{ 0, 0, 1 }, new String[]{ "a1" });

		assertThrows(IOException.class, () -> orderJSONDAO.createOrder(order), "IOException not thrown");
	}

	@Test
	void testGetOrderNotFound () {
		// Invoke
		Order order = orderJSONDAO.getOrder(99);

		// Analyze
		assertNull(order);
	}

	@Test
	void testDeleteOrderNotFound () {
		// Invoke
		boolean result = assertDoesNotThrow(() -> orderJSONDAO.deleteOrder(99), "Unexpected exception thrown");

		// Analyze
		assertFalse(result);
		assertEquals(orderJSONDAO.orders.size(), testOrders.length);
	}

	@Test
	void testConstructorException () throws IOException {
		// Setup
		ObjectMapper mockObjectMapper = mock(ObjectMapper.class);
		// We want to simulate with a Mock Object Mapper that an exception was raised during JSON object deserialization into Java objects
		// When the Mock Object Mapper readValue method is called from the OrderJSONDAO load method, an IOException is raised
		doThrow(new IOException()).when(mockObjectMapper).readValue(new File("mao-zedongs-little-red-book.epub"), Order[].class);

		// Invoke & Analyze
		assertThrows(IOException.class, () -> new OrderJSONDAO("mao-zedongs-little-red-book.epub", mockObjectMapper), "IOException not thrown");
	}
}
