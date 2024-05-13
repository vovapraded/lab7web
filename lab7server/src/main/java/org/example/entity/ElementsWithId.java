package org.example.entity;

import lombok.Getter;
import lombok.Setter;

import java.io.Serial;
import java.io.Serializable;

/**
 * class for dto with id
 */

public abstract class ElementsWithId implements Serializable {
    @Serial
    private static final long serialVersionUID = "ElementsWithId".hashCode();

@Setter @Getter
    protected Long id;



}








