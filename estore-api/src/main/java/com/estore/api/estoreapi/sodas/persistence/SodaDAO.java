package com.estore.api.estoreapi.sodas.persistence;

import java.io.IOException;
import com.estore.api.estoreapi.sodas.model.Soda;

/**
 * Defines the interface for Soda object persistence
 * 
 * @author SWEN Faculty
 */
public interface SodaDAO {
    /**
     * Retrieves all {@linkplain Soda sodas}
     * 
     * @return An array of {@link Soda soda} objects, may be empty
     * 
     * @throws IOException if an issue with underlying storage
     */
    Soda[] getSodas() throws IOException;

    /**
     * Finds all {@linkplain Soda sodas} whose name contains the given text
     * 
     * @param containsText The text to match against
     * 
     * @return An array of {@link Soda sodas} whose nemes contains the given text, may be empty
     * 
     * @throws IOException if an issue with underlying storage
     */
    Soda[] findSodas(String containsText) throws IOException;

    /**
     * Retrieves a {@linkplain Soda soda} with the given id
     * 
     * @param id The id of the {@link Soda soda} to get
     * 
     * @return a {@link Soda soda} object with the matching id
     * <br>
     * null if no {@link Soda soda} with a matching id is found
     * 
     * @throws IOException if an issue with underlying storage
     */
    Soda getSoda(int id) throws IOException;

    /**
     * Creates and saves a {@linkplain Soda soda}
     * 
     * @param soda {@linkplain Soda soda} object to be created and saved
     * <br>
     * The id of the soda object is ignored and a new uniqe id is assigned
     *
     * @return new {@link Soda soda} if successful, false otherwise 
     * 
     * @throws IOException if an issue with underlying storage
     */
    Soda createSoda(Soda soda) throws IOException;

    /**
     * Updates and saves a {@linkplain Soda soda}
     * 
     * @param {@link Soda soda} object to be updated and saved
     * 
     * @return updated {@link Soda soda} if successful, null if
     * {@link Soda soda} could not be found
     * 
     * @throws IOException if underlying storage cannot be accessed
     */
    Soda updateSoda(Soda soda) throws IOException;

    /**
     * Deletes a {@linkplain Soda soda} with the given id
     * 
     * @param id The id of the {@link Soda soda}
     * 
     * @return true if the {@link Soda soda} was deleted
     * <br>
     * false if soda with the given id does not exist
     * 
     * @throws IOException if underlying storage cannot be accessed
     */
    boolean deleteSoda(int id) throws IOException;
}
