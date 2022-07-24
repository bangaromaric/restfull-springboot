package ga.banga.restfull.domain.enumeration;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * @author Romaric BANGA
 * @version 1.0
 * @since 7/9/22
 */



/**
 * liste des droits Standards
 */
@RequiredArgsConstructor
@Getter
public enum RightStandard {
    //Objets directement construits

    READ( "READ"),
    UPDATE( "UPDATE"),
    CREATE( "CREATE"),
    DELETE( "DELETE");

    private final String libelle;

}
