package com.orion.clinics.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class WebSecurityConfig {

    public static final String ADMIN = "ADMIN";
    public static final String PATIENT = "PATIENT";
    public static final String DOCTOR = "DOCTOR";

    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.
                authorizeHttpRequests(authorizeRequests -> authorizeRequests
                                .requestMatchers("/cities/**").permitAll()
                                .requestMatchers("/clinics/**").permitAll()
                                .requestMatchers("/clinic-status/**").permitAll()
                                .requestMatchers("/countries/**").authenticated()
                                .requestMatchers("/doctors/**").permitAll()
                                .requestMatchers("/documents/**").authenticated()
                                .requestMatchers("/record-status/**").permitAll()
                                .requestMatchers("/specialties/**").authenticated()
                                .requestMatchers("/test/**").permitAll()

                                .requestMatchers("/admin/**").hasAuthority(ADMIN)
                                .requestMatchers("/record-status/**").permitAll()

                                .requestMatchers(HttpMethod.POST, "/cities/").permitAll()
                                .requestMatchers(HttpMethod.POST, "/clinics/**").permitAll()
                                .requestMatchers(HttpMethod.POST, "/clinic-status/").permitAll()
                                .requestMatchers(HttpMethod.POST, "/countries/").hasAuthority(ADMIN)
                                .requestMatchers(HttpMethod.POST, "/doctors/**").permitAll()
                                .requestMatchers(HttpMethod.POST, "/documents/").hasAnyAuthority(ADMIN, DOCTOR)
                                .requestMatchers(HttpMethod.POST, "/specialties/").hasAuthority(ADMIN)

                                .requestMatchers(HttpMethod.GET, "/cities").permitAll()
                                .requestMatchers(HttpMethod.GET, "/cities/{id}").hasAnyAuthority(ADMIN, PATIENT)
                                .requestMatchers(HttpMethod.GET, "/clinics").permitAll()
                                .requestMatchers(HttpMethod.GET, "/clinics/{id}").permitAll()
                                .requestMatchers(HttpMethod.GET, "/clinics/{id}/records").permitAll()
                                .requestMatchers(HttpMethod.GET, "/clinic-status").permitAll()
                                .requestMatchers(HttpMethod.GET, "/clinic-status/**").permitAll()
                                .requestMatchers(HttpMethod.GET, "/countries").hasAuthority(ADMIN)
                                .requestMatchers(HttpMethod.GET, "/countries/{isoCode}").hasAuthority(ADMIN)
                                .requestMatchers(HttpMethod.GET, "/doctors").permitAll()
                                .requestMatchers(HttpMethod.GET, "/doctors/{id}").permitAll()
                                .requestMatchers(HttpMethod.GET, "/doctors/{doctorId}/records").permitAll()
                                .requestMatchers(HttpMethod.GET, "/documents").hasAuthority(ADMIN)
                                .requestMatchers(HttpMethod.GET, "/documents/types").hasAuthority(ADMIN)
                                .requestMatchers(HttpMethod.GET, "/documents/{id}").hasAnyAuthority(DOCTOR, ADMIN)
                                .requestMatchers(HttpMethod.GET, "/documents/clinic/{clinicId}").hasAuthority(ADMIN)
                                .requestMatchers(HttpMethod.GET, "/specialties**").hasAuthority(ADMIN)

                                .requestMatchers(HttpMethod.PATCH, "/cities/**").permitAll()
                                .requestMatchers(HttpMethod.PATCH, "/clinics/**").permitAll()
                                .requestMatchers(HttpMethod.PATCH, "/countries/**").hasAuthority(ADMIN)
                                .requestMatchers(HttpMethod.PATCH, "/doctors/**").permitAll()
                                .requestMatchers(HttpMethod.PATCH, "/documents/**").hasAuthority(ADMIN)

                                .requestMatchers(HttpMethod.DELETE, "/cities/**").permitAll()
                                .requestMatchers(HttpMethod.DELETE, "/clinics/**").permitAll()
                                .requestMatchers(HttpMethod.DELETE, "/clinic-status/**").permitAll()
                                .requestMatchers(HttpMethod.DELETE, "/countries/**").hasAuthority(ADMIN)
                                .requestMatchers(HttpMethod.DELETE, "/doctors/**").permitAll()
                                .requestMatchers(HttpMethod.DELETE, "/documents/**").hasAuthority(ADMIN)
                                .requestMatchers(HttpMethod.DELETE, "/specialties/**").hasAuthority(ADMIN)
                                .anyRequest().permitAll()
                )
                .oauth2ResourceServer(oauth2 -> oauth2
                        .jwt(Customizer.withDefaults()));
        http.csrf(AbstractHttpConfigurer::disable); // Disable CSRF if it's blocking requests; Default JWT Configuration
        return http.build();
    }
}