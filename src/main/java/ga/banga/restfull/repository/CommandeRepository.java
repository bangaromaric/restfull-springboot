package ga.banga.restfull.repository;

import ga.banga.restfull.domain.entity.Commande;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommandeRepository extends JpaRepository<Commande, Long> {
}