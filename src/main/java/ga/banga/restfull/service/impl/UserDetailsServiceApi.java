package ga.banga.restfull.service.impl;


import ga.banga.restfull.config.security.UserSpring;
import ga.banga.restfull.config.security.authorization.RolePermission;
import ga.banga.restfull.domain.enumeration.RightStandard;
import ga.banga.restfull.domain.enumeration.Roles;
import ga.banga.restfull.domain.enumeration.Table;
import ga.banga.restfull.config.security.jwt.JwtTokenUtil;
import ga.banga.restfull.domain.entity.Utilisateur;
import ga.banga.restfull.domain.exception.NotFoundException;
import ga.banga.restfull.service.UserService;
import ga.banga.restfull.utils.TokenCO;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.*;
import java.util.stream.Collectors;

import static java.lang.String.format;


/**
 * @author Romaric BANGA
 * @version 1.0
 * @since 7/6/22
 */
@Service
public class UserDetailsServiceApi implements UserDetailsService {

    private BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    private final UserService clientAuthService;

    private final JwtTokenUtil jwtTokenUtil;

    public UserDetailsServiceApi(UserService clientAuthService, JwtTokenUtil jwtTokenUtil) {
        this.clientAuthService = clientAuthService;
        this.jwtTokenUtil = jwtTokenUtil;
    }

    @Override
    @Transactional(readOnly=true)
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        try {
            Optional<?> userOp = this.clientAuthService.findByEmail(email);
            if (userOp.isPresent())
                return getUserDetailsByUser((Utilisateur) userOp.get());
            else
                throw new NotFoundException(format("User with username - %s, not found", email));
        } catch (NotFoundException e) {
            throw new UsernameNotFoundException(e.getMessage());
        }
    }
    public static UserDetails getUserDetailsByUser(Utilisateur user) {
        return new UserSpring(user.getEmail(), user.getPassword(), AuthorityUtils.createAuthorityList(getUserAuthoritiesArray(user.getRole())), user.getId());
    }
    public UserDetails getUserDetails(String email) throws NotFoundException {
        Optional<?> userOp = this.clientAuthService.findByEmail(email);
        if (userOp.isPresent())
            return getUserDetailsByUser((Utilisateur) userOp.get());
        else
            throw new NotFoundException("");
    }

    public static String[] getUserAuthoritiesArray(String rol) {
        Collection<String> authoritiesList = getUserAuthoritiesCollection(rol);
        String[] authoritiesArr = new String[authoritiesList.size()];
        return authoritiesList.toArray(authoritiesArr);
    }
    private static Set<String> getUserAuthoritiesCollection(String rol) {
        Set<String> authoritiesList = new HashSet<>();
        if (Roles.ROLE_PARTICULIER.getLibelle().equals(rol)) {
            authoritiesList.add(Roles.ROLE_PARTICULIER.getLibelle());
            RolePermission permissionPanier = new RolePermission(Table.PANIER,
                    new RightStandard[]{RightStandard.CREATE, RightStandard.READ});

            RolePermission permissionProduit = new RolePermission(Table.PRODUIT,
                    new RightStandard[]{RightStandard.READ});

            List<String> authorotiesPanier = permissionPanier.getAuthoroties();
            List<String> authorotiesProduit = permissionProduit.getAuthoroties();
            authoritiesList.addAll(authorotiesPanier);
            authoritiesList.addAll(authorotiesProduit);
        }

        if (Roles.ROLE_ENTREPRISE.getLibelle().equals(rol)) {
            authoritiesList.add(Roles.ROLE_ENTREPRISE.getLibelle());
            RolePermission permissionPanier = new RolePermission(Table.PANIER,
                    new RightStandard[]{RightStandard.CREATE, RightStandard.READ});

            RolePermission permissionProduit = new RolePermission(Table.PRODUIT,
                    new RightStandard[]{RightStandard.READ});

            List<String> authorotiesPanier = permissionPanier.getAuthoroties();
            List<String> authorotiesProduit = permissionProduit.getAuthoroties();
            authoritiesList.addAll(authorotiesPanier);
            authoritiesList.addAll(authorotiesProduit);
        }

        if (Roles.ROLE_USER.getLibelle().equals(rol)) {
            authoritiesList.add(Roles.ROLE_USER.getLibelle());

            RolePermission permission = new RolePermission(Table.PRODUIT,
                    new RightStandard[]{RightStandard.CREATE, RightStandard.READ});

            List<String> authoroties = permission.getAuthoroties();
            authoritiesList.addAll(authoroties);

          /*  authoritiesList.add(UserDetailsServiceApi.AUTHORITY_USER);
            authoritiesList.add(UserDetailsServiceApi.READ_AUTHORITY);
            authoritiesList.add(UserDetailsServiceApi.WRITE_AUTHORITY);*/
        }
        if (Roles.ROLE_ADMIN.getLibelle().equals(rol))
            authoritiesList.add(Roles.ROLE_ADMIN.getLibelle());


        return authoritiesList;
    }

    private static GrantedAuthority getAuthority(String authority) {
        return new SimpleGrantedAuthority(authority);
    }
    public static String getScopeFromRole(String role) {
        return getUserAuthoritiesCollection(role).stream().map(authority -> authority).collect(Collectors.joining( " " ));
    }
    public static boolean isAdmin(Collection<? extends GrantedAuthority> authorities) {
        return authorities.contains(UserDetailsServiceApi.getAuthority(Roles.ROLE_ADMIN.getLibelle()));
    }
