package org.common.managers;

import com.google.gson.*;
import org.common.dto.Coordinates;
import org.common.dto.Ticket;
import org.common.dto.TicketType;
import org.common.dto.Venue;

import java.lang.reflect.Type;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.regex.Pattern;
/**
 * A class for deserializing ticket
 */
public class TicketFormatting implements JsonDeserializer<Ticket> {

    @Override
    public Ticket deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        JsonObject jsonObject = json.getAsJsonObject();
        // Чтение полей из JSON
        String name = jsonObject.get("name").getAsString();
        Coordinates coordinates = context.deserialize(jsonObject.get("coordinates"), Coordinates.class);
        Long price = jsonObject.get("price").getAsLong();
        Long discount =null;
        if (jsonObject.has("discount")) discount = jsonObject.get("discount").getAsLong();
        Boolean refundable= null;
        if (jsonObject.has("refundable")) {
            if (jsonObject.get("refundable").getAsString().equals("true")){
                refundable =true;
            }
            else if (jsonObject.get("refundable").getAsString().equals("false")){
                refundable =false;
            }
            else throw new JsonParseException("Ошибка чтения Билета");
        }
        Long id = jsonObject.get("id").getAsLong();

        TicketType type = context.deserialize(jsonObject.get("type"), TicketType.class);
        Venue venue = context.deserialize(jsonObject.get("venue"), Venue.class);
        String dateString = jsonObject.get("creationDate").getAsString();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
        Date date=null;
        //Проверим Дату
        try {
            String dateFormatPattern = "\\d{4}-\\d{2}-\\d{2}T\\d{2}:\\d{2}:\\d{2}\\.\\d{3}Z";
            if (!Pattern.matches(dateFormatPattern, dateString)){
                throw new ParseException("Ошибка",0);
            }
            date = dateFormat.parse(dateString);
        } catch (ParseException e) {
            throw new JsonParseException("Дата в неправильном формате");
        }
        //проверим поля что они не null


        if (date==null ||name.contains(" ")||name.contains("\n")||name.contains("\t")|| id<=0 ||price<=0||discount!=null && discount<=0||!jsonObject.has("id")||!jsonObject.has("name") ||name.isEmpty()||
                !jsonObject.has("coordinates") || !jsonObject.has("price") ||
                !jsonObject.has("type")) {
            throw new JsonParseException("Не удалось восстановить билет");
        }
        // Создание объекта Ticket

        return new Ticket(id,name, coordinates, date, price, discount, refundable, type, venue);
    }
}