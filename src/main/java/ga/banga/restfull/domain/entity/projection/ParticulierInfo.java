package ga.banga.restfull.domain.entity.projection;

import ga.banga.restfull.domain.entity.Entreprise;
import ga.banga.restfull.domain.entity.Particulier;
import org.springframework.data.rest.core.config.Projection;

import java.util.Set;

/**
 * @author Romaric BANGA
 * @version 1.0
 * @since 7/24/22
 */
@Projection(name = "particulierInfo", types = { Particulier.class })
public interface ParticulierInfo {
    String getNom();

    String getAdresse();

    String getEmail();

    String getUsername();
    Set<CommandeByClient> getCommandes();
}
