package ga.banga.restfull.api;

import ga.banga.restfull.config.security.UserSpring;
import ga.banga.restfull.domain.enumeration.Roles;
import ga.banga.restfull.config.security.jwt.JwtTokenUtil;
import ga.banga.restfull.domain.dto.ParticulierPostDto;
import ga.banga.restfull.domain.dto.AuthRequest;
import ga.banga.restfull.domain.entity.Particulier;
import ga.banga.restfull.domain.mapper.EntrepriseMapper;
import ga.banga.restfull.domain.mapper.ParticulierMapper;
import ga.banga.restfull.service.UserService;
import ga.banga.restfull.utils.TokenCO;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;

/**
 * Responsable de la creation et du login user
 * @author Romaric BANGA
 * @version 1.0
 * @since 6/27/22
 */
@RestController
@RequestMapping("/api/public")
@Slf4j
@AllArgsConstructor
public class AuthController {

    private final UserService clientAuthService;


    private final AuthenticationManager authenticationManager;
    private final JwtTokenUtil jwtTokenUtil;
    private final ParticulierMapper particulierMapper;
    private final EntrepriseMapper entrepriseMapper;


    /**
     * Permet de d'authentifier l'utilisateur
     * @param request contient email et password
     * @return contient l'user et son token
     */
    @PostMapping("login")
    public @ResponseBody ResponseEntity<Object> login(@RequestBody @Valid AuthRequest request) {
        try {
            Authentication authenticate = authenticationManager
                    .authenticate(
                            new UsernamePasswordAuthenticationToken(
                                    request.getEmail(), request.getPassword()
                            )
                    );

            UserSpring user = (UserSpring) authenticate.getPrincipal();
            TokenCO token = jwtTokenUtil.generateTokens(user, "");
            HashMap<String,Object> retour = new HashMap<>();
            retour.put("user",user);
            retour.put("token",token);
            return ResponseEntity.ok()
                    .header(
                            HttpHeaders.AUTHORIZATION,
                            token.getAccess_token()
                    )
                    .body(retour /*userAuthMapper.userAuthToUserAuthDto(user)*/);
        } catch (BadCredentialsException ex) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
    }

    /**
     * insert un user de type particulier
     * @param request contient email et password
     * @return contient l'user
     */
    @PostMapping("addparticulier")
    @ExceptionHandler( MethodArgumentNotValidException.class)
    public @ResponseBody ResponseEntity<?> addParticulier(@RequestBody @Valid ParticulierPostDto request) {
        Particulier user = particulierMapper.particulierPostDtoToParticulier(request);
        user.setRole(Roles.ROLE_PARTICULIER.getLibelle());
        Particulier userSave = clientAuthService.saveAndFlushParticulier(user);
        return new ResponseEntity<>(particulierMapper.particulierToParticulierGetDto(userSave),HttpStatus.CREATED);
    }


}
