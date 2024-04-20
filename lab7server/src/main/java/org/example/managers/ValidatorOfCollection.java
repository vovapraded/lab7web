package org.example.managers;

import org.common.dto.Ticket;
import org.common.dto.Venue;
import org.common.managers.Collection;
import org.common.utility.Console;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


import java.util.*;

/**
 * A class that checks the collection for the correctness of the data
 */
public class ValidatorOfCollection {
    private static final Logger logger = LoggerFactory.getLogger(ValidatorOfCollection.class);

    private final org.common.managers.Collection collection = Collection.getInstance();
    private final Console console;

    public ValidatorOfCollection(Console console) {
        this.console = console;
    }

    public boolean validateCollection() {
        Set<Long> setTicketId = new HashSet<>();
        Set<Long> setVenueId = new HashSet<>();

        for (Ticket ticket: Ticket.getInstancesTicket()){
            setTicketId.add(ticket.getId());
        }
        for (Venue venue: Venue.getInstancesVenue()){
            setVenueId.add(venue.getId());
        }
        if (setVenueId.size()!=Venue.getInstancesVenue().size() || setTicketId.size()!=Ticket.getInstancesTicket().size()){

            logger.error("Ошибка не уникальности id  в файле");
            collection.clearCollection();
            logger.debug("Hashmap очищен, в файле не валидные данные");
        }
        for (Long id: collection.getHashMap().keySet()){
            if (!Objects.equals(id, collection.getHashMap().get(id).getId())){
                logger.error("Ошибка. Не совпадает id "+id+" билета с ключом");
                collection.clearCollection();
                logger.debug("Hashmap очищен, в файле не валидные данные");


                return false;

            }
        }
        logger.debug("Коллекция валидна");
        return true;


    }
}