package org.example.managers;

import org.example.Main;
import org.example.entity.*;
import org.example.utility.Console;
import org.example.utility.InvalidFormatException;
import org.example.utility.TypesOfArgs;
import org.example.utility.Validator;


/**
 * The class is responsible for creating a ticket from the console
 */

public class CreateTicket {
    private  final Console console;
    private static final Collection collection=   Main.getContext().getBean(Collection.class);
    ;
    public CreateTicket(Console console){
        this.console = console;
    }
    public  Ticket createTicket(Long id) throws InvalidFormatException {
        String name = "";
        while (name.isEmpty() || name.contains(" ")|| name.contains("\t")|| name.contains("\n")) {
            console.print("Введите название билета");
            name = console.getInputFromCommand(1, 1);
            if (name.isEmpty()|| name.contains(" ")|| name.contains("\t")|| name.contains("\n")) {
                console.print("Имя не должно быть пустым, не должно содержать пробелов");
            }
        }
        Long price = -1L;
        while (price <= 0) {
            console.print("Введите цену");
            String priceStr = console.getInputFromCommand(1, 1);
            if (!Validator.validate(priceStr, TypesOfArgs.Long, false)) {
                console.print("Цена должна быть числом больше 0");
            } else if (Long.parseLong(priceStr) <= 0) {
                console.print("Цена должна быть числом больше 0");
            } else {
                price = Long.parseLong(priceStr);
            }

        }

        Long discount = -1L;
        while (discount != null && discount <= 0  ) {
            console.print("Введите скидку или пустую строку");
            String discountStr = console.getInputFromCommand(0, 1);
            if (Validator.validate(discountStr, TypesOfArgs.Long, false)) {
                discount = Long.parseLong(discountStr);
            } else if (discountStr.isEmpty()) {
                discount = null;
                break;
            }
            if (discount <= 0) {
                console.print("Скидка, если есть, должна быть числом больше 0");
            }
        }


        Boolean refundable = null;
        console.print("Введите возможность возврата или пустую строку");
        String refaundableStr = console.getInputFromCommand(0, 1);
        while (!Validator.validate(refaundableStr, TypesOfArgs.Boolean, true)) {
            console.print("Возможность возврата, если есть, должна быть \"true\" или \"false\"");
            console.print("Введите возможность возврата или пустую строку");
            refaundableStr = console.getInputFromCommand(0, 1);
        }
        if (!refaundableStr.isEmpty()) {
            refundable = Boolean.parseBoolean(refaundableStr);
        }


//        считываем ticketType

        String ticketTypeStr = "";
        console.print("Введите тип билета");
        for (
                TicketType type : TicketType.values()) {
            console.print(type.name());
        }
        ticketTypeStr = console.getInputFromCommand(1, 1);
        while (!Validator.validate(ticketTypeStr, TypesOfArgs.TicketType, false)) {
            console.print("Вы неверно ввели тип билета");
            console.print("Введите тип билета");
            for (TicketType type : TicketType.values()) {
                console.print(type.name());
            }
            ticketTypeStr = console.getInputFromCommand(1, 1);
        }
        TicketType ticketType = TicketType.valueOf(ticketTypeStr.toUpperCase());
        Double x = null;
        //считываем x
        while (x == null) {
            console.print("Введите координату X, где X число c плавающей точкой");
            String xstr = console.getInputFromCommand(1, 1);
            if (!Validator.validate(xstr, TypesOfArgs.Double, false)) {
                console.print("X должен быть числом с плавающей точкой");
            } else {
                x = Double.parseDouble(xstr);
            }
        }
        Long y = Long.valueOf(-1000);
        String ystr;
        //считываем y
        while (y <= -618) {
            console.print("Введите координату Y, где Y целое число > -618");
            ystr = console.getInputFromCommand(1, 1);
            if (!Validator.validate(ystr, TypesOfArgs.Long, false)) {
                console.print("Y должен быть числом");
            } else {
                y = (long) Long.parseLong(ystr);
                if (y <= -618) {
                    console.print("Y должен быть больше -618");
                }

            }
        }
        //считываем venueName
        String venueName = "";
        while (venueName.isEmpty()) {
            console.print("Введите Место встречи");
            venueName = console.getInputFromCommand(1, 1);
            if (venueName.isEmpty()) {
                console.print("Неверный формат ввода, вы не ввели название места встречи");
            }
        }
        //считываем venueCapacity
        Long venueCapacity = -1L;
        while (venueCapacity <= 0) {
            console.print("Введите вместимость места встречи или пустую строку");
            String venueCapacityStr = console.getInputFromCommand(0, 1);
            if (venueCapacityStr.isEmpty()) {
                venueCapacity = null;
                break;
            } else if (Validator.validate(venueCapacityStr, TypesOfArgs.Long, false)) {
                venueCapacity = Long.parseLong(venueCapacityStr);
            }
            if (venueCapacity <= 0) {
                console.print("Вместимость должна быть больше нуля");
            }

        }
        //считываем venueType
        VenueType venueType = null;
        while (venueType == null) {
            console.print("Введите тип места встречи из предложенных или пустую строку");
            for (VenueType type : VenueType.values()) {
                console.print(type.name());
            }
            String input = console.getInputFromCommand(0, 1);
            if (Validator.validate(input, TypesOfArgs.VenueType, false)) {
                venueType = VenueType.valueOf(input.toUpperCase());
            } else if (input.isEmpty()) {
                break;
            } else {
                console.print("Вы неверно ввели тип места встречи");
            }
        }
        Venue venue = new Venue(venueType, venueCapacity, venueName);
        Coordinates coordinates = new Coordinates(x, y);
        Ticket ticket = new Ticket(name, coordinates, price, discount, refundable, ticketType, venue);
        ticket.setId(id);
        return ticket;
    }
}
