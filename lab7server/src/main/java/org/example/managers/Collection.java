package org.example.managers;

import lombok.Getter;
import lombok.Setter;
import org.example.Main;
import org.example.commands.authorization.NoAccessException;
import org.example.dao.TicketDao;
import org.example.entity.Ticket;
import org.example.entity.Venue;
import com.google.common.collect.ImmutableList;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

/**
 * The class that manages the collection
 */
@Service
    public  class Collection  {

//    public static Collection getInstance() {
//        return INSTANCE;
//    }
    private static final Logger logger = LoggerFactory.getLogger(Collection.class);


//    private static final Collection INSTANCE= new Collection();
    private Date currentDate;
    private final ConcurrentHashMap<Long,Ticket> hashMap = new ConcurrentHashMap<>();
    private  final TicketDao ticketDao;


    private Collection(TicketDao ticketDao){
        this.ticketDao = ticketDao;
        currentDate = new Date();
        addHashMap(ticketDao.loadCollection());
        logger.debug("Коллекция загружена. Содержит " + hashMap.size() + " элементов");

    }
    public void clearCollection(String login){
        ticketDao.clear(login);
        hashMap.values().removeIf(ticket -> ticket.getCreatedBy().equals(login));
    }
    public void insertElement(Ticket ticket){
        ticketDao.insert(ticket);
        hashMap.put(ticket.getId(),ticket);

    }
    public void updateTicket(Ticket ticket,String login){
        ticketDao.update(ticket,login);
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
    public  void removeElement(Long id,String login) throws NoAccessException {
        ticketDao.removeTicket(id,login);
        hashMap.remove(id);
    }
    public  void removeElement(Ticket ticket){
            ticketDao.removeTicket(ticket);
            hashMap.remove(ticket.getId());

    }
    public void removeGreater(Ticket ourTicket,String login) {
        List<Ticket> ticketsToRemove = hashMap.values().stream()
                .filter(ticket -> ticket.getPrice() > ourTicket.getPrice() && ticket.getCreatedBy().equals(login))
                .toList();
        ticketsToRemove.forEach(ticket -> removeElement(ticket));
    }

    public void removeGreaterKey(Long id,String login) {
        List<Ticket> ticketsToRemove = hashMap.values().stream()
                .filter(ticket -> ticket.getId() > id && ticket.getCreatedBy().equals(login))
                .toList();
        ticketsToRemove.forEach(ticket -> removeElement(ticket));
    }

    public OptionalDouble getAveragePrice(){
        OptionalDouble average = hashMap.values().stream()
                .map(ticket -> ticket.getPrice())
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

    public ConcurrentHashMap<Long, Ticket>  getHashMap() {
        return hashMap;
    }
    public List<Ticket> getList() {
        synchronized (hashMap) {
            // Создаем неизменяемую копию текущих значений
            return ImmutableList.copyOf(hashMap.values());
        }
    }


    public Date getCurrentDate() {
        return currentDate;
    }
    public int getCountOfElements(){
        return hashMap.size();
    }
}

