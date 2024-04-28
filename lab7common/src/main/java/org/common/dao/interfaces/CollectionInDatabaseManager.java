package org.common.dao.interfaces;

import org.common.dto.Ticket;

import java.util.HashMap;

public interface CollectionInDatabaseManager {
    HashMap<Long, Ticket> loadCollection();
    void clear();


    void insert(Ticket ticket);

    void update(Ticket newTicket);

    void removeTicket(Ticket ticket);

    void removeTicket(Long id);


}
