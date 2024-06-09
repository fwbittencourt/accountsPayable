package com.fwbittencourt.accountspayable.infra.security.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

/**
 * @author <Filipe Bittencourt> on 08/06/24
 */
@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
            .authorizeHttpRequests(
                auth -> {
                    auth.requestMatchers( "/").permitAll();
                    auth.requestMatchers( "/actuator/health").permitAll();
                    auth.anyRequest().authenticated();
                }
            ).oauth2Login(Customizer.withDefaults())
            .build();
    }
}
