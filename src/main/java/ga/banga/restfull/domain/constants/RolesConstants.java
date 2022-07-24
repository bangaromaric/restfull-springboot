package ga.banga.restfull.domain.constants;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

/**
 * @author Romaric BANGA
 * @version 1.0
 * @since 7/6/22
 */
public class RolesConstants {
    public static final String ROLE_ADMIN = "ROLE_ADMIN";
    public static final String ROLE_USER = "ROLE_USER";
    public static final String ROLE_SHOP = "ROLE_SHOP";

    public static final Set<String> ROLES = new HashSet<>(Arrays.asList(ROLE_ADMIN, ROLE_USER, ROLE_SHOP));

}
