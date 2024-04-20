package org.common.managers;
import com.google.gson.*;

import java.lang.reflect.Type;
import java.util.Date;

/**
 * The class is responsible for serializing the date
 */
public class DateFormatting implements JsonSerializer<Date> {
    @Override
    public JsonElement serialize(Date date, Type type, JsonSerializationContext jsonSerializationContext) {
        String myString = date.toInstant().toString();
        return new JsonPrimitive(myString);
    }
}
