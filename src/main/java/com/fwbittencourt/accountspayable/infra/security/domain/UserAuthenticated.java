package com.fwbittencourt.accountspayable.infra.security.domain;

import com.fwbittencourt.accountspayable.infra.security.domain.entity.TbUser;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

/**
 * @author <Filipe Bittencourt> on 09/06/24
 */
public class UserAuthenticated implements UserDetails {
    private final TbUser tbUser;

    public UserAuthenticated(TbUser user) {
        this.tbUser = user;
    }

    @Override
    public String getUsername() {
        return tbUser.getUsername();
    }

    @Override
    public String getPassword() {
        return tbUser.getPassword();
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(() -> "read");
    }
}