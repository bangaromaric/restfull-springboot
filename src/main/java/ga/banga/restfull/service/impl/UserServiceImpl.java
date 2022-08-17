package ga.banga.restfull.service.impl;


import ga.banga.restfull.domain.entity.Entreprise;
import ga.banga.restfull.domain.entity.Particulier;
import ga.banga.restfull.repository.UtilisateurRepository;
import ga.banga.restfull.repository.EntrepriseRepository;
import ga.banga.restfull.repository.ParticulierRepository;
import ga.banga.restfull.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

import static java.lang.String.format;

/**
 * @author Romaric BANGA
 * @version 1.0
 * @since 7/4/22
 */
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

   private final UtilisateurRepository clientRepository;
   private final ParticulierRepository particulierRepository;
   private final EntrepriseRepository entrepriseRepository;


    @Override
    public Entreprise saveAndFlushEntreprise(Entreprise entreprise) {
//        entreprise.setPassword(passwordEncoder.encode(entreprise.getPassword()));
        return entrepriseRepository.saveAndFlush(entreprise);
    }

    @Override
    public Particulier saveAndFlushParticulier(Particulier particulier) {
//        particulier.setPassword(passwordEncoder.encode(particulier.getPassword()));
        return particulierRepository.saveAndFlush(particulier);
    }

    @Override
    public Optional<?> findByEmail(String email) {
        return clientRepository.findByEmail(email);
    }

}
