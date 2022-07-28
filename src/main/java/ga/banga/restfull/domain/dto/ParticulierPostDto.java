package ga.banga.restfull.domain.dto;

import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.io.Serial;
import java.io.Serializable;

/**
 * @author Romaric BANGA
 * @version 1.0
 * @since 7/24/22
 */
@Data
public class ParticulierPostDto implements Serializable {
    @Serial
    private static final long serialVersionUID = 832793233556525770L;

    @NotBlank
    private final String nom;
    private final String adresse;
    @Email
    private final String email;
    @NotBlank
    @Size(min = 6)
    private final String password;
}