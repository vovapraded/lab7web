package org.common.utility;


import org.common.dto.TicketType;
import org.common.dto.VenueType;

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
