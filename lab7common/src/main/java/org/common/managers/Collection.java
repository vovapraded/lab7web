package org.common.managers;

import org.common.dto.Ticket;

import java.util.*;
import java.util.stream.Collectors;

/**
 * The class that manages the collection
 */

    public  class Collection  {

    public static Collection getInstance() {
        return INSTANCE;
    }

    private static final Collection INSTANCE= new Collection();
    private Date currentDate;
    private HashMap<Long,Ticket> hashMap = new HashMap<>();


    private Collection(){
        currentDate = new Date();
    }
    public void clearCollection(){
        hashMap.clear();
    }
    public void insertElement(Ticket ticket){
        hashMap.put(ticket.getId(),ticket);
        Ticket.addToInstance(ticket);
    }
    public void addHashMap(HashMap<Long,Ticket> anotherHashMap){
        hashMap.putAll(anotherHashMap);
    }
    public Long getFreeId(){
        Long maxId = hashMap.keySet().stream().max(Long::compare).orElse(0L);
        return maxId+1L;
    }

    public Ticket getElement(Long id) {
        return hashMap.get(id);
    }
    public  void removeElement(Long id){
        hashMap.remove(id);
    }
    public void removeGreater(Ticket ourTicket) {
        hashMap.values().removeIf(ticket -> ticket.compareTo(ourTicket) > 0);
    }

    public void removeGreaterKey(Long id) {
        hashMap.values().removeIf(ticket -> ticket.getId() > id);
    }

    public OptionalDouble getAveragePrice(){
        OptionalDouble average = hashMap.values().stream()
                .map(ticket -> ticket.getId())
                .mapToLong(Long::longValue)
                .average();
        return average;
    }
    public List<Ticket> filterLessThanVenue(Long capacity){
        List<Ticket>  filtered = hashMap.values().stream()
                .filter((ticket) -> ( ticket.getVenue().getCapacity() == null || ticket.getVenue().getCapacity() < capacity))
                .collect(Collectors.toList());
        return filtered;
    }

    public HashMap<Long, Ticket>  getHashMap() {
        return hashMap;
    }


    public Date getCurrentDate() {
        return currentDate;
    }
    public int getCountOfElements(){
        return hashMap.size();
    }
}

