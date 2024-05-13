package org.example.http.controller;

import lombok.RequiredArgsConstructor;
import org.example.dao.TicketDao;
import org.example.entity.Coordinates;
import org.example.entity.Ticket;
import org.example.entity.Venue;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@RequiredArgsConstructor
@Controller
@RequestMapping("/tickets")
public class TicketController {
    private final TicketDao ticketDao;
    @GetMapping
    public String findAll(Model model){
//        model.addAttribute("users",userService.findAll());
//        Page<Ticket> page = ticketDao.findAll(filter,pageable);
        model.addAttribute("tickets", ticketDao.findAll());
//        model.addAttribute("filter", filter);

        return "ticket/ticket";
    }
//    @GetMapping("/tickets/insert")
//    public String redirectToInsert(@ModelAttribute("ticket") Ticket ticket){
//        return "ticket/insert";
//    }
    @PostMapping("/insert")
    public String create(@ModelAttribute("ticket") Ticket ticket){
        System.out.println(ticket);
        ticket.setCreatedBy("vova");
        ticketDao.insert(ticket);
        return "redirect:/tickets";
    }
    @GetMapping("/insert")
    public String showTicketForm(Model model) {
        Ticket ticket = new Ticket();
        Coordinates coordinates = new Coordinates(); // Создаем объект координат
        ticket.setCoordinates(coordinates); // Устанавливаем координаты для билета
        Venue venue = new Venue();
        ticket.setVenue(venue);
        model.addAttribute("ticket", ticket); // Добавляем билет в модель
        return "ticket/insert";
    }

}
