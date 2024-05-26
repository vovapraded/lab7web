package org.example.authorization;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.math.BigInteger;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

@RequiredArgsConstructor
@Component
@Slf4j
public class RegistrationListener {
    @Autowired
    private final CustomAuthenticationProvider customAuthenticationProvider;
    // Метод, который будет вызываться после успешной регистрации пользователя
    public void onRegistrationSuccess(User user) {
        // Получаем имя пользователя и пароль
        String login = user.getLogin();

        // Создаем объект аутентификации с учетными данными пользователя
        Authentication authentication = new UsernamePasswordAuthenticationToken(login, null, Collections.emptyList());

        // Аутентифицируем пользователя
        authentication = customAuthenticationProvider.authenticate(authentication);

        // Сохраняем аутентификацию в контексте безопасности
        SecurityContextHolder.getContext().setAuthentication(authentication);
        log.debug("СОХРАНИЛИ");

        // Перенаправляем пользователя на нужную страницу
        // В данном случае на страницу с билетами

    }
}