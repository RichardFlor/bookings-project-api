package br.richard.bookingsproject.security.config;

import br.richard.bookingsproject.security.dto.RouteDTO;
import br.richard.bookingsproject.security.filters.AuthenticationFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.util.List;

import static br.richard.bookingsproject.enums.UserRole.ADMIN;
import static br.richard.bookingsproject.enums.UserRole.CUSTOMER;
import static org.springframework.http.HttpMethod.*;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final AuthenticationFilter authenticationFilter;

    private static final String[] SWAGGER_RESOURCES = {
            "/swagger-ui/**",
            "/v3/api-docs/**",
            "/swagger-resources/**",
            "/docs/**"
    };

    private static final RouteDTO PUBLIC_ROUTES = new RouteDTO()
            .setPaths(POST, List.of(
                    "/login",
                    "/users",
                    "/users/email-exists"
            ))
            .setPaths(PATCH, List.of(
                    "/require-password-recovery",
                    "/validate-password-recovery-code",
                    "/users/change-password"
            ))
            .setPaths(GET, List.of(
                    "/users/*/validate-email",
                    "/files/*/*",
                    "/rental-types",
                    "/rental-types/available"
            ));

    private static final RouteDTO PRIVATE_ROUTES = new RouteDTO()
            .setPaths(GET, List.of(
                    "/users/*"
            ))
            .setPaths(PATCH, List.of(
                    "/users/*"
            ))
            .setPaths(DELETE, List.of(
                    "/users/*"
            ));

    private static final RouteDTO ADMIN_ROUTES = new RouteDTO()
            .setRoles(ADMIN)
            .setPaths(GET, List.of(
                    "/users"
            ))
            .setPaths(POST, List.of(
                    "/rental-types"
            ))
            .setPaths(PATCH, List.of(
                    "/rental-types/*"
            ))
            .setPaths(DELETE, List.of(
                    "/rental-types/*"
            ));

    private static final RouteDTO CUSTOMER_ROUTES = new RouteDTO()
            .setRoles(CUSTOMER)
            .setPaths(GET, List.of(
                    "/reservations/my-reservations"
            ))
            .setPaths(PATCH, List.of(
                    "/reservations/*"
            ));

    private static final RouteDTO ADMIN_AND_CUSTOMER_ROUTES = new RouteDTO()
            .setRoles(ADMIN, CUSTOMER)
            .setPaths(POST, List.of(
                    "/reservations"
            ))
            .setPaths(DELETE, List.of(
                    "/reservations/*"
            ));

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {

        return httpSecurity
                .csrf(AbstractHttpConfigurer::disable)
                .sessionManagement(session ->
                        session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(authorize -> authorize

                        .requestMatchers(PATCH, PUBLIC_ROUTES.getPathsByMethod(PATCH)).permitAll()
                        .requestMatchers(POST, PUBLIC_ROUTES.getPathsByMethod(POST)).permitAll()
                        .requestMatchers(GET, PUBLIC_ROUTES.getPathsByMethod(GET)).permitAll()

                        .requestMatchers(GET, PRIVATE_ROUTES.getPathsByMethod(GET)).authenticated()
                        .requestMatchers(PATCH, PRIVATE_ROUTES.getPathsByMethod(PATCH)).authenticated()
                        .requestMatchers(DELETE, PRIVATE_ROUTES.getPathsByMethod(DELETE)).authenticated()

                        .requestMatchers(GET, ADMIN_ROUTES.getPathsByMethod(GET))
                        .hasAnyAuthority(ADMIN_ROUTES.getRoles())

                        .requestMatchers(POST, ADMIN_ROUTES.getPathsByMethod(POST))
                        .hasAnyAuthority(ADMIN_ROUTES.getRoles())

                        .requestMatchers(PATCH, ADMIN_ROUTES.getPathsByMethod(PATCH))
                        .hasAnyAuthority(ADMIN_ROUTES.getRoles())

                        .requestMatchers(DELETE, ADMIN_ROUTES.getPathsByMethod(DELETE))
                        .hasAnyAuthority(ADMIN_ROUTES.getRoles())

                        .requestMatchers(GET, CUSTOMER_ROUTES.getPathsByMethod(GET))
                        .hasAnyAuthority(CUSTOMER_ROUTES.getRoles())

                        .requestMatchers(PATCH, CUSTOMER_ROUTES.getPathsByMethod(PATCH))
                        .hasAnyAuthority(CUSTOMER_ROUTES.getRoles())

                        .requestMatchers(POST, ADMIN_AND_CUSTOMER_ROUTES.getPathsByMethod(POST))
                        .hasAnyAuthority(ADMIN_AND_CUSTOMER_ROUTES.getRoles())

                        .requestMatchers(DELETE, ADMIN_AND_CUSTOMER_ROUTES.getPathsByMethod(DELETE))
                        .hasAnyAuthority(ADMIN_AND_CUSTOMER_ROUTES.getRoles())

                        .requestMatchers(SWAGGER_RESOURCES).permitAll()

                        .anyRequest().authenticated()
                )
                .addFilterBefore(authenticationFilter, UsernamePasswordAuthenticationFilter.class)
                .build();
    }

    @Bean
    public AuthenticationManager authenticationManager(
            AuthenticationConfiguration authenticationConfiguration
    ) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}