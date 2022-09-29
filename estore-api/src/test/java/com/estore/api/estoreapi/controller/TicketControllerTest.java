package com.estore.api.estoreapi.controller;

import com.estore.api.estoreapi.model.Ticket;
import com.estore.api.estoreapi.persistence.TicketDAO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

/**
 * Test the TicketController class.
 *
 * @author Group 3C, The Code Monkeys
 */
@Tag("Controller-Tier")
public class TicketControllerTest {
	private TicketController ticketController;
	private TicketDAO mockTicketDao;

	/**
	 * Before a test, create a new TicketController object and inject a mock Ticket DAO.
	 */
	@BeforeEach
	public void setupTicketController () {
		mockTicketDao = mock(TicketDAO.class);
		ticketController = new TicketController(mockTicketDao);
	}

	@Test
	public void testGetTicket () throws IOException {
		// setup
		Ticket ticket = new Ticket(99, "Star Wars");
		// when the same id is passed in, our mock ticket DAO will return the Ticket object
		when(mockTicketDao.getTicket(ticket.getId())).thenReturn(ticket);

		// invoke
		ResponseEntity<Ticket> response = ticketController.getTicket(ticket.getId());

		// analyze
		assertEquals(HttpStatus.OK, response.getStatusCode());
		assertEquals(ticket, response.getBody());
	}

	@Test
	public void testGetTicketNotFound () throws Exception {
		// setup
		int ticketId = 99;
		// when the same id is passed in, our mock ticket DAO will return null, simulating no ticket found
		when(mockTicketDao.getTicket(ticketId)).thenReturn(null);

		// invoke
		ResponseEntity<Ticket> response = ticketController.getTicket(ticketId);

		// analyze
		assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
	}

	@Test
	public void testGetTicketHandleException () throws Exception {
		// setup
		int ticketId = 99;
		// when getTicket is called on the mock ticket DAO, throw an IOException
		doThrow(new IOException()).when(mockTicketDao).getTicket(ticketId);

		// invoke
		ResponseEntity<Ticket> response = ticketController.getTicket(ticketId);

		// analyze
		assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
	}
}
