package ga.banga.restfull.repository;

import ga.banga.restfull.domain.entity.Panier;
import ga.banga.restfull.domain.entity.projection.PanierInfo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource(excerptProjection = PanierInfo.class)
public interface PanierRepository extends JpaRepository<Panier, Long>, JpaSpecificationExecutor<Panier> {

    Page<PanierInfo> findByCommande_Client_Email(String email, Pageable pageable);



}