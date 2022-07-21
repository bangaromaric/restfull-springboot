package ga.banga.restfull.domain.entity;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.io.Serial;
import java.io.Serializable;

@Entity
@Table(name = "particulier")
@DiscriminatorValue("Particulier")
public class Particulier extends Client implements Serializable {
    @Serial
    private static final long serialVersionUID = -2811171275033672021L;
}