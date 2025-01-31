package com.orion.clinics.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class WebSecurityConfig {

    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .authorizeHttpRequests(authorizeRequests -> authorizeRequests
                .requestMatchers("/admin/**").authenticated()
                .requestMatchers(HttpMethod.POST,"/admin/clinic/").hasAuthority("ADMIN")
                .requestMatchers(HttpMethod.POST,"/admin/doctor/").hasAuthority("ADMIN")
                .requestMatchers(HttpMethod.GET,"/admin/clinicDummy").hasAuthority("ADMIN")
                .requestMatchers(HttpMethod.GET,"/admin/doctorDummy").hasAuthority("ADMIN")
                .requestMatchers(HttpMethod.GET,"/admin/clinics/**").hasAnyAuthority("DOCTOR","ADMIN")
                .requestMatchers(HttpMethod.GET,"/admin/doctors/**").hasAnyAuthority("DOCTOR","ADMIN")
                .requestMatchers(HttpMethod.PATCH,"/admin/clinics/**").hasAuthority("ADMIN")
                .requestMatchers(HttpMethod.PATCH,"/admin/doctors/**").hasAuthority("ADMIN")
            );
        return http.build();
    }
}
