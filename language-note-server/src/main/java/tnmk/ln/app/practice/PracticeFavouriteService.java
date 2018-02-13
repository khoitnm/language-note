package tnmk.ln.app.practice;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tnmk.ln.app.aggregation.practice.model.ExpressionComposite;
import tnmk.ln.app.practice.entity.favourite.PracticeFavourite;
import tnmk.ln.app.practice.entity.result.ExpressionPracticeResult;
import tnmk.ln.infrastructure.security.neo4j.entity.User;

import java.util.Arrays;
import java.util.List;

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
    @Autowired
    private ExpressionPracticeResultQueryRepository expressionPracticeResultQueryRepository;
    @Autowired
    private ExpressionPracticeResultRepository expressionPracticeResultRepository;

    public int findExpressionFavourite(Long userId, String expressionId) {
        PracticeFavourite practiceFavourite = practiceFavouriteCustomRepository.findByOwnerIdAndExpressionId(userId, expressionId);
        if (practiceFavourite == null) {
            return 0;
        } else {
            return practiceFavourite.getFavourite();
        }
    }

    /**
     * If favourite is null and PracticeFavourite instance is null, return null.
     *
     * @param user
     * @param expressionId
     * @param favouritePointObject
     * @return
     */
    @Transactional
    public PracticeFavourite saveExpressionFavourite(User user, String expressionId, Integer favouritePointObject) {
        PracticeFavourite practiceFavourite = practiceFavouriteCustomRepository.findByOwnerIdAndExpressionId(user.getId(), expressionId);
        int favouritePoint;
        if (favouritePointObject == null) {
            if (practiceFavourite == null) {
                return null;
            } else {
                favouritePoint = 0;
            }
        } else {
            favouritePoint = favouritePointObject;
        }

        if (practiceFavourite == null) {
            practiceFavourite = new PracticeFavourite();
            practiceFavourite.setOwner(user);
            practiceFavourite.setExpressionId(expressionId);
        }
        practiceFavourite.setFavourite(favouritePoint);
        //TODO set to PracticeResult
        //when change favourite -> change practiceResult
        //when create new practiceResult, must check favourite
        ExpressionPracticeResult expressionPracticeResult = expressionPracticeResultQueryRepository.findByOwnerIdAndExpressionId(user.getId(), expressionId);
        if (expressionPracticeResult == null) {
            expressionPracticeResult = new ExpressionPracticeResult();
            expressionPracticeResult.setAnswers(Arrays.asList());
            expressionPracticeResult.setOwner(user);
            expressionPracticeResult.setExpressionId(expressionId);
        }
        expressionPracticeResult.setAdditionalPoints(-favouritePoint);
        PracticeAnswerHelper.calculateAnswerPoints(expressionPracticeResult);
        expressionPracticeResultRepository.save(expressionPracticeResult);
        return practiceFavouriteRepository.save(practiceFavourite);
    }

    @Async
    @Transactional
    public void saveExpressionFavourites(User user, List<ExpressionComposite> expressions) {
        for (ExpressionComposite expression : expressions) {
            saveExpressionFavourite(user, expression.getId(), expression.getFavourite());
        }
    }
}
