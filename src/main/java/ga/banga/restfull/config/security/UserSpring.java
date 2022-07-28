package ga.banga.restfull.config.security;

import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

/**
 * Responsable de recuperer les informations de l'utilisateur
 * @author Romaric BANGA
 * @version 1.0
 * @since 7/6/22
 */
public class UserSpring extends org.springframework.security.core.userdetails.User {
    private static final long serialVersionUID = 6816604814556898835L;

    private Long id;
    public UserSpring(String username, String password, Collection<? extends GrantedAuthority> authorities) {
        super(username, password, authorities);
    }
    public UserSpring(String username, String password, Collection<? extends GrantedAuthority> authorities, Long id) {
        super(username, password, authorities);
        this.id = id;
    }

    public UserSpring(String username, String password, boolean enabled, boolean accountNonExpired, boolean credentialsNonExpired, boolean accountNonLocked, Collection<? extends GrantedAuthority> authorities) {
        super(username, password, enabled, accountNonExpired, credentialsNonExpired, accountNonLocked, authorities);
    }
}
