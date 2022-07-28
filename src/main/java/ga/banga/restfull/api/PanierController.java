package ga.banga.restfull.api;

import ga.banga.restfull.config.security.authorization.annotation.RoleCreatePanier;
import ga.banga.restfull.domain.entity.Utilisateur;
import ga.banga.restfull.domain.entity.Panier;
import ga.banga.restfull.domain.entity.Particulier;
import ga.banga.restfull.domain.entity.Produit;
import ga.banga.restfull.domain.entity.projection.PanierInfo;
import ga.banga.restfull.repository.UtilisateurRepository;
import ga.banga.restfull.repository.ProduitRepository;
import ga.banga.restfull.service.PanierService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.rest.webmvc.RepositoryRestController;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

/**
 * Controller du panier
 * @author Romaric BANGA
 * @version 1.0
 * @since 7/21/22
 */
@RepositoryRestController
@Slf4j
public class PanierController {
    private final PanierService serviceParticulier;
    private final PanierService serviceEntreprise;
    private final UtilisateurRepository utilisateurRepository;
    private final ProduitRepository produitRepository;




    public PanierController(@Qualifier("panierServiceImplParticulier") PanierService serviceParticulier,
                            @Qualifier("panierServiceImplEntreprise") PanierService serviceEntreprise, UtilisateurRepository utilisateurRepository, ProduitRepository produitRepository) {
        this.serviceParticulier = serviceParticulier;
        this.serviceEntreprise = serviceEntreprise;
        this.utilisateurRepository = utilisateurRepository;
        this.produitRepository = produitRepository;
    }

    /**
     * Creer un panier
     * @param panier contient toutes les informations du panier
     * @return contient le panier apres sa creation
     */
    @PostMapping( "/paniers")
//    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @RoleCreatePanier
    public @ResponseBody ResponseEntity<?> postPanier(@RequestBody Panier panier) {

        Optional<Utilisateur> clOp = utilisateurRepository.findById(panier.getCommande().getClient().getId());
        Optional<Produit> prodOp = produitRepository.findById(panier.getProduit().getId());
        Utilisateur cl = clOp.get();
        Produit prod = prodOp.get();

        panier.setProduit(prod);
        Panier panierSave;

        // save panier
        if (cl instanceof Particulier)
            panierSave = serviceParticulier.saveAndFlush(panier);
        else
            panierSave = serviceEntreprise.saveAndFlush(panier);

        // The results of type T need to be wrapped up in a Spring HATEOAS
        EntityModel<Panier> resources = EntityModel.of(panierSave);
        //	Add a link back to this exact method as a self link.
        resources.add(linkTo(methodOn(PanierController.class).postPanier(panier)).withSelfRel());

        return new ResponseEntity<>(panierSave, HttpStatus.CREATED);
    }


    /**
     * Affiche les panier en fonction de l'email de l'utilisateur connecte
     * @param pageable page recu
     * @return liste paniers
     */
    @GetMapping( "/paniers/search/findByCommande_Client_Email")
    public @ResponseBody ResponseEntity<?> getPanier(Pageable pageable) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//      String currentPrincipalName = authentication.getName();
        Utilisateur user = (Utilisateur) authentication.getPrincipal();
        Page<PanierInfo> paniers = serviceParticulier.findByCommandeClientEmail(user.getEmail(), pageable);
        return new ResponseEntity<>(paniers, HttpStatus.OK);
    }


}
