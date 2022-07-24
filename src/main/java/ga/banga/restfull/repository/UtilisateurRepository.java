package ga.banga.restfull.repository;

import ga.banga.restfull.domain.entity.Utilisateur;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RestResource;

import java.util.Optional;

public interface UtilisateurRepository extends JpaRepository<Utilisateur, Long> {

    @Override
    @RestResource(exported = false)
    void deleteById(Long id);

    @Override
    @RestResource(exported = false)
    void delete(Utilisateur client);

    @Override
    @RestResource(exported = false)
    <S extends Utilisateur> S save(S client);

    Optional<Utilisateur> findByEmail(String email);

}