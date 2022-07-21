package ga.banga.restfull.repository;

import ga.banga.restfull.domain.entity.Client;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RestResource;

public interface ClientRepository extends JpaRepository<Client, Long> {

    @Override
    @RestResource(exported = false)
    void deleteById(Long id);

    @Override
    @RestResource(exported = false)
    void delete(Client client);

    @Override
    @RestResource(exported = false)
    Client save(Client client);
}