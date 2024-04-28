package org.common.managers;

import lombok.Getter;
import lombok.Setter;
import org.common.dao.interfaces.CollectionInDatabaseManager;
import org.common.dto.Ticket;
import org.common.dto.Venue;

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
    @Setter @Getter
    private   CollectionInDatabaseManager ticketDao;


    private Collection(){
        currentDate = new Date();
    }
    public void clearCollection(){
        ticketDao.clear();
        hashMap.clear();
    }
    public void insertElement(Ticket ticket){
        ticketDao.insert(ticket);
        hashMap.put(ticket.getId(),ticket);

    }
    public void updateTicket(Ticket ticket){
        ticketDao.update(ticket);
        hashMap.put(ticket.getId(),ticket);
    }
    public ArrayList<Venue> getAllVenue(){
        return (ArrayList<Venue>) hashMap.values().stream().map(Ticket::getVenue).collect(Collectors.toList());
    }
    public void addHashMap(HashMap<Long,Ticket> anotherHashMap){

        hashMap.putAll(anotherHashMap);
    }


    public Ticket getElement(Long id) {
        return hashMap.get(id);
    }
    public  void removeElement(Long id){
        ticketDao.removeTicket(hashMap.get(id));
        hashMap.remove(id);
    }
    public  void removeElement(Ticket ticket){
        ticketDao.removeTicket(ticket);
        hashMap.remove(ticket.getId());
    }
    public void removeGreater(Ticket ourTicket) {
        hashMap.values().stream()
                .filter(ticket -> ticket.getPrice() > ourTicket.getPrice())
                .forEach(this::removeElement);
    }

    public void removeGreaterKey(Long id) {
        hashMap.values().stream()
                .filter(ticket -> ticket.getId() > id)
                .peek(this::removeElement);
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

