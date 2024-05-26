package org.example.config;

//import org.example.authorization.CustomAuthenticationFailureHandler;
import lombok.RequiredArgsConstructor;
import org.example.authorization.CustomAuthenticationManager;
import org.example.authorization.CustomAuthenticationProvider;
import org.example.authorization.InMemoryUserDetailsService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.RememberMeAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.config.annotation.web.configurers.CsrfConfigurer;
import org.springframework.security.web.authentication.RememberMeServices;
import org.springframework.security.web.authentication.rememberme.RememberMeAuthenticationFilter;
import org.springframework.security.web.authentication.rememberme.TokenBasedRememberMeServices;


import static org.springframework.security.config.Customizer.withDefaults;
import static org.springframework.security.web.util.matcher.AntPathRequestMatcher.antMatcher;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
@RequiredArgsConstructor
public class SecurityConfiguration {
    private final String KEY = "ABOBA";
    private final CustomAuthenticationManager authenticationManager;
    private final InMemoryUserDetailsService userDetailsService;
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception{
        http
                .csrf(CsrfConfigurer::disable)

                .authenticationProvider(new CustomAuthenticationProvider())
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/login","/register","/swagger-v1/","/style.css").permitAll()
//                        .requestMatchers(antMatcher("users/{\\d}/delete")).hasAnyAuthority(ADMIN.getAuthority())
                        .anyRequest().authenticated())
//                .httpBasic(Customizer.withDefaults())
                .formLogin(login -> login
                        .loginPage("/login")
                        .defaultSuccessUrl("/tickets",true)
                                .usernameParameter("login")
//                        .failureHandler(new CustomAuthenticationFailureHandler())
                        .permitAll()

                ).rememberMe((remember) -> remember
                        .rememberMeServices(rememberMeServices(userDetailsService)
                        ).key(KEY)
                )


                .logout(logout -> logout
                        .logoutUrl("/logout")
                        .logoutSuccessUrl("/login")
                        .deleteCookies("JSESSIONID"));
        http.addFilterBefore(rememberMeFilter(), RememberMeAuthenticationFilter.class);

        return http.build();
    }
//    private final RememberMeServices rememberMeServices;
@Bean
public RememberMeServices rememberMeServices(UserDetailsService userDetailsService) {
    TokenBasedRememberMeServices rememberMeServices = new TokenBasedRememberMeServices(KEY, userDetailsService);
    rememberMeServices.setMatchingAlgorithm(TokenBasedRememberMeServices.RememberMeTokenAlgorithm.MD5);
    return rememberMeServices;
}

    @Bean
    public RememberMeAuthenticationFilter rememberMeFilter() {
        return new RememberMeAuthenticationFilter(authenticationManager, rememberMeServices(userDetailsService));
    }

    @Bean
    public RememberMeAuthenticationProvider rememberMeAuthenticationProvider() {
        return new RememberMeAuthenticationProvider(KEY);
    }

}
