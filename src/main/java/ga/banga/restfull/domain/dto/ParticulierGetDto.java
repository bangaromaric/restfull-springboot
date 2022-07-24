package ga.banga.restfull.domain.dto;

import lombok.Data;

import javax.validation.constraints.Email;
import java.io.Serializable;

/**
 * @author Romaric BANGA
 * @version 1.0
 * @since 7/24/22
 */
@Data
public class ParticulierGetDto implements Serializable {
    private final String nom;
    private final String adresse;
    @Email
    private final String email;
    private final String role;
}
