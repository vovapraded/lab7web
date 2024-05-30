package org.example.filter;

import lombok.*;
import org.example.entity.Ticket;
import org.example.entity.TicketType;
import org.example.entity.VenueType;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;

@Builder
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class TicketFilter {
    private Long idMax;
    private Long idMin;
    private String partOfName;
    private Long priceMin;
    private Long priceMax;
    private Long discountMin;
    private Long discountMax;
    private LocalDate dateMin;
    private LocalDate dateMax;


    private ArrayList<Boolean> refundable = new ArrayList<>(Arrays.asList(Boolean.TRUE,Boolean.FALSE,null));
    private VenueFilter venueFilter = new VenueFilter();
    private CoordinatesFilter coordinatesFilter = new CoordinatesFilter();
    private ArrayList<TicketType> ticketTypes =   new ArrayList<>(Arrays.asList(TicketType.USUAL, TicketType.VIP, TicketType.BUDGETARY));
}
