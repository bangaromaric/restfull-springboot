package ga.banga.restfull.repository;

import ga.banga.restfull.domain.entity.Entreprise;
import ga.banga.restfull.domain.entity.projection.EntrepriseInfo;
import ga.banga.restfull.domain.entity.projection.PanierInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource(excerptProjection = EntrepriseInfo.class)
public interface EntrepriseRepository extends JpaRepository<Entreprise, Long> {
}