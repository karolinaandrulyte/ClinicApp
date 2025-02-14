package com.orion.clinics.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
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
                                .requestMatchers("/cities/**").authenticated()
                                .requestMatchers("/clinics/**").authenticated()
                                .requestMatchers("/clinic-status/**").authenticated()
                                .requestMatchers("/countries/**").authenticated()
                                .requestMatchers("/doctors/**").authenticated()
                                .requestMatchers("/documents/**").authenticated()
                                .requestMatchers("/record-status/**").authenticated()
                                .requestMatchers("/specialties/**").authenticated()
                                .requestMatchers("/test/**").authenticated()

                                .requestMatchers("/admin/**").hasAuthority(ADMIN)
                                .requestMatchers("/record-status/**").hasAuthority(ADMIN)

                                .requestMatchers(HttpMethod.POST, "/cities/").hasAuthority(ADMIN)
                                .requestMatchers(HttpMethod.POST, "/clinics/**").hasAuthority(ADMIN)
                                .requestMatchers(HttpMethod.POST, "/clinic-status/").hasAuthority(ADMIN)
                                .requestMatchers(HttpMethod.POST, "/countries/").hasAuthority(ADMIN)
                                .requestMatchers(HttpMethod.POST, "/doctors/**").hasAuthority(ADMIN)
//                                .requestMatchers(HttpMethod.POST, "/documents/").hasAnyAuthority(ADMIN, DOCTOR)
//                                .requestMatchers(HttpMethod.POST, "/documents/types/").hasAuthority(ADMIN)
                                .requestMatchers(HttpMethod.POST, "/specialties/").hasAuthority(ADMIN)

                                .requestMatchers(HttpMethod.GET, "/cities").hasAuthority(ADMIN)
                                .requestMatchers(HttpMethod.GET, "/cities/{id}").hasAnyAuthority(ADMIN, PATIENT)
                                .requestMatchers(HttpMethod.GET, "/clinics").hasAuthority(ADMIN)
                                .requestMatchers(HttpMethod.GET, "/clinics/{id}").hasAnyAuthority(DOCTOR, ADMIN, PATIENT)
                                .requestMatchers(HttpMethod.GET, "/clinics/{id}/records").hasAuthority(ADMIN)
                                .requestMatchers(HttpMethod.GET, "/clinic-status").hasAnyAuthority(DOCTOR, ADMIN, PATIENT)
                                .requestMatchers(HttpMethod.GET, "/clinic-status/**").hasAnyAuthority(DOCTOR, ADMIN)
                                .requestMatchers(HttpMethod.GET, "/countries").hasAuthority(ADMIN)
                                .requestMatchers(HttpMethod.GET, "/countries/{isoCode}").hasAuthority(ADMIN)
                                .requestMatchers(HttpMethod.GET, "/doctors").hasAuthority(ADMIN)
                                .requestMatchers(HttpMethod.GET, "/doctors/{id}").hasAnyAuthority(DOCTOR, ADMIN, PATIENT)
                                .requestMatchers(HttpMethod.GET, "/doctors/{doctorId}/records").hasAuthority(ADMIN)
                                .requestMatchers(HttpMethod.GET, "/documents").hasAuthority(ADMIN)
//                                .requestMatchers(HttpMethod.GET, "/documents/types").hasAuthority(ADMIN)
//                                .requestMatchers(HttpMethod.GET, "/documents/{id}").hasAnyAuthority(DOCTOR, ADMIN)
//                                .requestMatchers(HttpMethod.GET, "/documents/clinic/{clinicId}").hasAuthority(ADMIN)
                                .requestMatchers(HttpMethod.GET, "/specialties**").hasAuthority(ADMIN)

                                .requestMatchers(HttpMethod.PATCH, "/cities/**").hasAuthority(ADMIN)
                                .requestMatchers(HttpMethod.PATCH, "/clinics/**").hasAuthority(ADMIN)
                                .requestMatchers(HttpMethod.PATCH, "/countries/**").hasAuthority(ADMIN)
                                .requestMatchers(HttpMethod.PATCH, "/doctors/**").hasAuthority(ADMIN)
//                                .requestMatchers(HttpMethod.PATCH, "/documents/**").hasAuthority(ADMIN)

                                .requestMatchers(HttpMethod.DELETE, "/cities/**").hasAuthority(ADMIN)
                                .requestMatchers(HttpMethod.DELETE, "/clinics/**").hasAuthority(ADMIN)
                                .requestMatchers(HttpMethod.DELETE, "/clinic-status/**").hasAuthority(ADMIN)
                                .requestMatchers(HttpMethod.DELETE, "/countries/**").hasAuthority(ADMIN)
                                .requestMatchers(HttpMethod.DELETE, "/doctors/**").hasAuthority(ADMIN)
//                                .requestMatchers(HttpMethod.DELETE, "/documents/**").hasAuthority(ADMIN)
                                .requestMatchers(HttpMethod.DELETE, "/specialties/**").hasAuthority(ADMIN)
                                .anyRequest().permitAll()
                )
                .oauth2ResourceServer(oauth2 -> oauth2
                        .jwt(Customizer.withDefaults()));       // Default JWT Configuration
        return http.build();
    }
}