package ga.banga.restfull.service;


import ga.banga.restfull.domain.entity.Entreprise;
import ga.banga.restfull.domain.entity.Particulier;

import java.util.Optional;

/**
 * Interface qui gere l'user
 * @author Romaric BANGA
 * @version 1.0
 * @since 7/4/22
 */
public interface UserService {

    /**
     *  @inheritDoc
     *  Creer une entreprise
     * @param entreprise
     * @return
     */
    Entreprise saveAndFlushEntreprise(Entreprise entreprise);

    /**
     * @inheritDoc
     * Creer un particulier
     * @param particulier
     * @return
     */
    Particulier saveAndFlushParticulier(Particulier particulier);

    /**
     * @inheritDoc
     * Recherche par l'email
     * @param email
     * @return
     */
    Optional<?> findByEmail(String email);



}
