package tnmk.ln.app.practice;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import tnmk.ln.app.common.entity.UriPrefixConstants;
import tnmk.ln.app.practice.entity.favourite.PracticeFavourite;
import tnmk.ln.app.practice.model.QuestionWithPracticeResult;
import tnmk.ln.infrastructure.security.helper.SecurityContextHelper;
import tnmk.ln.infrastructure.security.neo4j.entity.User;

import java.util.List;

/**
 * @author khoi.tran on 3/4/17.
 */
@RestController
public class PracticeFavouriteResource {

    @Autowired
    private PracticeFavouriteService practiceFavouriteService;

    @RequestMapping(value = UriPrefixConstants.API_PREFIX + "/expression/favourite", method = RequestMethod.POST)
    public PracticeFavourite favouriteExpression(@RequestBody FavouriteRequest favouriteRequest) {
        User user = SecurityContextHelper.validateExistAuthenticatedUser();
        return practiceFavouriteService.saveExpressionFavourite(user, favouriteRequest.getExpressionId(), favouriteRequest.getFavourite());
    }

    public static class FavouriteRequest {
        private String expressionId;
        private int favourite;

        public String getExpressionId() {
            return expressionId;
        }

        public void setExpressionId(String expressionId) {
            this.expressionId = expressionId;
        }

        public int getFavourite() {
            return favourite;
        }

        public void setFavourite(int favourite) {
            this.favourite = favourite;
        }
    }
}
