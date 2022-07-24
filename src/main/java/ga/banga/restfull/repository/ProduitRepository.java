package ga.banga.restfull.repository;

import ga.banga.restfull.domain.entity.Produit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.access.prepost.PreAuthorize;

@PreAuthorize("hasRole('ROLE_ADMIN')")
public interface ProduitRepository extends JpaRepository<Produit, Long> {
}