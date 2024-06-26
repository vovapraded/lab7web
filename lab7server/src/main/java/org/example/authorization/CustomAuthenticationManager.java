package org.example.authorization;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Component;

import java.math.BigInteger;
import java.util.Collections;
@Component
public class CustomAuthenticationManager implements AuthenticationManager {

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {

        // Реализуйте здесь логику аутентификации
        // Например, проверка имени пользователя и пароля
        String login = authentication.getName();
        System.out.println(login);
        Object password = authentication.getCredentials();
        if (password!=null) {
            String passwordString = password.toString();
            var result = AuthorizationManager.checkLoginAndPassword(login, passwordString);
            var loginCorrect = result.getLeft();
            var passwordCorrect = result.getRight();
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


}
