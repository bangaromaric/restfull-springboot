package ga.banga.restfull.event;

import ga.banga.restfull.domain.entity.Utilisateur;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.rest.core.annotation.*;
import org.springframework.stereotype.Component;

/**
 * @author Romaric BANGA
 * @version 1.0
 * @since 6/27/22
 */
@Slf4j
@RepositoryEventHandler
@Component
public class ClientEventHandler {

    @HandleBeforeCreate
    public void handleClientBeforeCreate(Utilisateur client){
        log.info("*** intercept before create");
        log.info(client.toString());
    }

    @HandleAfterCreate
    public void handleClientAfterCreate(Utilisateur client){
        log.info("*** intercept after create");
        log.info(client.toString());
    }

    @HandleBeforeSave
    public void handleClientBeforeSave(Utilisateur client){
        log.info("*** intercept before save");
        log.info(client.toString());
    }

    @HandleAfterSave
    public void handleclientAfterSave(Utilisateur client){
        log.info("*** intercept after save");
        log.info(client.toString());
    }

    @HandleBeforeDelete
    public void handleclientBeforeDel(Utilisateur client){
        log.info("*** intercept before del");
        log.info(client.toString());
    }

    @HandleAfterDelete
    public void handleClientAfterDel(Utilisateur client){
        log.info("*** intercept after del");
        log.info(client.toString());
    }

}
