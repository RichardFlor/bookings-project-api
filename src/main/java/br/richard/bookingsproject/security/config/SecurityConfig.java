package br.richard.bookingsproject.security.config;

import br.richard.bookingsproject.enums.UserRole;
import br.richard.bookingsproject.security.filters.AuthenticationFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.RegexRequestMatcher;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {
    private final AuthenticationFilter authenticationFilter;

    private static final String[] SWAGGER_RESOURCES = {
            "/swagger-ui/**",
            "/v3/api-docs/**",
            "/swagger-resources/**",
            "/docs",
            "/swagger-ui/**",
            "/swagger-ui/**",
            "/v3/api-docs/**",
            "/swagger-resources/**",
            "/docs/**"
    };

    private static final String[] PUBLIC_POST_ENDPOINTS = {
            "/login",
            "/users",
            "/users/email-exists",
            "/reservations"
    };

    private static final String[] PUBLIC_PATCH_ENDPOINTS = {
            "/require-password-recovery",
            "/validate-password-recovery-code",
            "/users/change-password"
    };

    private static final String[] PUBLIC_GET_ENDPOINTS = {
            "/users/*/validate-email",
            "/files/*/*",
            "/rental-types",
            "/rental-types/available"
    };

    private static final String[] ADMIN_POST_ENDPOINTS = {
            "/rental-types"
    };

    private static final String[] ADMIN_PATCH_ENDPOINTS = {
            "/rental-types/*"
    };

    private static final String[] ADMIN_GET_ENDPOINTS = {
            "/categories/*",
            "/users"
    };

    private static final String[] ADMIN_DELETE_ENDPOINTS = {
            "/rental-types/*",
            "/reservations/*"
    };

    private RegexRequestMatcher doRegexPath(HttpMethod method, String pathPattern) {
        return RegexRequestMatcher.regexMatcher(method, pathPattern);
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        final var UUID_MATCH = "[0-9a-fA-F]{8}(-[0-9a-fA-F]{4}){3}-[0-9a-fA-F]{12}";
        return httpSecurity
                .csrf(AbstractHttpConfigurer::disable)
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(authorize -> authorize
                        .requestMatchers(HttpMethod.PATCH, PUBLIC_PATCH_ENDPOINTS).permitAll()
                        .requestMatchers(HttpMethod.POST, PUBLIC_POST_ENDPOINTS).permitAll()
                        .requestMatchers(HttpMethod.GET, PUBLIC_GET_ENDPOINTS).permitAll()
                        .requestMatchers(HttpMethod.POST, ADMIN_POST_ENDPOINTS).hasAnyAuthority(UserRole.ADMIN.name())
                        .requestMatchers(HttpMethod.GET, ADMIN_GET_ENDPOINTS).hasAnyAuthority(UserRole.ADMIN.name())
                        .requestMatchers(HttpMethod.PATCH, ADMIN_PATCH_ENDPOINTS).hasAnyAuthority(UserRole.ADMIN.name())
                        .requestMatchers(HttpMethod.DELETE, ADMIN_DELETE_ENDPOINTS).hasAnyAuthority(UserRole.ADMIN.name())
                        .requestMatchers(
                                doRegexPath(HttpMethod.GET, "/users/" + UUID_MATCH)).hasAuthority(UserRole.ADMIN.name())
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
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}