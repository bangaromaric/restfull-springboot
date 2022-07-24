package ga.banga.restfull.repository;

import ga.banga.restfull.domain.entity.Particulier;
import ga.banga.restfull.domain.entity.projection.PanierInfo;
import ga.banga.restfull.domain.entity.projection.ParticulierInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource(excerptProjection = ParticulierInfo.class)
public interface ParticulierRepository extends JpaRepository<Particulier, Long> {
}