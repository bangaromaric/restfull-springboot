package ga.banga.restfull.domain.enumeration;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * liste des tables
 * @author Romaric BANGA
 * @version 1.0
 * @since 7/9/22
 */
@Getter
@RequiredArgsConstructor
public enum Table {
    CLIENT("CLIENT"),
    PARTICULIER("PARTICULIER"),
    ENTREPRISE("ENTREPRISE"),
    PRODUIT("PRODUIT"),
    PANIER("PANIER");


    private final String libelle;
}