package org.example.sort;

import com.google.common.collect.Ordering;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.apache.commons.collections4.ComparatorUtils;
import org.example.entity.Ticket;

import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
public class ComparatorHashMap {
    private final Map<Field, Comparator<Ticket>> comparators = new HashMap<>();
    public Comparator getComparatorByFieldAndOrder(Field field, Order order){
        var comparator =Comparator.nullsFirst(comparators.get(field));
        if (order.equals(Order.DESC)){
            comparator =Comparator.nullsFirst(comparator.reversed());
        }
        return comparator;
    }
    private ComparatorHashMap(){
        // Добавляем лямбда-выражение Ticket::getId в хешмапу
        comparators.put(Field.ID, Ordering.natural().nullsFirst().onResultOf(Ticket::getId));
        comparators.put(Field.NAME, Ordering.natural().nullsFirst().onResultOf(Ticket::getName));
        comparators.put(Field.PRICE, Ordering.natural().nullsFirst().onResultOf(Ticket::getPrice));
        comparators.put(Field.DISCOUNT, Ordering.natural().nullsFirst().onResultOf(Ticket::getDiscount));
        comparators.put(Field.COORDINATE_X, Ordering.natural().nullsFirst().onResultOf(ticket -> ticket.getCoordinates().getX()));
        comparators.put(Field.COORDINATE_Y, Ordering.natural().nullsFirst().onResultOf(ticket -> ticket.getCoordinates().getY()));
        comparators.put(Field.REFUNDABLE, Ordering.natural().nullsFirst().onResultOf(Ticket::getRefundable));
        comparators.put(Field.TICKET_TYPE, Ordering.natural().nullsFirst().onResultOf(Ticket::getTicketType));
        comparators.put(Field.VENUE_NAME,Ordering.natural().nullsFirst().onResultOf(ticket -> ticket.getVenue().getName()));
        comparators.put(Field.VENUE_CAPACITY,Ordering.natural().nullsFirst().onResultOf(ticket -> ticket.getVenue().getCapacity()));
        comparators.put(Field.VENUE_TYPE,Ordering.natural().nullsFirst().onResultOf(ticket -> ticket.getVenue().getVenueType()));
        comparators.put(Field.CREATED_BY, Ordering.natural().nullsFirst().onResultOf(Ticket::getCreatedBy));

    }
    @Getter

    private static final ComparatorHashMap instance = new ComparatorHashMap();

}
