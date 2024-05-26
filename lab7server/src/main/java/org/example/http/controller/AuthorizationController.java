package org.example.http.controller;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.example.authorization.AuthorizationException;
import org.example.authorization.AuthorizationManager;
import org.example.authorization.PasswordManager;
import org.example.authorization.RegistrationListener;
import org.example.connection.ResponseListener;
import org.example.dao.FailedTransactionException;
import org.example.dao.TicketDao;
import org.example.dao.UserDao;
import org.example.entity.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
@Slf4j
@RequiredArgsConstructor
@Controller
public class AuthorizationController {
//    private final UserDao userDao;
    private final RegistrationListener registrationListener;
    @GetMapping("/login")
    public String showLoginForm(User user){
        return "authorization/login";
    }
    @GetMapping("/register")
    public String showRegisterForm(User user){
        return "authorization/register";
    }

    @PostMapping("/register")
    public String register(@RequestParam("login") String login, @RequestParam("password") String password){
        try {
            log.debug("РЕГИСТРАЦИЯ НАЧАЛАСЬ");

            var user=AuthorizationManager.register(login,password);
            System.out.println(user);
            registrationListener.onRegistrationSuccess(user);
            log.debug("РЕГИСТРАЦИЯ Кончилась");
        }catch (FailedTransactionException e){
            return "/register?error"+"Попробуйте позже";
        }
        catch (AuthorizationException e){
            return "/register?error"+e.getMessage();
        }
        return "redirect:/tickets";
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
