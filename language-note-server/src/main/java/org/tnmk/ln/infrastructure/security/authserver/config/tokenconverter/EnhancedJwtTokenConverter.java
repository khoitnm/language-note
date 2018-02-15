package org.tnmk.ln.infrastructure.security.authserver.config.tokenconverter;

import org.springframework.security.oauth2.common.DefaultOAuth2AccessToken;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.tnmk.ln.infrastructure.security.authserver.config.userdetails.AuthenticatedUser;
import org.tnmk.ln.infrastructure.security.authserver.usermanagement.AuthServerUser;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * This class is used only for adding some more information into the accessToken.
 */
public class EnhancedJwtTokenConverter extends JwtAccessTokenConverter {
    public static final String ADDINFO_KEY_ACCESS_TOKEN_USER_DETAILS = "accessTokenUserDetails";
    @Override
    public OAuth2AccessToken enhance(OAuth2AccessToken accessToken, OAuth2Authentication authentication) {
        addUserDetailsToAdditionalInformationOfAccessTokenIfPossible(accessToken, authentication);

        //Must change additionalInformation before calling the super method. Otherwise, the updated information will not be encoded into accessToken value.
        return super.enhance(accessToken, authentication);
    }
    private void addUserDetailsToAdditionalInformationOfAccessTokenIfPossible(OAuth2AccessToken accessToken, OAuth2Authentication authentication){
        if (accessToken instanceof DefaultOAuth2AccessToken) {
            DefaultOAuth2AccessToken defaultOAuth2AccessToken = (DefaultOAuth2AccessToken) accessToken;
            Map<String, Object> additionalInformation = new LinkedHashMap<>(defaultOAuth2AccessToken.getAdditionalInformation());
            AuthenticatedUser authenticatedUser = (AuthenticatedUser) authentication.getUserAuthentication().getPrincipal();
            AccessTokenUserDetails accessTokenUserDetails = toAccessTokenUserDetails(authenticatedUser.getUser());
            additionalInformation.put(ADDINFO_KEY_ACCESS_TOKEN_USER_DETAILS, accessTokenUserDetails);
            defaultOAuth2AccessToken.setAdditionalInformation(additionalInformation);
        }
    }
    //Could move it to a converter.
    public static AccessTokenUserDetails toAccessTokenUserDetails(AuthServerUser user){
        AccessTokenUserDetails accessTokenUserDetails = new AccessTokenUserDetails();
        accessTokenUserDetails.setUserId(user.getId());
        accessTokenUserDetails.setUsername(user.getUsername());
        accessTokenUserDetails.setCreatedDateTime(user.getCreatedDateTime());
        accessTokenUserDetails.setUpdatedDateTime(user.getUpdatedDateTime());
        return accessTokenUserDetails;
    }
}