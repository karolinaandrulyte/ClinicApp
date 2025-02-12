package com.orion.clinics.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class WebSecurityConfig {

    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.
                authorizeHttpRequests(authorizeRequests -> authorizeRequests
                                .requestMatchers("/cities/**").permitAll()
                                .requestMatchers("/clinics/**").permitAll()
                                .requestMatchers("/clinic-status/**").permitAll()
                                .requestMatchers("/countries/**").permitAll()
                                .requestMatchers("/doctors/**").permitAll()
                                .requestMatchers("/documents/**").permitAll()
                                .requestMatchers("/record-status/**").permitAll()
                                .requestMatchers("/specialties/**").permitAll()
                                .requestMatchers("/test/**").permitAll()
                                .requestMatchers("/admin/**").hasAuthority("ADMIN")

                                .requestMatchers(HttpMethod.POST, "/cities/").hasAuthority("ADMIN")
                                .requestMatchers(HttpMethod.POST, "/clinics/**").hasAuthority("ADMIN")
                                .requestMatchers(HttpMethod.POST, "/clinic-status/").hasAuthority("ADMIN")
                                .requestMatchers(HttpMethod.POST, "/countries/").hasAuthority("ADMIN")
                                .requestMatchers(HttpMethod.POST, "/doctors/**").hasAuthority("ADMIN")
//                                .requestMatchers(HttpMethod.POST, "/documents/").hasAnyAuthority("ADMIN", "DOCTOR")
//                                .requestMatchers(HttpMethod.POST, "/documents/types/").hasAuthority("ADMIN")
                                .requestMatchers(HttpMethod.POST, "/record-status/").hasAuthority("ADMIN")
                                .requestMatchers(HttpMethod.POST, "/specialties/").hasAuthority("ADMIN")

                                .requestMatchers(HttpMethod.GET, "/cities").hasAuthority("ADMIN")
                                .requestMatchers(HttpMethod.GET, "/cities/{id}").hasAnyAuthority("ADMIN", "PATIENT")
                                .requestMatchers(HttpMethod.GET, "/clinics").hasAuthority("ADMIN")
                                .requestMatchers(HttpMethod.GET, "/clinics/{id}").hasAnyAuthority("DOCTOR", "ADMIN", "PATIENT")
                                .requestMatchers(HttpMethod.GET, "/clinics/{id}/records").hasAuthority("ADMIN")
                                .requestMatchers(HttpMethod.GET, "/clinic-status").hasAnyAuthority("DOCTOR", "ADMIN", "PATIENT")
                                .requestMatchers(HttpMethod.GET, "/clinic-status/**").hasAnyAuthority("DOCTOR", "ADMIN")
                                .requestMatchers(HttpMethod.GET, "/countries").hasAuthority("ADMIN")
                                .requestMatchers(HttpMethod.GET, "/countries/{isoCode}").hasAuthority("ADMIN")
                                .requestMatchers(HttpMethod.GET, "/doctors").hasAuthority("ADMIN")
                                .requestMatchers(HttpMethod.GET, "/doctors/{id}").hasAnyAuthority("DOCTOR", "ADMIN", "PATIENT")
                                .requestMatchers(HttpMethod.GET, "/doctors/{doctorId}/records").hasAuthority("ADMIN")
                                .requestMatchers(HttpMethod.GET, "/documents").hasAuthority("ADMIN")
//                                .requestMatchers(HttpMethod.GET, "/documents/types").hasAuthority("ADMIN")
//                                .requestMatchers(HttpMethod.GET, "/documents/{id}").hasAnyAuthority("DOCTOR", "ADMIN")
//                                .requestMatchers(HttpMethod.GET, "/documents/clinic/{clinicId}").hasAuthority("ADMIN")
                                .requestMatchers(HttpMethod.GET, "/record-status**").hasAuthority("ADMIN")
                                .requestMatchers(HttpMethod.GET, "/specialties**").hasAuthority("ADMIN")

                                .requestMatchers(HttpMethod.PATCH, "/cities/**").hasAuthority("ADMIN")
                                .requestMatchers(HttpMethod.PATCH, "/clinics/**").hasAuthority("ADMIN")
                                .requestMatchers(HttpMethod.PATCH, "/countries/**").hasAuthority("ADMIN")
                                .requestMatchers(HttpMethod.PATCH, "/doctors/**").hasAuthority("ADMIN")
//                                .requestMatchers(HttpMethod.PATCH, "/documents/**").hasAuthority("ADMIN")

                                .requestMatchers(HttpMethod.DELETE, "/cities/**").hasAuthority("ADMIN")
                                .requestMatchers(HttpMethod.DELETE, "/clinics/**").hasAuthority("ADMIN")
                                .requestMatchers(HttpMethod.DELETE, "/clinic-status/**").hasAuthority("ADMIN")
                                .requestMatchers(HttpMethod.DELETE, "/countries/**").hasAuthority("ADMIN")
                                .requestMatchers(HttpMethod.DELETE, "/doctors/**").hasAuthority("ADMIN")
//                                .requestMatchers(HttpMethod.DELETE, "/documents/**").hasAuthority("ADMIN")
                                .requestMatchers(HttpMethod.DELETE, "/record-status/**").hasAuthority("ADMIN")
                                .requestMatchers(HttpMethod.DELETE, "/specialties/**").hasAuthority("ADMIN")
                                .anyRequest().authenticated()
                )
                .oauth2ResourceServer(oauth2 -> oauth2
                        .jwt(Customizer.withDefaults()));
        return http.build();
    }
}