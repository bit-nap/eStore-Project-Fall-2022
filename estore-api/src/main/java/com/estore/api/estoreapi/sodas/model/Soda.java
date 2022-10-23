package com.estore.api.estoreapi.sodas.model;

import java.util.logging.Logger;

import com.fasterxml.jackson.annotation.JsonProperty;
/**
 * Represents a Soda entity
 * 
 * @author SWEN Faculty
 */
public class Soda {
    private static final Logger LOG = Logger.getLogger(Soda.class.getName());

    // Package private for tests
    static final String STRING_FORMAT = "Soda [id=%d, name=%s]";

    @JsonProperty("id") private int id;
    @JsonProperty("name") private String name;

    /**
     * Create a soda with the given id and name
     * @param id The id of the soda
     * @param name The name of the soda
     * 
     * {@literal @}JsonProperty is used in serialization and deserialization
     * of the JSON object to the Java object in mapping the fields.  If a field
     * is not provided in the JSON object, the Java field gets the default Java
     * value, i.e. 0 for int
     */
    public Soda(@JsonProperty("id") int id, @JsonProperty("name") String name) {
        this.id = id;
        this.name = name;
    }

    /**
     * Retrieves the id of the soda
     * @return The id of the soda
     */
    public int getId() {return id;}

    /**
     * Sets the name of the soda - necessary for JSON object to Java object deserialization
     * @param name The name of the soda
     */
    public void setName(String name) {this.name = name;}

    /**
     * Retrieves the name of the soda
     * @return The name of the soda
     */
    public String getName() {return name;}

    /**
     * {@inheritDoc}
     */
    @Override
    public String toString() {
        return String.format(STRING_FORMAT,id,name);
    }
}