package org.example.authorization;

import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Component
public class CustomAuthenticationProvider implements AuthenticationProvider {

    // Ваш сервис для работы с пользователями (например, заглушка для демонстрации)



    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {

        // Реализуйте здесь логику аутентификации
        // Например, проверка имени пользователя и пароля
        String login = authentication.getName();
        Object password = authentication.getCredentials();
        if (password!=null) {
            String passwordString = password.toString();
            var result = AuthorizationManager.checkLoginAndPassword(login, passwordString);
            var loginCorrect = result.getLeft();
            var passwordCorrect = result.getRight();
            System.out.println(passwordCorrect);
            // Проверка имени пользователя и пароля
            if (!loginCorrect) {
                throw new BadCredentialsException("Invalid username");
            }
            if (!passwordCorrect) {
                throw new BadCredentialsException("Invalid password");
            }
        }
        return new UsernamePasswordAuthenticationToken(login, password, Collections.emptyList());

    }

    @Override
    public boolean supports(Class<?> authentication) {
        return authentication.equals(UsernamePasswordAuthenticationToken.class);
    }

    // Проверка соответствия пароля (может быть дополнена)
}
