package org.example.authorization;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;

import java.util.Collections;

public class CustomAuthenticationManager implements AuthenticationManager {

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        // Реализуйте здесь логику аутентификации
        // Например, проверка имени пользователя и пароля
        String login = authentication.getName();
        String password = authentication.getCredentials().toString();
        var result=AuthorizationManager.checkLoginAndPassword(login,password);
        var loginCorrect = result.getLeft();
        var passwordCorrect = result.getRight();
        // Проверка имени пользователя и пароля
        if (!loginCorrect ) {

            throw new BadCredentialsException("Invalid username");
        }
        if (!passwordCorrect){
            throw new BadCredentialsException("Invalid password");
        }
        return new UsernamePasswordAuthenticationToken(login, password, Collections.emptyList());

    }
}
