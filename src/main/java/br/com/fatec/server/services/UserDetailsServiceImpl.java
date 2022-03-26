package br.com.fatec.server.services;

import java.util.Optional;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import br.com.fatec.server.entities.UserEntity;
import br.com.fatec.server.repositories.UserRepository;

@Component
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UserRepository userRepository;

    public UserDetailsServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Optional<UserEntity> user = userRepository.findByUseEmail(email);
        if (user.isEmpty()) {
            throw new UsernameNotFoundException("User [" + email + "] not found");
        }

        return new UserDetailsData(user);
    }
    
}
