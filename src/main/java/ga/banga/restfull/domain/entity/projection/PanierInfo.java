package ga.banga.restfull.domain.entity.projection;

import ga.banga.restfull.domain.entity.Utilisateur;
import ga.banga.restfull.domain.entity.Commande;
import ga.banga.restfull.domain.entity.Panier;
import ga.banga.restfull.domain.entity.Produit;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.rest.core.config.Projection;

import java.util.Date;

/**
 * @author Romaric BANGA
 * @version 1.0
 * @since 7/22/22
 */
@Projection(name = "panierInfo", types = { Panier.class })
public interface PanierInfo {
    Integer getQuantite();

    CommandeInfo getCommande();

    ProduitInfo getProduit();

    @Value("#{target.commande.montant * target.quantite}")
    Double getPrixTotal();
    @Projection(name = "commandeInfo", types = { Commande.class })
    interface CommandeInfo {
        Date getDate();

        Double getMontant();

        UserInfo getClient();
        @Projection(name = "userInfo", types = { Utilisateur.class })
        interface UserInfo {
            String getNom();

            String getAdresse();

            String getEmail();

            String getUsername();
        }
    }
    @Projection(name = "produitInfo", types = { Produit.class })
    interface ProduitInfo {
        String getDesignation();

        Double getPrixUnitaire();
    }
}
