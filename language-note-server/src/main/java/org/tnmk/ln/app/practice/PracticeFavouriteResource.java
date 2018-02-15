package org.tnmk.ln.app.practice;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.tnmk.ln.app.common.constant.UriPrefixConstants;
import org.tnmk.ln.app.practice.entity.favourite.PracticeFavourite;
import org.tnmk.ln.infrastructure.security.authserver.config.tokenconverter.AccessTokenUserDetails;
import org.tnmk.ln.infrastructure.security.resourceserver.helper.ResourceServerSecurityContextHelper;
import org.tnmk.ln.infrastructure.security.resourceserver.helper.SecurityContextHelper;
import org.tnmk.ln.infrastructure.security.usersmanagement.neo4j.entity.User;

import javax.inject.Inject;

/**
 * @author khoi.tran on 3/4/17.
 */
@RestController
public class PracticeFavouriteResource {

    @Inject
    private ResourceServerSecurityContextHelper resourceServerSecurityContextHelper;

    @Autowired
    private PracticeFavouriteService practiceFavouriteService;

    @RequestMapping(value = UriPrefixConstants.API_PREFIX + "/expression/favourite", method = RequestMethod.POST)
    public PracticeFavourite favouriteExpression(@RequestBody FavouriteRequest favouriteRequest) {
        User user = resourceServerSecurityContextHelper.validateExistUser();
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
