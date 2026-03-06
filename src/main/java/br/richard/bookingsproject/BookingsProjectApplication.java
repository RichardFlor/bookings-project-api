package br.richard.bookingsproject;

import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@Slf4j
@SpringBootApplication
@EnableJpaAuditing
@SecurityScheme(type = SecuritySchemeType.HTTP, name = "jwt", scheme = "bearer", bearerFormat = "JWT")
public class BookingsProjectApplication {

    public static void main(String[] args) {
        SpringApplication.run(BookingsProjectApplication.class, args);

        log.info("""
                
                ▗▄▄▖           ▗▖     █                \s
                ▐▛▀▜▌          ▐▌     ▀                \s
                ▐▌ ▐▌ ▟█▙  ▟█▙ ▐▌▟▛  ██  ▐▙██▖ ▟█▟▌▗▟██▖
                ▐███ ▐▛ ▜▌▐▛ ▜▌▐▙█    █  ▐▛ ▐▌▐▛ ▜▌▐▙▄▖▘
                ▐▌ ▐▌▐▌ ▐▌▐▌ ▐▌▐▛█▖   █  ▐▌ ▐▌▐▌ ▐▌ ▀▀█▖
                ▐▙▄▟▌▝█▄█▘▝█▄█▘▐▌▝▙ ▗▄█▄▖▐▌ ▐▌▝█▄█▌▐▄▄▟▌
                ▝▀▀▀  ▝▀▘  ▝▀▘ ▝▘ ▀▘▝▀▀▀▘▝▘ ▝▘ ▞▀▐▌ ▀▀▀\s
                                               ▜█▛▘    \s
                
                  BOOKINGS PROJECT :: 0.1
                \s""");
    }
}
