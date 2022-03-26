package br.com.fatec.server.services;

import java.util.Collection;
import java.util.Optional;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import br.com.fatec.server.entities.UserEntity;

public class UserDetailsData implements UserDetails {

    private final Optional<UserEntity> user;

    public UserDetailsData(Optional<UserEntity> user) {
        this.user = user;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
    }

    @Override
    public String getPassword() {
        return user.orElse(new UserEntity()).getUsePassword();
    }

    @Override
    public String getUsername() {
        return user.orElse(new UserEntity()).getUseEmail();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

}
