package com.fwbittencourt.accountspayable.infra.security.domain.service;

import com.fwbittencourt.accountspayable.infra.security.domain.UserAuthenticated;
import com.fwbittencourt.accountspayable.infra.security.repository.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

/**
 * @author <Filipe Bittencourt> on 09/06/24
 */
@Service
public class UserDetailsServiceImpl implements UserDetailsService {
    private final UserRepository userRepository;

    public UserDetailsServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByUsername(username)
            .map(user -> new UserAuthenticated(user))
            .orElseThrow(
                () -> new UsernameNotFoundException("Não encontrado usuário com nome: " + username)
            );
    }
}