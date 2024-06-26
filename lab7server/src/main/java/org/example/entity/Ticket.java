package org.example.entity;

import jakarta.persistence.*;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import jakarta.validation.constraints.Positive;
import lombok.*;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.NumberFormat;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.Date;
/**
 * a class for storing ticket data
 */
@EqualsAndHashCode(callSuper = true)
@Getter
@Setter
@Data
@NoArgsConstructor
@Entity
@Table(name = "ticket",schema = "s409397")
public class Ticket extends ElementsWithId implements Comparable<Ticket>, Serializable {
    @Serial
    private static final long serialVersionUID = "Ticket".hashCode();
    @Id
    @Positive @NotNull
    Long id;
    @NotBlank
    private String name; //Поле не может быть null, Строка не может быть пустой
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "coordinates_id")
    @Valid
    private Coordinates coordinates; //Поле не может быть null
    @Column(name = "creation_date")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @NotNull @PastOrPresent
    private LocalDate creationDate;
    @Positive @NotNull//Поле не может быть null, Значение этого поля должно генерироваться автоматически
    private Long price; //Поле не может быть null, Значение поля должно быть больше 0
    @Positive
    private Long discount; //Поле может быть null, Значение поля должно быть больше 0
    private Boolean refundable; //Поле может быть null
    @Enumerated(EnumType.STRING)
    @Column(name = "ticket_type")
    @JdbcTypeCode(SqlTypes.NAMED_ENUM)
    @Basic(optional=false)
    @NotNull
    private TicketType ticketType; //Поле не может быть null
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "venue_id")
    @Valid
    private Venue venue; //Поле не может быть null
    @Column(name = "created_by")
    private  String createdBy; //Поле не может быть null, Строка не может быть пустой

    //конструктор без даты и id

    public Ticket(String name,Coordinates coordinates,Long price,Long discount,Boolean refundable,TicketType type,Venue venue){
        this.name= name;
        this.coordinates=coordinates;
        LocalDate currentDate = LocalDate.now();
        this.creationDate=currentDate;
        this.price=price;
        this.discount = discount;
        this.refundable = refundable;
        this.ticketType = type;
        this.venue=venue;

    }
    //конструктор без даты но с id
    public Ticket(Long id, String name,Coordinates coordinates,Long price,Long discount,Boolean refundable,TicketType type,Venue venue){
        this.id = id;
        this.name= name;
        this.coordinates=coordinates;
        LocalDate currentDate = LocalDate.now();
        this.creationDate=currentDate;
        this.price=price;
        this.discount = discount;
        this.refundable = refundable;
        this.ticketType = type;
        this.venue=venue;
    }

    //конструктор с датой и id

    public Ticket(Long id,String name,Coordinates coordinates,LocalDate creationDate,Long price,Long discount,Boolean refundable,TicketType type,Venue venue){
        this.name= name;
        this.id = id;
        this.coordinates=coordinates;
        this.creationDate = creationDate;
        this.price=price;
        this.discount = discount;
        this.refundable = refundable;
        this.ticketType = type;
        this.venue=venue;
    }





    @Override
    public int compareTo(Ticket other){
        return  Long.compare(price,other.getPrice());

    }


    @Override
    public String toString(){
        return "id "+id+
                ", name "+name+
                ", coordinates "+coordinates.toString()+
                ", creationDate "+ creationDate+
                ", price " +price+
                ", discount " +discount+
                ", refundable " +refundable+
                ", ticketType "+ ticketType +
                ", venue " + venue.toString()+
                ", created by " + createdBy;
    }
}
