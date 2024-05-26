package org.example.authorization;

import lombok.Getter;
import org.example.dao.UserDao;
import org.example.entity.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

@Service
public class InMemoryUserDetailsService implements UserDetailsService {
    @Getter
    private Map<String, UserDetails> users = new ConcurrentHashMap<>();
    private final UserDao userDao;

    public InMemoryUserDetailsService(UserDao userDao) {
        this.userDao = userDao;
        // Добавление пользователей из базы данных в память
        users.putAll(userDao.findAll().stream()
                .collect(Collectors.toMap(
                        User::getLogin,
                        user -> org.springframework.security.core.userdetails.User.builder()
                                .username(user.getLogin())
                                .password(user.getPassword().toString()) // Преобразование BigInteger в строку
                                .roles("USER") // Добавление ролей при необходимости
                                .build()
                )));
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserDetails user = users.get(username);
        if (user == null) {
            throw new UsernameNotFoundException("User not found: " + username);
        }
        return user;
    }
}
