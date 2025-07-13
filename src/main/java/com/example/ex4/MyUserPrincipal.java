package com.example.ex4;

import java.util.Collection;
import java.util.List;
import com.example.ex4.entity.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

/**
 * Implementation of Spring Security's {@link UserDetails} interface
 * that wraps the application's {@link User} entity.
 * <p>
 * This principal is used to provide user details and authorities to the Spring Security context.
 *
 * @see User
 */
public class MyUserPrincipal implements UserDetails {

    /** Serialization UID for compatibility. */
    private static final long serialVersionUID = 1L;

    /** The underlying user entity. */
    private final User user;

    /**
     * Constructs a new {@code MyUserPrincipal} from a user entity.
     *
     * @param user the user entity to wrap
     */
    public MyUserPrincipal(User user) {
        this.user = user;
    }

    /**
     * Returns the username used to authenticate the user.
     *
     * @return the user's username
     */
    @Override
    public String getUsername() {
        return user.getUserName();
    }

    /**
     * Returns the password used to authenticate the user.
     *
     * @return the user's password hash
     */
    @Override
    public String getPassword() {
        return user.getPassword();
    }

    /**
     * Returns the authorities granted to the user.
     *
     * @return a collection of granted authorities
     */
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority("ROLE_" + user.getRole()));
    }

    /** @return true - user account is not expired */
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    /** @return true - user account is not locked */
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    /** @return true - user credentials are not expired */
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    /** @return true - user account is enabled */
    @Override
    public boolean isEnabled() {
        return true;
    }

    /**
     * Returns the wrapped user entity.
     *
     * @return the {@link User} object
     */
    public User getUser() {
        return user;
    }
}
