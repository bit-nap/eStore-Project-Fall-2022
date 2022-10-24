package com.estore.api.estoreapi.sodas;

import com.estore.api.estoreapi.sodas.model.Soda;
import com.estore.api.estoreapi.sodas.persistence.SodaDAO;
import org.springframework.stereotype.Component;

import java.io.IOException;

/**
 * Get a Soda object from a SodaDAO object with its id.
 */
@Component
public class SodaGetter {
	/** SodaDAO to retrieve Soda objects from. */
	private final SodaDAO sodaDAO;

	/**
	 * Create a new SodaGetter object with the given SodaDAO object.
	 *
	 * @param sodaDAO The SodaDAO object to retrieve sodas from.
	 */
	public SodaGetter (SodaDAO sodaDAO) {
		this.sodaDAO = sodaDAO;
	}

	/**
	 * Get a Soda object with the given id.
	 *
	 * @param id The id of the soda to get
	 * @return Soda object with given id
	 */
	public Soda getSoda (int id) {
		try {
			return sodaDAO.getSoda(id);
		} catch (IOException e) {
			e.printStackTrace();
			System.exit(69);
		}
		return null;
	}
}
