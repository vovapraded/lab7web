package org.common.dto;

import jakarta.persistence.Entity;

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
