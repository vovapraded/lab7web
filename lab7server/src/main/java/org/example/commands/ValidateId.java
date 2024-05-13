package org.example.commands;

import org.example.managers.Collection;
import org.example.utility.InvalidFormatException;
import org.example.utility.*;


/**
 * A class for checking element IDs
 */
public class ValidateId {
    public static Long validateId(String idStr, boolean mustBeUnique, Collection collection){
        if (!Validator.validate(idStr, TypesOfArgs.Long,false)){
            throw new InvalidFormatException("Id должен быть числом");
        }
        Long id = Long.parseLong(idStr);
        if (id<=0){
            throw new InvalidFormatException("Id должен быть больше нуля");
        }
        String ne = mustBeUnique ? "" :" не";
        if (mustBeUnique == collection.getHashMap().containsKey(id)) {
            throw new InvalidFormatException("Неправильный формат ввода id"+ne +" должен быть уникальным");
        }
        return Long.parseLong(idStr);
    }
}
