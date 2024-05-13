package org.example.http.controller;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.example.authorization.AuthorizationException;
import org.example.authorization.AuthorizationManager;
import org.example.authorization.PasswordManager;
import org.example.dao.TicketDao;
import org.example.dao.UserDao;
import org.example.entity.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@Controller
public class AuthorizationController {
    private final UserDao userDao;
    @GetMapping("/login")
    public String showLoginForm(User user){
//        model.addAttribute("users",userService.findAll());
//        Page<Ticket> page = ticketDao.findAll(filter,pageable);
//        model.addAttribute("user", new User());
//        if (error != null && !error.isBlank()){
//        model.addAttribute("filter", filter);

        return "authorization/login";
    }
//    @SneakyThrows
//    @PostMapping("/login")
//    public String showLoginForm(Model model,@ModelAttribute("login")  String login, @ModelAttribute("password")  String password){
//        var result = AuthorizationManager.checkLoginAndPassword(login,password);
//        var loginCorrect =result.getLeft();
//        var passwordCorrect = result.getRight();
//        if (!loginCorrect){
//            AuthorizationException e = new AuthorizationException("Неверный логин");
//            model.addAttribute("error",e.getMessage());
//            throw e;
//        }else if (!passwordCorrect){
//            AuthorizationException e = new AuthorizationException("Неверный пароль");
//            model.addAttribute("error",e.getMessage());
//            throw e;
//        }
//        return "tickets/login";
//    }


}
