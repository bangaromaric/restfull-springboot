package ga.banga.restfull.repository;

import ga.banga.restfull.domain.entity.Pagnier;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PagnierRepository extends JpaRepository<Pagnier, Long> {
}