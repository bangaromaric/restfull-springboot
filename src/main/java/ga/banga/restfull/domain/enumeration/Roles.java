package ga.banga.restfull.domain.enumeration;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * liste des roles
 * @author Romaric BANGA
 * @version 1.0
 * @since 7/9/22
 */
@Getter
@RequiredArgsConstructor
public enum Roles {
    //Objets directement construits
    ROLE_USER("ROLE_USER"),
    ROLE_PARTICULIER("ROLE_PARTICULIER"),
    ROLE_ENTREPRISE("ROLE_ENTREPRISE"),
    ROLE_ADMIN("ROLE_ADMIN"),
    ROLE_GUEST("ROLE_GUEST");

    private final String libelle;

}