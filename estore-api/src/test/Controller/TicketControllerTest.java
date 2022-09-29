import java.beans.Transient;
import java.io.IOException;

import org.springframework.http.ResponseEntity;

import com.estore.api.estoreapi.controller.TicketController;
import com.estore.api.estoreapi.model.Ticket;
import com.estore.api.estoreapi.persistence.TicketDAO;

/**
 * Test the Ticket Controller class
 *
 * @author Group 3c
 */
@Tag("Controller-tier")
public class TicketControllerTest {
	private TicketController ticketController;
	private TicketDAO mockTicketDao;

	/**
	 * Before a test, create a new TicketController object and inject
	 * a mock Ticket DAO
	 */
	@BeforeEach
	public void setupTicketController() {
		mockTicketDao = mock(TicketDAO.class);
		ticketController = new TicketController(mockTicketDao);
	}

	/**
	 * Test the getTicket method to get a ticket for a movie based on
	 * the ticket Id
	 * @throws IOException
	 */
	@Test
	public void testGetTicket() throws IOException {
		// Setup
		Ticket ticket = new Ticket(99, "Star Wars");
		// When the same id is passed in, our mock Ticket DAO will return the Ticket object
		when(mockTicketDao.getTicket(ticket.getId())).thenReturn(ticket);

		// Invoke
		ResponseEntity<Ticket> resonse = ticketController.getTicket(ticket.getId());

		// Analyze
		assertEquals(HttpStatus.OK, response.getStatusCode());
		assertEquals(ticket, response.getBody());
	}

	@Test
    public void testGetTicketNotFound() throws Exception { // getTicket may throw IOException
        // Setup
        int ticketId = 99;
        // When the same id is passed in, our mock Ticket DAO will return null, simulating
        // no hero found
        when(mockTicketDAO.getTicket(ticketId)).thenReturn(null);

        // Invoke
        ResponseEntity<Ticket> response = ticketController.getTicket(ticketId);

        // Analyze
        assertEquals(HttpStatus.NOT_FOUND,response.getStatusCode());
    }

    @Test
    public void testGetTicketHandleException() throws Exception { // getTicket may throw IOException
        // Setup
        int ticketId = 99;
        // When getTicket is called on the Mock Ticket DAO, throw an IOException
        doThrow(new IOException()).when(mockTicketDAO).getTicket(ticketId);

        // Invoke
        ResponseEntity<Ticket> response = ticketController.getTicket(ticketId);

        // Analyze
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR,response.getStatusCode());
    }

	@Test
    public void testGetTickets() throws IOException { // getTickets may throw IOException
        // Setup
        Ticket[] ticket = new Ticket[2];
        ticket[0] = new Ticket(99,"Napolean Dynamite");
        ticket[1] = new Ticket(100,"Warm Bodies");
        // When getTicket is called return the tickets created above
        when(mockTicketDAO.getTickets()).thenReturn(ticket);

        // Invoke
        ResponseEntity<Ticket[]> response = ticketController.getTicket();

        // Analyze
        assertEquals(HttpStatus.OK,response.getStatusCode());
        assertEquals(ticket,response.getBody());
    }

    @Test
    public void testGetTicketesHandleException() throws IOException { // getTickets may throw IOException
        // Setup
        // When getTickets is called on the Mock Ticket DAO, throw an IOException
        doThrow(new IOException()).when(mockTicketDAO).getTickets();

        // Invoke
        ResponseEntity<Ticket[]> response = ticketController.getTickets();

        // Analyze
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR,response.getStatusCode());
    }
}

