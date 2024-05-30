package org.example.http.controller;

import com.google.common.collect.ImmutableList;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.example.Main;
import org.example.dao.FailedTransactionException;
import org.example.dao.TicketDao;
import org.example.entity.Coordinates;
import org.example.entity.Ticket;
import org.example.entity.Venue;
import org.example.entity.VenueType;
import org.example.filter.CoordinatesFilter;
import org.example.filter.Filter;
import org.example.filter.TicketFilter;
import org.example.filter.VenueFilter;
import org.example.managers.Collection;
import org.example.sort.Field;
import org.example.sort.Order;
import org.example.sort.Sort;
import org.example.sort.TicketSorter;
import org.hibernate.validator.internal.util.stereotypes.Immutable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.thymeleaf.spring6.expression.Fields;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static org.example.utility.EnumValidator.isValidEnum;

@RequiredArgsConstructor
@Controller
@RequestMapping("/tickets")
public class TicketController {
    private final Collection collection;
    private final TicketSorter ticketSorter;
    private final Filter filter;
    private final int COUNT_ON_ONE_PAGE = 20;

    @PostMapping("/applyFilter")
    public ResponseEntity<Void> filter(TicketFilter ticketFilter, HttpSession session, HttpServletRequest request) {
        String queryString = request.getQueryString();
        System.out.println(queryString);
        System.out.println(ticketFilter);
        session.setAttribute("ticketFilter", ticketFilter);
        return ResponseEntity.ok().build();
    }
    @PostMapping("/resetFilter")
    public ResponseEntity<Void> resetFilter(HttpSession session) {
        session.removeAttribute("ticketFilter");
        return ResponseEntity.ok().build();
    }


    @GetMapping
    public String findAll(@RequestParam(value = "page", defaultValue = "1") int page, @RequestParam(value = "sorting_by", required = false) List<String> sortingBy, Model model,HttpSession session){
        TicketFilter ticketFilter= (TicketFilter) session.getAttribute("ticketFilter");
        if (ticketFilter==null){
            ticketFilter = new TicketFilter();
        }

        List<String> validSortingBy =  Collections.emptyList();
        var sorting = parseSortingBy(sortingBy);
        System.out.println(sorting);

        var start = (page - 1) * COUNT_ON_ONE_PAGE;
        var finish = page * COUNT_ON_ONE_PAGE;
        var tickets = collection.getList();
        var filteredTickets = filter.filter(ticketFilter,tickets);
        var filteredAndSortedTickets = ticketSorter.getSortedList(filteredTickets,finish,sorting);

        System.out.println(filteredAndSortedTickets);
//        model.addAttribute("ticketFilter",ticketFilter);
        int countPages = (int) Math.ceil( filteredAndSortedTickets.size()  /(double) COUNT_ON_ONE_PAGE);
        model.addAttribute("tickets", filteredAndSortedTickets.subList(start,Math.min(finish,filteredAndSortedTickets.size())));

        List<Integer> range = IntStream.range(1, countPages+1).boxed().collect(Collectors.toList());
        model.addAttribute("range", range);
        model.addAttribute("count-pages", countPages);
        model.addAttribute("page", page);

        model.addAttribute("ticketFilter", ticketFilter);
        return "ticket/ticket";
    }
    private  List<Sort> parseSortingBy(List<String> sortingBy) {
        if (sortingBy != null) {
            return sortingBy.stream()
                    .map(s -> s.split("_"))
                    .map(array -> Arrays.stream(array)
                            .map(String::toUpperCase) // Преобразуем каждую строку в массиве к верхнему регистру
                            .map(str -> str.replace("-", "_")).toArray())                     .filter(parts -> parts.length == 2) // Проверяем, что есть имя столбца и порядок сортировки
                    .filter(parts -> isValidEnum((String) parts[0],Field.class))
                    .filter(parts -> isValidEnum((String)parts[1], Order.class)) // Проверяем валидность порядка сортировки
// Проверяем валидность порядка сортировки
                    .map(parts -> new Sort(Field.valueOf((String) parts[0]),Order.valueOf((String) parts[1]) ) )// Соединяем имя столбца и порядок сортировки обратно в строку
                    .collect(Collectors.toList());
        } else {
            var sorts = new ArrayList<Sort>();
            sorts.add(new Sort(Field.ID,Order.ASC));
            return sorts;
        }
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
public ResponseEntity<?> create(@Valid Ticket ticket, BindingResult bindingResult) {
    Map<String, String> errors = new HashMap<>();
    if (bindingResult.hasErrors()) {
        for (FieldError error : bindingResult.getFieldErrors()) {
            errors.put(error.getField(), error.getDefaultMessage());
        }
        return ResponseEntity.badRequest().body(errors);
    } else {
        try {
            // Получение текущего пользователя из контекста безопасности
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            String username = authentication.getName();

            // Установка createdBy
            ticket.setCreatedBy(username);
            collection.insertElement(ticket);
            return ResponseEntity.ok().build();

        }catch (FailedTransactionException e){
            errors.put("responseError",e.getCause().getMessage());
            return ResponseEntity.badRequest().body(errors);
        }
    }
}
//    @PostMapping("/insert")
//    public String create(@Valid Ticket ticket, BindingResult bindingResult, RedirectAttributes redirectAttributes) {
//        if (bindingResult.hasErrors()) {
//            System.out.println("aboba");
//            System.out.println(bindingResult.getAllErrors());
////            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.user", bindingResult);
//            redirectAttributes.addFlashAttribute("ticket", ticket);
//            return "ticket/insert";
//        } else {
//            try {
//                // Получение текущего пользователя из контекста безопасности
//                Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//                String username = authentication.getName();
//
//                // Установка createdBy
//                ticket.setCreatedBy(username);
//                collection.insertElement(ticket);
//
//            }catch (FailedTransactionException e){
//                bindingResult.addError(new ObjectError(e.getCause().toString(),e.getCause().getMessage()));
//                return "ticket/insert";
//            }
//
//            return "redirect:/tickets";
//        }
//    }

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
