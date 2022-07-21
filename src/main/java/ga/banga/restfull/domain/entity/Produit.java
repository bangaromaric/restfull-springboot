package ga.banga.restfull.domain.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serial;
import java.io.Serializable;


@Getter
@Setter
@Entity
@Table(name = "produit")
public class Produit implements Serializable {


    @Serial
    private static final long serialVersionUID = 2624655264707165215L;


    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "designation")
    private String designation;

    @Column(name = "prix_unitaire")
    private String prixUnitaire;



}