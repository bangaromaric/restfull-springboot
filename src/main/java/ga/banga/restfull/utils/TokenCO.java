package ga.banga.restfull.utils;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @author Romaric BANGA
 * @version 1.0
 * @since 7/6/22
 */

@Data @NoArgsConstructor @AllArgsConstructor @Builder
public class TokenCO implements Serializable {
    private static final long serialVersionUID = -5198455531003510795L;

    private String access_token;
    private int expires_in;
    private String refresh_token;
    private String scope;
    private String token_type;
}
