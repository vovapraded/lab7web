package org.common.dto;

import lombok.Getter;
import lombok.Setter;

import java.io.Serial;
import java.io.Serializable;
import java.util.ArrayList;

/**
 * class for dto with id
 */

public abstract class ElementsWithId implements Serializable {
    @Serial
    private static final long serialVersionUID = "ElementsWithId".hashCode();
    @Getter
    protected static ArrayList<Ticket> instancesTicket = new ArrayList<>();
    @Getter
    protected static ArrayList<Venue> instancesVenue = new ArrayList<>();

@Setter @Getter
    protected Long id;

    protected static  <T extends ElementsWithId> Long getFreeId(ArrayList<T> arrayList) {
        Long max = 0L;
        for (int i=0;i<arrayList.size();i++) {
            max = arrayList.get(i).getId() > max ?  arrayList.get(i).getId()  : max;
        }
        return max+1L;
    }
    public static void addToInstance(Ticket ticket){
        var venue= ticket.getVenue();
        venue.setId(getFreeId(instancesVenue));
        instancesVenue.add(venue);
        instancesTicket.add(ticket);
    }
}








