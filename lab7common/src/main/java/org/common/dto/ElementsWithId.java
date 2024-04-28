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

@Setter @Getter
    protected Long id;



}








