package org.example.utility;


import org.example.entity.TicketType;
import org.example.entity.VenueType;

/**
 * Enum with argument types for validator
 */
public enum TypesOfArgs {
    Long(Long.class),
    Double(Double.class),
    String(String.class),
    Boolean(Boolean.class),
    Command(Commands.class),

    VenueType(VenueType.class),
    TicketType(TicketType.class);
    private Class<?> clas;


    TypesOfArgs(Class<?> clas) {
        this.clas=clas;
    }

    public Class<?> getClas() {
        return clas;
    }
}
