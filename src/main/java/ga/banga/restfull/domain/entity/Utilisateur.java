package ga.banga.restfull.domain.entity;

import lombok.Getter;
import lombok.Setter;
import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import java.io.Serial;
import java.io.Serializable;
import java.util.LinkedHashSet;
import java.util.Set;


@Getter
@Setter
@Entity
@Table(name = "utilisateur")
@Inheritance(strategy = InheritanceType.JOINED)
@DiscriminatorValue("Client")
@DiscriminatorColumn(name = "type_user")
public class Utilisateur implements Serializable {
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
    @Column(name = "email", nullable = false, unique = true)
    @Email @NotBlank
    private String email;

    @OneToMany(mappedBy = "client", orphanRemoval = true, cascade = CascadeType.REMOVE)
    @OrderBy(value = "montant ASC")
    private Set<Commande> commandes = new LinkedHashSet<>();

    @Column(name = "password")
    private String password;

    @Column(name = "enabled")
    private Boolean enabled = true;

    @Column(name = "role")
    private String role;
}