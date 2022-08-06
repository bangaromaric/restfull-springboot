package ga.banga.restfull.service.impl;

import ga.banga.restfull.domain.entity.Panier;
import ga.banga.restfull.repository.PanierRepository;
import ga.banga.restfull.service.PanierService;
import org.springframework.stereotype.Service;

/**
 * @author Romaric BANGA
 * @version 1.0
 * @since 7/22/22
 */
//@AllArgsConstructor
@Service
public class PanierServiceImplParticulier implements PanierService {

    private final PanierRepository repository;


    public PanierServiceImplParticulier(PanierRepository repository) {
        this.repository = repository;
    }


    @Override
    public Panier saveAndFlush(Panier panier) {
        //set prix avec une reduction de 5%
        panier.getCommande().setMontant(panier.getProduit().getPrixUnitaire() - (panier.getProduit().getPrixUnitaire() * 5) / 100);
        return repository.saveAndFlush(panier);
    }
}


