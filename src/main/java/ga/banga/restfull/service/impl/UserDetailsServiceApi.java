package ga.banga.restfull.service.impl;


import ga.banga.restfull.config.security.UserSpring;
import ga.banga.restfull.config.security.authorization.RolePermission;
import ga.banga.restfull.domain.enumeration.RightStandard;
import ga.banga.restfull.domain.enumeration.Roles;
import ga.banga.restfull.domain.enumeration.Table;
import ga.banga.restfull.config.security.jwt.JwtTokenUtil;
import ga.banga.restfull.domain.constants.RolesConstants;
import ga.banga.restfull.domain.entity.Utilisateur;
import ga.banga.restfull.domain.exception.NotFoundException;
import ga.banga.restfull.service.UserAuthService;
import ga.banga.restfull.utils.TokenCO;
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

    public static final String AUTHORITY_ADMIN = "ADMIN_A";
//    public static final String AUTHORITY_USER = "USER_A";
    public static final String AUTHORITY_USER = "USER";
    public static final String AUTHORITY_SHOP = "SHOP_A";
    public static final String READ_AUTHORITY = "OP_READ";
    public static final String WRITE_AUTHORITY = "OP_WRITE";
    public static final String UPDATE_AUTHORITY = "OP_UPDATE";
    public static final String DELETE_AUTHORITY = "OP_DELETE";



    private BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();


    private final UserAuthService clientAuthService;


    private final JwtTokenUtil jwtTokenUtil;

    public UserDetailsServiceApi(UserAuthService clientAuthService, JwtTokenUtil jwtTokenUtil) {
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
        if (Roles.ROLE_ADMIN.getLibelle().equals(rol)) {
            authoritiesList.add(UserDetailsServiceApi.AUTHORITY_ADMIN);
          /*  authoritiesList.add(UserDetailsServiceApi.AUTHORITY_USER);
            authoritiesList.add(UserDetailsServiceApi.AUTHORITY_SHOP);*/
        }
        if (RolesConstants.ROLE_SHOP.equals(rol)) {
            authoritiesList.add(UserDetailsServiceApi.AUTHORITY_SHOP);
        }
        return authoritiesList;
    }

    private static GrantedAuthority getAuthority(String authority) {
        return new SimpleGrantedAuthority(authority);
    }
    public static String getScopeFromRole(String role) {
        return getUserAuthoritiesCollection(role).stream().map(authority -> authority).collect(Collectors.joining( " " ));
    }
    public static boolean isAdmin(Collection<? extends GrantedAuthority> authorities) {
        return authorities.contains(UserDetailsServiceApi.getAuthority(UserDetailsServiceApi.AUTHORITY_ADMIN));
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
                    throw new BadCredentialsException("invalid password");
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
            String email = this.jwtTokenUtil.getUsernameFromToken(refreshToken);
//            Optional<?> userOp = this.clientAuthService.findByUsername(username);
            Optional<?> userOp = this.clientAuthService.findByEmail(email);
            if (userOp.isPresent()){
                Utilisateur user = (Utilisateur) userOp.get();
                UserDetails userDetails = getUserDetailsByUser(user);
                return this.jwtTokenUtil.generateTokens(email, userDetails.getPassword(), user.getRole(), getScopeFromRole(user.getRole()));
            }
            else
                throw new NotFoundException("");


        } catch (NotFoundException e) {
            throw new UsernameNotFoundException(e.getMessage());
        }
    }


}
