package org.tnmk.ln.infrastructure.security.authserver.config.tokenconverter;

import org.springframework.security.oauth2.common.DefaultOAuth2AccessToken;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.tnmk.ln.infrastructure.security.authserver.config.userdetails.AuthenticatedUser;
import org.tnmk.ln.infrastructure.security.usersmanagement.neo4j.entity.User;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * This class is used only for adding some more information into the accessToken.
 */
public class EnhancedJwtTokenConverter extends JwtAccessTokenConverter {

    @Override
    public OAuth2AccessToken enhance(OAuth2AccessToken accessToken, OAuth2Authentication authentication) {
        addUserDetailsToAdditionalInformationOfAccessTokenIfPossible(accessToken, authentication);

        //Must change additionalInformation before calling the super method. Otherwise, the updated information will not be encoded into accessToken value.
        return super.enhance(accessToken, authentication);
    }
    private void addUserDetailsToAdditionalInformationOfAccessTokenIfPossible(OAuth2AccessToken accessToken, OAuth2Authentication authentication){
        if (accessToken instanceof DefaultOAuth2AccessToken) {
            DefaultOAuth2AccessToken defaultOAuth2AccessToken = (DefaultOAuth2AccessToken) accessToken;

            //We cannot get directly defaultOAuth2AccessToken.getAdditionalInformation() because the result could be immutable, so we need to create a new Map.
            //We should use LinkedHashMap because the source code of Spring also use the same {@link JwtAccessTokenConverter#enhance(accessToken, authentication)}
            Map<String, Object> additionalInformation = new LinkedHashMap<>(defaultOAuth2AccessToken.getAdditionalInformation());
            AuthenticatedUser authenticatedUser = (AuthenticatedUser) authentication.getUserAuthentication().getPrincipal();
            AccessTokenUserDetails accessTokenUserDetails = toAccessTokenUserDetails(authenticatedUser.getUser());
            additionalInformation.put("accessTokenUserDetails", accessTokenUserDetails);
            defaultOAuth2AccessToken.setAdditionalInformation(additionalInformation);
        }
    }
    //Could move it to a converter.
    public static AccessTokenUserDetails toAccessTokenUserDetails(User user){
        AccessTokenUserDetails accessTokenUserDetails = new AccessTokenUserDetails();
        accessTokenUserDetails.setUserId(user.getId());
        accessTokenUserDetails.setUsername(user.getUsername());
        accessTokenUserDetails.setCreatedDateTime(user.getCreatedDateTime());
        accessTokenUserDetails.setUpdatedDateTime(user.getUpdatedDateTime());
        return accessTokenUserDetails;
    }
}