package org.common.dto;

import lombok.Getter;
import lombok.Setter;

import java.io.Serial;
import java.io.Serializable;
import java.util.Date;
/**
 * a class for storing ticket data
 */
@Getter
@Setter
public class Ticket extends ElementsWithId implements Comparable<Ticket>, Serializable {
    @Serial
    private static final long serialVersionUID = "Ticket".hashCode();
    private String name; //Поле не может быть null, Строка не может быть пустой
    private Coordinates coordinates; //Поле не может быть null
    private Date creationDate; //Поле не может быть null, Значение этого поля должно генерироваться автоматически
    private Long price; //Поле не может быть null, Значение поля должно быть больше 0
    private Long discount; //Поле может быть null, Значение поля должно быть больше 0, Максимальное значение поля: 100
    private Boolean refundable; //Поле может быть null
    private TicketType type; //Поле не может быть null
    private Venue venue; //Поле не может быть null
    //конструктор без даты и id
    public Ticket(String name,Coordinates coordinates,Long price,Long discount,Boolean refundable,TicketType type,Venue venue){
        this.name= name;
        this.coordinates=coordinates;
        Date currentDate = new Date();
        this.creationDate=currentDate;
        this.price=price;
        this.discount = discount;
        this.refundable = refundable;
        this.type = type;
        this.venue=venue;
        addToInstance(this);
    }
    //конструктор без даты но с id
    public Ticket(Long id, String name,Coordinates coordinates,Long price,Long discount,Boolean refundable,TicketType type,Venue venue){
        this.id = id;
        this.name= name;
        this.coordinates=coordinates;
        Date currentDate = new Date();
        this.creationDate=currentDate;
        this.price=price;
        this.discount = discount;
        this.refundable = refundable;
        this.type = type;
        this.venue=venue;
        addToInstance(this);
    }

    //конструктор с датой и id

    public Ticket(Long id,String name,Coordinates coordinates,Date creationDate,Long price,Long discount,Boolean refundable,TicketType type,Venue venue){
        this.name= name;
        this.id = id;
        this.coordinates=coordinates;
        this.creationDate = creationDate;
        this.price=price;
        this.discount = discount;
        this.refundable = refundable;
        this.type = type;
        this.venue=venue;
        addToInstance(this);
    }





    @Override
    public int compareTo(Ticket other){
        return  Long.compare(price,other.getPrice());

    }


    @Override
    public String toString(){
        return "id "+id+
                ", name "+name+
                ", coordinates "+getCoordinates().toString()+
                ", creationDate "+ creationDate+
                ", price " +price+
                ", discount " +discount+
                ", refundable " +refundable+
                ", ticketType "+type+
                ", venue " + venue.toString();
    }
}
