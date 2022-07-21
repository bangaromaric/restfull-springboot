package ga.banga.restfull.domain.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serial;
import java.io.Serializable;
import java.util.LinkedHashSet;
import java.util.Set;

@Getter
@Setter
@Entity
@Table(name = "client")
@Inheritance(strategy = InheritanceType.JOINED)
@DiscriminatorValue("Client")
@DiscriminatorColumn(name = "type_client")
public abstract class Client implements Serializable {
    @Serial
    private static final long serialVersionUID = -6045052047859236263L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    private Long id;
    @Column(name = "nom")
    private String nom;
    @Column(name = "adresse")
    private String adresse;
    @Column(name = "mail")
    private String mail;

    @OneToMany(mappedBy = "client", orphanRemoval = true)
    private Set<Commande> commandes = new LinkedHashSet<>();

}