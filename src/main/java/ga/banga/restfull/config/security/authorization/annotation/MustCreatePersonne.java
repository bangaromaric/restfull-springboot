package ga.banga.restfull.config.security.authorization.annotation;

import org.springframework.security.access.prepost.PreAuthorize;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author Romaric BANGA
 * @version 1.0
 * @since 7/9/22
 */
@Target({ElementType.TYPE, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@PreAuthorize(value = "hasRole({T(com.example.restdatatest.config.security.authorization.enu.RightStandard).CREATE}+'_'+{T(com.example.restdatatest.config.security.authorization.enu.Table).PERSONNE})")
public @interface MustCreatePersonne {
}
