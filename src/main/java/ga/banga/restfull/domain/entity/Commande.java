package ga.banga.restfull.domain.entity;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.io.Serial;
import java.io.Serializable;
import java.util.Date;

@Getter
@Setter
@Entity
@Table(name = "commande")
public class Commande implements Serializable {

    @Serial
    private static final long serialVersionUID = 254773881452103891L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    private Long id;

    @CreationTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "date")
    private Date date;

    @Column(name = "montant")
    private Double montant;

    @ManyToOne(optional = false)
    @JoinColumn(name = "client_id", nullable = false)
    private Utilisateur client;


}