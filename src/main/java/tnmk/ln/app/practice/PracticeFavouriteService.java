package tnmk.ln.app.practice;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tnmk.ln.app.practice.entity.favourite.PracticeFavourite;
import tnmk.ln.infrastructure.security.neo4j.entity.User;

/**
 * @author khoi.tran on 4/30/17.
 * @design-note Favourite object
 * <pre>
 * Favourite doesn't belong to an Expression because it depends on the user.
 * Favourite doesn't belong to Questions either because there are so many questions related to an expression.
 * </pre>
 * Problem: need to support adding Favourite when creating a new Expression (still not saved yet)
 */
@Service
public class PracticeFavouriteService {
    @Autowired
    private PracticeFavouriteRepository practiceFavouriteRepository;
    @Autowired
    private PracticeFavouriteCustomRepository practiceFavouriteCustomRepository;

    public Integer findExpressionFavourite(User user, String expressionId) {
        PracticeFavourite practiceFavourite = practiceFavouriteCustomRepository.findByOwnerIdAndExpressionId(user.getId(), expressionId);
        if (practiceFavourite == null) {
            return null;
        } else {
            return practiceFavourite.getFavourite();
        }
    }

    public PracticeFavourite saveExpressionFavourite(User user, String expressionId, int favourite) {
        PracticeFavourite practiceFavourite = practiceFavouriteCustomRepository.findByOwnerIdAndExpressionId(user.getId(), expressionId);
        if (practiceFavourite == null) {
            practiceFavourite = new PracticeFavourite();
            practiceFavourite.setOwner(user);
            practiceFavourite.setExpressionId(expressionId);
        }
        practiceFavourite.setFavourite(favourite);
        //TODO set to PracticeResult

        return practiceFavouriteRepository.save(practiceFavourite);
    }
}
