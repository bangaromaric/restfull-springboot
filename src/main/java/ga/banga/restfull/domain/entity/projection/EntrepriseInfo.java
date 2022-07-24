package ga.banga.restfull.domain.entity.projection;

import ga.banga.restfull.domain.entity.Particulier;
import org.springframework.data.rest.core.config.Projection;

import java.util.Set;

/**
 * @author Romaric BANGA
 * @version 1.0
 * @since 7/24/22
 */
@Projection(name = "entrepriseInfo", types = { Particulier.class })

public interface EntrepriseInfo {
    String getNom();

    String getAdresse();

    String getEmail();


    String getNif();

    Set<CommandeByClient> getCommandes();
}
