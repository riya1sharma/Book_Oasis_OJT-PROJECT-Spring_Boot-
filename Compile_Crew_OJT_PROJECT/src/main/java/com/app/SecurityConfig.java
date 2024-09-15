package com.app;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    @Autowired
    private UserService userService;  // UserService implements UserDetailsService

    // AuthenticationManager bean
    @Bean
    public AuthenticationManager authenticationManager(HttpSecurity http) throws Exception {
        AuthenticationManagerBuilder authManagerBuilder = http.getSharedObject(AuthenticationManagerBuilder.class);
        authManagerBuilder.authenticationProvider(authenticationProvider());
        return authManagerBuilder.build();
    }

    // Define PasswordEncoder as a Bean
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    // SecurityFilterChain for authorization configuration
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .authorizeRequests()
            .requestMatchers("/", "/signup", "/login", "/bookEdit", "/bookList/**", "/cart", "/contact", "/form", "/home", "/index", "/myBooks", "/Privacy", "/Terms and Condition", "/bookDonationForm", "/bookDonationSuccess").permitAll()
            .requestMatchers("/images/**", "/css/**", "/js/**").permitAll()  // Allow static resources
            .anyRequest().authenticated()  // Protect all other endpoints
            .and()
            .formLogin()
                .loginPage("/login")
                .permitAll()
                .defaultSuccessUrl("/home", true)  // Redirect after successful login
                .failureUrl("/login?error=true")   // Handle login failures
            .and()
            .logout()
                .permitAll()
                .logoutSuccessUrl("/login?logout=true");

        return http.build();
    }

    // Define DaoAuthenticationProvider bean
    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(userService);
        authProvider.setPasswordEncoder(passwordEncoder());
        return authProvider;
    }
}
