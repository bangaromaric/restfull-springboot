package ga.banga.restfull.api;

import ga.banga.restfull.domain.dto.AuthRefreshTokenRequest;

import ga.banga.restfull.domain.dto.ParticulierPostDto;
import ga.banga.restfull.domain.dto.AuthRequest;
import ga.banga.restfull.domain.entity.Particulier;
import ga.banga.restfull.domain.entity.Utilisateur;
import ga.banga.restfull.domain.mapper.EntrepriseMapper;
import ga.banga.restfull.domain.mapper.ParticulierMapper;
import ga.banga.restfull.service.UserService;
import ga.banga.restfull.utils.TokenCO;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.Optional;

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
    private final ParticulierMapper particulierMapper;
    private final EntrepriseMapper entrepriseMapper;


    /**
     * Permet de d'authentifier l'utilisateur
     * @param request contient email et password
     * @return contient l'user et son token
     */
    @PostMapping("login")
    public ResponseEntity<Object> login(@RequestBody @Valid AuthRequest request) {

        Optional<?> userOp = clientAuthService.findByEmail(request.getEmail());
        Utilisateur user;
           if (userOp.isPresent()){
               user = (Utilisateur) userOp.get();
               user.getPassword().equals(request.getPassword());
               return ResponseEntity.ok()
                       .body(user);
           }else
               return new ResponseEntity<>("Utilisateur introuvable",HttpStatus.NOT_FOUND);
    }

    /**
     * insert un user de type particulier
     * @param request contient email et password
     * @return contient l'user
     */
    @PostMapping("addparticulier")
    @ExceptionHandler( MethodArgumentNotValidException.class)
    public ResponseEntity<?> addParticulier(@RequestBody @Valid ParticulierPostDto request) {
        Particulier user = particulierMapper.particulierPostDtoToParticulier(request);
//        user.setRole(Roles.ROLE_PARTICULIER.getLibelle());
        Particulier userSave = clientAuthService.saveAndFlushParticulier(user);
        return new ResponseEntity<>(particulierMapper.particulierToParticulierGetDto(userSave),HttpStatus.CREATED);
    }

}
