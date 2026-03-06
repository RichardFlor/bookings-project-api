package br.richard.bookingsproject.security.services;

import br.richard.bookingsproject.repositories.user.UserJpaRepository;
import br.richard.bookingsproject.security.dto.UserDetailsDTO;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class AuthorizationService implements UserDetailsService {

    private final UserJpaRepository userJpaRepository;
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        var user = userJpaRepository.findByEmail(username);
        user.orElseThrow(() -> new UsernameNotFoundException(username));
        return new UserDetailsDTO(user.get());
    }
}
