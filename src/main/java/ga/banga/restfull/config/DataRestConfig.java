package ga.banga.restfull.config;

import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.rest.core.event.ValidatingRepositoryEventListener;
import org.springframework.data.rest.webmvc.config.RepositoryRestConfigurer;
import org.springframework.validation.Validator;

/**
 * Permet d'activer la validation
 * @author Romaric BANGA
 * @version 1.0
 * @since 6/27/22
 */
@Configuration
@AllArgsConstructor
public class DataRestConfig implements RepositoryRestConfigurer {

    private final Validator validator;
    @Override
    public void configureValidatingRepositoryEventListener(ValidatingRepositoryEventListener validatingListener) {
        validatingListener.addValidator("beforeCreate",this.validator);
        validatingListener.addValidator("beforeSave",this.validator);
    }
}
