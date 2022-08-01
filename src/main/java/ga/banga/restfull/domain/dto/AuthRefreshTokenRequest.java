package ga.banga.restfull.domain.dto;


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
public class AuthRefreshTokenRequest implements Serializable {

    private static final long serialVersionUID = 5936941129927853575L;

    private String refresh_token;

}