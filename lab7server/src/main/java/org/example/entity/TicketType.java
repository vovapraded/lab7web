package org.example.entity;

import java.io.Serial;
import java.io.Serializable;

/**
 * Enum for the ticket type
 */
public enum TicketType implements Serializable {  VIP,
    USUAL,
    BUDGETARY;
    @Serial
    private static final long serialVersionUID = "TicketType".hashCode();
}