//	public static String getScope(Collection<? extends GrantedAuthority> authorities) {
//		return authorities.stream().map(authority -> authority.getAuthority()).collect(Collectors.joining( " " ));
//	}
//	public static String getScope(Collection<String> authorities) {
//		return authorities.stream().map(authority -> authority).collect(Collectors.joining( " " ));
//	}


    @Transactional(readOnly=true)
    public TokenCO checkCredentialsAndGetTokens(String email, String password) {
        try {
            Optional<?> userDb = this.clientAuthService.findByEmail(email);
            UserDetails userDetails;
            if (userDb.isPresent()){
                Utilisateur user = (Utilisateur) userDb.get();
                userDetails = getUserDetailsByUser(user);
                if (!this.passwordEncoder.matches(password, userDetails.getPassword())) {
                    throw new ResponseStatusException(
                            HttpStatus.UNAUTHORIZED, "Utilisateur introuvable");
                }
                return this.jwtTokenUtil.generateTokens(email, user.getPassword(), user.getRole(), getScopeFromRole(user.getRole()));
            }
            else
                throw new BadCredentialsException("invalid password3");

        } catch (NotFoundException e) {
            throw new UsernameNotFoundException(e.getMessage());
        }
    }

    @Transactional(readOnly=true)
    public TokenCO checkCredentialsAndGetTokens(String refreshToken) {
        try {
            if (!this.jwtTokenUtil.isValidRefreshToken(refreshToken)) {
                throw new BadCredentialsException("invalid refresh token");
            }
            String email = this.jwtTokenUtil.getEmailFromToken(refreshToken);
            Optional<?> userOp = this.clientAuthService.findByEmail(email);
            if (userOp.isPresent()){
                Utilisateur user = (Utilisateur) userOp.get();
                UserDetails userDetails = getUserDetailsByUser(user);
                return this.jwtTokenUtil.generateTokens(email, userDetails.getPassword(), user.getRole(), getScopeFromRole(user.getRole()));
            }
            else
                throw new ResponseStatusException(
                        HttpStatus.UNAUTHORIZED, "Utilisateur introuvable");


        } catch (NotFoundException e) {
            throw new ResponseStatusException(
                    HttpStatus.UNAUTHORIZED, "Utilisateur introuvable");
        }
    }


}
