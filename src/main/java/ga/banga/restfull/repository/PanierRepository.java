package ga.banga.restfull.repository;

import ga.banga.restfull.domain.entity.Panier;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface PanierRepository extends JpaRepository<Panier, Long>, JpaSpecificationExecutor<Panier> {



}