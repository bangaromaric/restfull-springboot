package ga.banga.restfull.repository;

import ga.banga.restfull.domain.entity.Panier;
import ga.banga.restfull.domain.entity.projection.PanierInfo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.security.access.prepost.PreAuthorize;

@RepositoryRestResource(excerptProjection = PanierInfo.class)
public interface PanierRepository extends JpaRepository<Panier, Long>, JpaSpecificationExecutor<Panier> {
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @Override
    Page<Panier> findAll(Pageable pageable);

    Page<PanierInfo> findByCommande_Client_Email(String email, Pageable pageable);



}