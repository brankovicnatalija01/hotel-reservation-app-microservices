package com.hotelapp.userservice.security;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.hotelapp.userservice.entity.User;
import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

public class UserDetailsImpl implements UserDetails {

    @Getter
    private final Long id;
    private final String email;
    @JsonIgnore
    private final String password;
    private final Collection<? extends GrantedAuthority> authorities;

    public UserDetailsImpl(Long id, String email, String password, Collection<? extends GrantedAuthority> authorities) {
        this.id = id;
        this.email = email;
        this.password = password;
        this.authorities = authorities;
    }

    public static UserDetailsImpl build(User user) {
        String roleName = Optional.ofNullable(user.getRole())
                .map(role -> role.getName())
                .orElse("USER");

        if (!roleName.startsWith("ROLE_")) roleName = "ROLE_" + roleName;

        List<GrantedAuthority> auths = Collections.singletonList(new SimpleGrantedAuthority(roleName));
        return new UserDetailsImpl(user.getId(), user.getEmail(), user.getPassword(), auths);
    }

    @Override public Collection<? extends GrantedAuthority> getAuthorities() { return authorities; }
    @Override public String getPassword() { return password; }
    @Override public String getUsername() { return email; }
    @Override public boolean isAccountNonExpired() { return true; }
    @Override public boolean isAccountNonLocked() { return true; }
    @Override public boolean isCredentialsNonExpired() { return true; }
    @Override public boolean isEnabled() { return true; }
}