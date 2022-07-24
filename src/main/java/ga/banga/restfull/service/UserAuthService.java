package ga.banga.restfull.service;


import ga.banga.restfull.domain.entity.Entreprise;
import ga.banga.restfull.domain.entity.Particulier;

import java.util.Optional;

/**
 * @author Romaric BANGA
 * @version 1.0
 * @since 7/4/22
 */
public interface UserAuthService {


    Entreprise saveAndFlushEntreprise(Entreprise entreprise);
    Particulier saveAndFlushParticulier(Particulier particulier);

    Optional<?> findByEmail(String email);



}
