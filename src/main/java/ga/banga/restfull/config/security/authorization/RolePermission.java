package ga.banga.restfull.config.security.authorization;


import ga.banga.restfull.domain.enumeration.RightStandard;
import ga.banga.restfull.domain.enumeration.Table;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static java.lang.String.format;

/**
 * @author Romaric BANGA
 * @version 1.0
 * @since 7/9/22
 */
@Getter
@RequiredArgsConstructor
public class RolePermission {

    private final Table table;
    protected final RightStandard[] rightStandards;


    public List<String> getAuthoroties(){
       /* List<String> authorities = new ArrayList<>();
        authorities.addAll(Arrays.stream(rightStandards).map(rightStandard -> {
            return format("%s_%s", rightStandard.getLibelle(), table.getLibelle());
        }).toList());
*/


        return new ArrayList<>(Arrays.stream(rightStandards).map(rightStandard -> format("%s_%s", rightStandard.getLibelle(), table.getLibelle())).toList());
    }


}
