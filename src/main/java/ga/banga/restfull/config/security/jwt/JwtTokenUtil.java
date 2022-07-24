package ga.banga.restfull.config.security.jwt;

import ga.banga.restfull.service.impl.UserDetailsServiceApi;
import ga.banga.restfull.utils.DateUtil;
import ga.banga.restfull.utils.TokenCO;
import io.jsonwebtoken.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;


/**
 * @author Romaric BANGA
 * @version 1.0
 * @since 7/6/22
 */
@Component
public class JwtTokenUtil implements Serializable{

    private static final long serialVersionUID = -2550185165626007488L;

    private static final long ONE_HOUR_IN_SECONDS = 60 * 60;
    private static final long ONE_DAY_IN_SECONDS = 24 * ONE_HOUR_IN_SECONDS;
    private static final long ONE_DAY_IN_MILLIS = ONE_DAY_IN_SECONDS * 1000;
    public static final long JWT_ACCESS_TOKEN_VALIDITY_IN_SECONDS = 5 * ONE_DAY_IN_SECONDS;
    private static final long JWT_ACCESS_TOKEN_VALIDITY_IN_MILLIS = JWT_ACCESS_TOKEN_VALIDITY_IN_SECONDS * 1000;
    private static final long JWT_REFRESH_TOKEN_VALIDITY = 30 * ONE_DAY_IN_MILLIS;

    public static final String TOKEN_TYPE_BEARER = "bearer";
    private static final String TOKEN_TYPE_ACCESS_TOKEN = "access_token";
    private static final String TOKEN_TYPE_REFRESH_TOKEN = "refresh_token";
    private static final String TOKEN_TYPE_CLAIM = "token_type";
    private static final String ROLE_CLAIM = "role";
    private static final String USERNAME_CLAIM = "email";


    @Value("${jwt.secret}")
    private String secret;


    ////////////////////////////////////
    // validation
    ////////////////////////////////////
    public boolean isValidAccessToken(String token) {
        return isValid(token) && TOKEN_TYPE_ACCESS_TOKEN.equals(getTokenTypeFromToken(token)) && !StringUtils.isEmpty(getRoleFromToken(token));
    }
    public boolean isValidRefreshToken(String token) {
        return isValid(token) && TOKEN_TYPE_REFRESH_TOKEN.equals(getTokenTypeFromToken(token));
    }
    private boolean isValid(String token) {
        try {
            if (StringUtils.isEmpty(token)) {
                return false;
            }
            final String username = getUsernameFromToken(token);
            return !StringUtils.isEmpty(username) && !isTokenExpired(token);
        } catch (SignatureException | IllegalArgumentException | ExpiredJwtException | MalformedJwtException e) {
            return false;
        }
    }

    public boolean validateToken(String token, UserDetails userDetails) {
        final String username = getUsernameFromToken(token);
        return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }
    public Boolean validate(String token) {
        final String username = getUsernameFromToken(token);
        return (username != null && !isTokenExpired(token));
    }
    //check if the token has expired
    private Boolean isTokenExpired(String token) {
        final Date expiration = getExpirationDateFromToken(token);
        return expiration.before(new Date());
    }




    ////////////////////////////////////
    // generation
    ////////////////////////////////////
    public TokenCO generateTokens(UserDetails userDetails, String role) {
        return generateTokens(userDetails.getUsername(), userDetails.getPassword(), role, UserDetailsServiceApi.getScopeFromRole(role));
    }
    public TokenCO generateTokens(String username, String password, String role, String scope) {
        String access_token = generateAccessToken(username, password, role);
        int expires_in = Long.valueOf(JWT_ACCESS_TOKEN_VALIDITY_IN_SECONDS).intValue();
        String refresh_token = generateRefreshToken(username);
        return new TokenCO(access_token, expires_in, refresh_token, scope, TOKEN_TYPE_BEARER);
    }
    //generate token for user
    private String generateAccessToken(String username, String password, String role) {
        Map<String, Object> claims = new HashMap<>();
        claims.put(TOKEN_TYPE_CLAIM, TOKEN_TYPE_ACCESS_TOKEN);
        claims.put(ROLE_CLAIM, role);
        claims.put(USERNAME_CLAIM, username);
        return doGenerateToken(claims, username, JWT_ACCESS_TOKEN_VALIDITY_IN_MILLIS);
    }
    private String generateRefreshToken(String username) {
        Map<String, Object> claims = new HashMap<>();
        claims.put(TOKEN_TYPE_CLAIM, TOKEN_TYPE_REFRESH_TOKEN);
        return doGenerateToken(claims, username, JWT_REFRESH_TOKEN_VALIDITY);
    }
    //while creating the token -
    //1. Define  claims of the token, like Issuer, Expiration, Subject, and the ID
    //2. Sign the JWT using the HS512 algorithm and secret key.
    //3. According to JWS Compact Serialization(https://tools.ietf.org/html/draft-ietf-jose-json-web-signature-41#section-3.1) compaction of the JWT to a URL-safe string
    private String doGenerateToken(Map<String, Object> claims, String subject, long expiration) {
        long now = DateUtil.getNow().getTimeInMillis();
        String token = Jwts.builder()
                .setClaims(claims)
                .setSubject(subject)
                .setIssuedAt(new Date(now))
                .setExpiration(new Date(now + expiration))
                .signWith(SignatureAlgorithm.HS512, this.secret).compact();
        // log.debug("generated token for username " + subject + " = " + token);
        return token;
    }


    ////////////////////////////////////
    // get attributes
    ////////////////////////////////////
    //retrieve username from jwt token
    public String getUsernameFromToken(String token) {
        return getClaimFromToken(token, Claims::getSubject);
    }
    //retrieve expiration date from jwt token
    private Date getExpirationDateFromToken(String token) {
        return getClaimFromToken(token, Claims::getExpiration);
    }
    public String getRoleFromToken(String token) {
        final Claims claims = getAllClaimsFromToken(token);
        return claims.get(ROLE_CLAIM, String.class);
    }
    private String getTokenTypeFromToken(String token) {
        final Claims claims = getAllClaimsFromToken(token);
        return claims.get(TOKEN_TYPE_CLAIM, String.class);
    }
    //for retrieving any information from token we will need the secret key
    private Claims getAllClaimsFromToken(String token) throws SignatureException {
        return Jwts.parser().setSigningKey(this.secret).parseClaimsJws(token).getBody();
    }
    private <T> T getClaimFromToken(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = getAllClaimsFromToken(token);
        return claimsResolver.apply(claims);
    }
//    public UserDetails getUserDetailsFromToken(String token) throws NotFoundException {
//        return this.userDetailsService.getUserDetails(getUsernameFromToken(token));
//    }


}
