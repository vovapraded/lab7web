package org.example.entity;

import java.io.Serial;
import java.io.Serializable;

/**
 * Enum for the venue type
 */
public enum VenueType implements Serializable {

    BAR,
    THEATRE,
    CINEMA,
    STADIUM;
    @Serial
    private static final long serialVersionUID = "VenueType".hashCode();

}
