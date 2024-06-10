package com.fwbittencourt.accountspayable.infra.security.domain.service;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

/**
 * @author <Filipe Bittencourt> on 09/06/24
 */

@Service
public class AuthenticationService {
    private final JwtService jwtService;

    public AuthenticationService(JwtService jwtService) {
        this.jwtService = jwtService;
    }

    public String authenticate(Authentication authentication) {
        return jwtService.generateToken(authentication);
    }
}
