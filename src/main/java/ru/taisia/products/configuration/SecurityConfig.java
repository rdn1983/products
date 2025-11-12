package ru.taisia.products.configuration;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.HeadersConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import ru.taisia.products.security.JwtTokenAuthFilter;
import ru.taisia.products.security.XssProtectionFilter;

import javax.sql.DataSource;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http, JwtTokenAuthFilter jwtTokenAuthFilter, XssProtectionFilter xssProtectionFilter) throws Exception {
        // Настройка заголовков безопасности для защиты от XSS
        http.headers(headers -> headers
                .contentSecurityPolicy(csp -> csp
                        .policyDirectives("default-src 'self'; script-src 'none'; style-src 'none'; img-src 'none'; font-src 'none'; connect-src 'self'; frame-ancestors 'none'; form-action 'none'; base-uri 'self';")
                )
                .frameOptions(HeadersConfigurer.FrameOptionsConfig::deny)
                .httpStrictTransportSecurity(hsts -> hsts
                        .maxAgeInSeconds(31536000)
                )
        );
        
        http.authorizeHttpRequests(auth -> auth
                        .requestMatchers("/auth/login").permitAll()
                        .requestMatchers("/api/data").authenticated()
                )
                .addFilterBefore(xssProtectionFilter, UsernamePasswordAuthenticationFilter.class)
                .addFilterAt(jwtTokenAuthFilter, UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }

    @Bean
    public UserDetailsManager users(DataSource dataSource) {
        JdbcUserDetailsManager userDetailsManager = new JdbcUserDetailsManager(dataSource);
        userDetailsManager.setUsersByUsernameQuery(
                "SELECT username, password, enabled FROM users WHERE username = ?");
        userDetailsManager.setAuthoritiesByUsernameQuery(
                "SELECT username, authority FROM authorities WHERE username = ?");
        return userDetailsManager;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
