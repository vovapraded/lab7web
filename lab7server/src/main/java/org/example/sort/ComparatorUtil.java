package org.example.sort;

import lombok.Getter;
import org.example.entity.Ticket;

import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
@Getter
public class ComparatorUtil {
    private final Map<Field, Comparator<Ticket>> comparators = new HashMap<>();
    public ComparatorUtil(){
        // Добавляем лямбда-выражение Ticket::getId в хешмапу
        comparators.put(Field.ID, Comparator.comparing(Ticket::getId));
        comparators.put(Field.NAME, Comparator.comparing(Ticket::getName));
        comparators.put(Field.PRICE, Comparator.comparing(Ticket::getPrice));
        comparators.put(Field.DISCOUNT, Comparator.comparing(Ticket::getDiscount));
        comparators.put(Field.COORDINATE_X, Comparator.comparing(ticket -> ticket.getCoordinates().getX()));
        comparators.put(Field.COORDINATE_Y, Comparator.comparing(ticket -> ticket.getCoordinates().getY()));
        comparators.put(Field.REFUNDABLE, Comparator.comparing(Ticket::getRefundable));
        comparators.put(Field.TICKET_TYPE, Comparator.comparing(Ticket::getTicketType));
        comparators.put(Field.VENUE_NAME,Comparator.comparing(ticket -> ticket.getVenue().getName()));
        comparators.put(Field.VENUE_CAPACITY,Comparator.comparing(ticket -> ticket.getVenue().getCapacity()));
        comparators.put(Field.VENUE_TYPE,Comparator.comparing(ticket -> ticket.getVenue().getVenueType()));
        comparators.put(Field.CREATED_BY, Comparator.comparing(Ticket::getCreatedBy));

    }

}
