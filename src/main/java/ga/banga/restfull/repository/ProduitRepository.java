package ga.banga.restfull.repository;

import ga.banga.restfull.domain.entity.Produit;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProduitRepository extends JpaRepository<Produit, Long> {
}