package tnmk.ln.app.security.user;

import org.springframework.stereotype.Component;
import tnmk.common.exception.AuthorizationServiceException;
import tnmk.ln.app.common.entity.Possession;
import tnmk.ln.infrastructure.security.neo4j.entity.User;

/**
 * @author khoi.tran on 3/31/17.
 */
@Component
public class PossessionAuthorization {
    public void validateCanRemovePossession(User user, Possession possession) {
        if (!canRemovePossession(user, possession)) {
            throw new AuthorizationServiceException(String.format("No permission. %s belongs to another one, you cannot delete it. OwnerId: %s, UserId: %s", possession.getClass().getSimpleName(), possession.getOwner().getId(), user.getId()));
        }
    }

    public boolean canRemovePossession(User user, Possession possession) {
        return UserAuthorization.isAdmin(user) || possession.getOwner().getId().equals(user.getId());
    }
}
