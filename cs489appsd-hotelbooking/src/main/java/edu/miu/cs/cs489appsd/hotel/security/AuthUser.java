package edu.miu.cs.cs489appsd.hotel.security;

import edu.miu.cs.cs489appsd.hotel.entities.User;
import lombok.Builder;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;
import java.util.Objects;

@Builder
public final class AuthUser implements UserDetails {
    private final User user;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(
                "ROLE_" + Objects.requireNonNull(user.getRole(), "User role must not be null").name()
        ));
    }

    @Override
    public String getPassword() {
        return Objects.requireNonNull(user.getPassword(), "Password must not be null");
    }

    @Override
    public String getUsername() {
        return Objects.requireNonNull(user.getEmail(), "Email must not be null");
    }

    @Override
    public boolean isAccountNonExpired() {
        // Default to true for non-implemented expiry logic
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        // Default to true for non-implemented lock functionality
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        // Default to true for non-implemented credential expiry
        return true;
    }

    @Override
    public boolean isEnabled() {
        // Use existing isActive field if available, otherwise default to true
        return user.getIsActive() != null ? user.getIsActive() : true;
    }

    // Security-conscious equals/hashCode
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof AuthUser authUser)) return false;
        return getUsername().equals(authUser.getUsername());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getUsername());
    }

    @Override
    public String toString() {
        return "AuthUser{" +
                "username='" + getUsername() + '\'' +
                ", active=" + isEnabled() +
                '}';
    }
}