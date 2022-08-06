package ga.banga.restfull.service;

import ga.banga.restfull.domain.entity.Panier;

/**
 * @author Romaric BANGA
 * @version 1.0
 * @since 7/22/22
 */
public interface PanierService {
    /**
     * @inheritDoc
     * Permet de creer un panier
     * @param panier
     * @return
     */
    Panier saveAndFlush(Panier panier);



}
