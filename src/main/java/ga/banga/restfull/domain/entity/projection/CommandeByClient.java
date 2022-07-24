package ga.banga.restfull.domain.entity.projection;

import ga.banga.restfull.domain.entity.Commande;
import ga.banga.restfull.domain.entity.Entreprise;
import org.springframework.data.rest.core.config.Projection;

import java.util.Date;

/**
 * @author Romaric BANGA
 * @version 1.0
 * @since 7/24/22
 */
@Projection(name = "commandeByClient", types = { Commande.class })
public interface CommandeByClient {
    Long getId();

    Date getDate();

    Double getMontant();
}
