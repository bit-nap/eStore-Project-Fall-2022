package com.estore.api.estoreapi.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.logging.Logger;

/**
 * Represents a ticket to the screening of a movie.
 */
public class Ticket {
	/** TODO: Add description of the purpose of Logger, once it's actually used. */
	private static final Logger LOG = Logger.getLogger(Ticket.class.getName());

	// Package private for tests - Prof
	static final String STRING_FORMAT = "Ticket [screeningId=%d, name=%s]";

	/** The id of the screening this ticket belongs to. */
	@JsonProperty("screeningId") private int screeningId;
	/** The name of the individual who purchased this ticket. */
	@JsonProperty("name") private String ticketHolderName;

	/**
	 * Create a ticket with the given screening id and ticket holder's name.
	 *
	 * @param screeningId      The id of the screening this ticket belongs to.
	 * @param ticketHolderName The name of the individual who purchased this ticket.
	 *                         <p>
	 *                         {@literal @}JsonProperty is used in serialization and deserialization
	 *                         of the JSON object to the Java object in mapping the fields.  If a field
	 *                         is not provided in the JSON object, the Java field gets the default Java
	 *                         value, i.e. 0 for int
	 */
	public Ticket (@JsonProperty("screeningId") int screeningId, @JsonProperty("name") String ticketHolderName) {
		this.screeningId = screeningId;
		this.ticketHolderName = ticketHolderName;
	}

	/**
	 * @return The id of the screening this ticket belongs to.
	 */
	public int getScreeningId () {
		return screeningId;
	}

	/**
	 * Necessary for JSON object to perform Java object deserialization.
	 * <p>
	 * I think this is what was originally meant. - Oscar
	 *
	 * @param ticketHolderName The name of the ticket holder this ticket will belong to.
	 */
	public void setTicketHolderName (String ticketHolderName) {
		this.ticketHolderName = ticketHolderName;
	}

	/**
	 * @return The name of the ticket holder this ticket belongs to.
	 */
	public String getTicketHolderName () {
		return ticketHolderName;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String toString () {
		return String.format(STRING_FORMAT, screeningId, ticketHolderName);
	}
}
