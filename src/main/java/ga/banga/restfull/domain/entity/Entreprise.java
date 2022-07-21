package ga.banga.restfull.domain.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.io.Serial;
import java.io.Serializable;

@Getter
@Setter
@Entity
@Table(name = "entreprise")
@DiscriminatorValue("Entreprise")
public class Entreprise extends Client implements Serializable {
    @Serial
    private static final long serialVersionUID = 5008992829024442087L;
    @Column(name = "nif")
    private String nif;

}