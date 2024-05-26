package org.example.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.io.Serializable;

/**
 * a class for storing coordinates data
 */
@Data
@NoArgsConstructor
@Entity
@Table(name = "coordinates",schema = "s409397")
public class Coordinates implements Serializable {
    @Serial
    private static final long serialVersionUID = "Coordinates".hashCode();
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE,generator = "coord_seq")
    @SequenceGenerator(name = "coord_seq", sequenceName = "coordinates_id_seq",schema = "s409397", allocationSize = 1)
    private Long id;
    @NotNull
    private Double x; //Поле не может быть null
    @NotNull @Min(-617L)
    private long y; //Значение поля должно быть больше -618
    public Coordinates(Double x, long y) {
        this.x = x;
        this.y = y;

    }
    @Override
    public String toString(){
        return x+";"+y+" id:"+id;
    }
}
