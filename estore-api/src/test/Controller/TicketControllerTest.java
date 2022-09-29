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
}

