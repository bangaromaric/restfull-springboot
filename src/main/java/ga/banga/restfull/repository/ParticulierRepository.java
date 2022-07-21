package ga.banga.restfull.repository;

import ga.banga.restfull.domain.entity.Particulier;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ParticulierRepository extends JpaRepository<Particulier, Long> {
}