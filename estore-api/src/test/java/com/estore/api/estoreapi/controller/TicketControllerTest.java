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

	@Test
	public void testCreateTicket () throws IOException {
		// setup
		Ticket ticket = new Ticket(72, "The Godfather");
		// when createTicket is called, return true simulating successful creation and save
		when(mockTicketDao.createTicket(ticket)).thenReturn(ticket);

		// invoke
		ResponseEntity<Ticket> response = ticketController.createTicket(ticket);

		// analyze
		assertEquals(HttpStatus.CREATED, response.getStatusCode());
		assertEquals(ticket, response.getBody());
	}

	@Test
	public void testCreateTicketFailed () throws IOException {
		// setup
		Ticket ticket = new Ticket(72, "The Godfather");
		// when createTicket is called, return false simulating failed creation and save
		when(mockTicketDao.createTicket(ticket)).thenReturn(null);

		// invoke
		ResponseEntity<Ticket> response = ticketController.createTicket(ticket);

		// analyze
		assertEquals(HttpStatus.CONFLICT, response.getStatusCode());
	}

	@Test
	public void testCreateTicketHandleException () throws IOException {
		// setup
		Ticket ticket = new Ticket(72, "The Godfather");

		// when createTicket is called, throw an IOException
		doThrow(new IOException()).when(mockTicketDao).createTicket(ticket);

		// invoke
		ResponseEntity<Ticket> response = ticketController.createTicket(ticket);

		// analyze
		assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
	}
  
  @Test
  public void testSearchTickets() throws IOException {
        // Setup
        String searchString = "The";
        Ticket[] foundTickets = new Ticket[2];
        foundTickets[0] = new Ticket(99,"The Terminator");
        foundTickets[1] = new Ticket(100,"The Godfather");
        // When findTickets is called with the search string, return the two
        /// tickets above
        when(mockTicketDao.findTickets(searchString)).thenReturn(foundTickets);

        // Invoke
        ResponseEntity<Ticket[]> response = ticketController.searchTickets(searchString);

        // Analyze
        assertEquals(HttpStatus.OK,response.getStatusCode());
        assertEquals(foundTickets,response.getBody());
    }

    @Test
    public void testSearchTicketsHandleException() throws IOException {
        // Setup
        String searchString = "an";
        // When createTicket is called on the Mock Ticket DAO, throw an IOException
        doThrow(new IOException()).when(mockTicketDao).findTickets(searchString);

        // Invoke
        ResponseEntity<Ticket[]> response = ticketController.searchTickets(searchString);

        // Analyze
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR,response.getStatusCode());
    }

	@Test
    public void testDeleteTicket() throws IOException { // deleteTicket may throw IOException
        // Setup
        int ticketId = 99;
        // when deleteTicket is called return true, simulating successful deletion
        when(mockTicketDao.deleteTicket(ticketId)).thenReturn(true);

        // Invoke
        ResponseEntity<Ticket> response = ticketController.deleteTicket(ticketId);

        // Analyze
        assertEquals(HttpStatus.OK,response.getStatusCode());
    }

    @Test
    public void testDeleteTicketNotFound() throws IOException { // deleteTicket may throw IOException
        // Setup
        int ticketId = 99;
        // when deleteTicket is called return false, simulating failed deletion
        when(mockTicketDao.deleteTicket(ticketId)).thenReturn(false);

        // Invoke
        ResponseEntity<Ticket> response = ticketController.deleteTicket(ticketId);

        // Analyze
        assertEquals(HttpStatus.NOT_FOUND,response.getStatusCode());
    }

    @Test
    public void testDeleteTicketHandleException() throws IOException { // deleteTicket may throw IOException
        // Setup
        int ticketId = 99;
        // When deleteTicket is called on the Mock Ticket DAO, throw an IOException
        doThrow(new IOException()).when(mockTicketDao).deleteTicket(ticketId);

        // Invoke
        ResponseEntity<Ticket> response = ticketController.deleteTicket(ticketId);

        // Analyze
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR,response.getStatusCode());
    }
}
