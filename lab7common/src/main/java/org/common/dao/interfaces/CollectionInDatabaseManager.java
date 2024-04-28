package org.common.dao.interfaces;

import org.common.dto.Ticket;

import java.util.HashMap;

public interface CollectionInDatabaseManager {
    HashMap<Long, Ticket> loadCollection();



    void clear( String login);

    void insert(Ticket ticket);



    void update(Ticket newTicket, String login);

    void removeTicket(Ticket ticket);

    void removeTicket(Long id, String login);
}
