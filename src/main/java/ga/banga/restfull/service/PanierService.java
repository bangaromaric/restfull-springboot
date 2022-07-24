package ga.banga.restfull.service;

import ga.banga.restfull.domain.entity.Panier;
import ga.banga.restfull.domain.entity.projection.PanierInfo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * @author Romaric BANGA
 * @version 1.0
 * @since 7/22/22
 */
public interface PanierService {
    Panier saveAndFlush(Panier panier);
    Page<PanierInfo> findByCommandeClientEmail(String email, Pageable pageable);



}
