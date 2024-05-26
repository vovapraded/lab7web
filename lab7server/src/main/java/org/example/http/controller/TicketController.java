package org.example.http.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.ArrayUtils;
import org.example.Main;
import org.example.dao.TicketDao;
import org.example.entity.Coordinates;
import org.example.entity.Ticket;
import org.example.entity.Venue;
import org.example.managers.Collection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.thymeleaf.spring6.expression.Fields;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@RequiredArgsConstructor
@Controller
@RequestMapping("/tickets")
public class TicketController {
    private final Collection collection;
    private final int COUNT_ON_ONE_PAGE = 20;

    @GetMapping
    public String findAll(@RequestParam(value = "page", defaultValue = "1") int page, Model model){
        var start = (page - 1) * COUNT_ON_ONE_PAGE;
        var finish = page * COUNT_ON_ONE_PAGE;
        var tickets = collection.getList();
        int countPages = (int) Math.ceil(collection.getCountOfElements() / COUNT_ON_ONE_PAGE);
        model.addAttribute("tickets", tickets.subList(start,Math.min(finish,tickets.size())));

        List<Integer> range = IntStream.range(1, countPages).boxed().collect(Collectors.toList());
        model.addAttribute("range", range);
        model.addAttribute("count-pages", countPages);
        model.addAttribute("page", page);


        return "ticket/ticket";
    }
//    @GetMapping("/tickets/insert")
//    public String redirectToInsert(@ModelAttribute("ticket") Ticket ticket){
//        return "ticket/insert";
//    }
//    @PostMapping("/insert")
//    public String create(@ModelAttribute("ticket") @Validated Ticket ticket){
//        System.out.println(ticket);
//        ticket.setCreatedBy("vova");
//        collection.insertElement(ticket);
//        return "redirect:/tickets";
//    }
    @PostMapping("/insert")
    public String create(@Valid Ticket ticket, BindingResult bindingResult, RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            System.out.println("aboba");
            System.out.println(bindingResult.getAllErrors());
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.user", bindingResult);
            redirectAttributes.addFlashAttribute("ticket", ticket);
            return "ticket/insert";
        } else {
            // Валидация прошла успешно
            return "redirect:/tickets";
        }
    }

    @GetMapping("/insert")
    public String showTicketForm(Model model) {
        Ticket ticket = new Ticket();
        Coordinates coordinates = new Coordinates(); // Создаем объект координат
        ticket.setCoordinates(coordinates); // Устанавливаем координаты для билета
        Venue venue = new Venue();
        ticket.setVenue(venue);
        model.addAttribute("ticket", ticket);
// Добавляем билет в модель
        return "ticket/insert";
    }

}
