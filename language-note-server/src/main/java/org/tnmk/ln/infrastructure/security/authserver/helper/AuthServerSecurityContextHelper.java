package org.tnmk.ln.infrastructure.security.authserver.helper;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.collections4.MapUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.authentication.OAuth2AuthenticationDetails;
import org.springframework.security.oauth2.provider.token.AuthorizationServerTokenServices;
import org.springframework.security.oauth2.provider.token.DefaultTokenServices;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.stereotype.Component;
import org.tnmk.common.exception.UnexpectedException;
import org.tnmk.common.util.ObjectMapperUtil;
import org.tnmk.common.util.oauth2.jwt.JwtTokenJson;
import org.tnmk.common.util.oauth2.jwt.JwtTokenUtils;
import org.tnmk.ln.infrastructure.security.authserver.config.tokenconverter.AccessTokenUserDetails;
import org.tnmk.ln.infrastructure.security.authserver.config.tokenconverter.EnhancedJwtTokenConverter;
import org.tnmk.ln.infrastructure.security.authserver.config.userdetails.AuthenticatedUser;

import javax.inject.Inject;
import java.util.Map;

@Component
public class AuthServerSecurityContextHelper {
    public static final Logger LOGGER = LoggerFactory.getLogger(AuthServerSecurityContextHelper.class);

    @Inject
    private ObjectMapper objectMapper;

    /**
     * @return If UserAccess is not null, UserAccess.user and UserAccess.consumer are always not null.
     */
    public AccessTokenUserDetails getUser() {
        AccessTokenUserDetails result = null;
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null) {
            OAuth2Authentication oauth2Authentication = (OAuth2Authentication) auth;
            OAuth2AuthenticationDetails details = (OAuth2AuthenticationDetails) oauth2Authentication.getDetails();
            String tokenValue = details.getTokenValue();
            JwtTokenJson jwtTokenJson = JwtTokenUtils.decoded(tokenValue);
            String jwtTokenJsonBody = jwtTokenJson.getJwtBody();
//            Map<String, Object> jwtTokenBodyMap = objectMapper.convertValue(jwtTokenJsonBody, Map.class);
//            Map<String, Object> additionalInfo = (Map)jwtTokenBodyMap.get(EnhancedJwtTokenConverter.ADDINFO_KEY_ACCESS_TOKEN_USER_DETAILS);
            AccessTokenUserDetailsWrapper accessTokenUserDetailsWrapper = ObjectMapperUtil.toObject(objectMapper, jwtTokenJsonBody, AccessTokenUserDetailsWrapper.class);
            result = accessTokenUserDetailsWrapper.accessTokenUserDetails;
//
//            OAuth2AccessToken oAuth2AccessToken = tokenServices.readAccessToken(tokenValue);
//            result = (AccessTokenUserDetails)oAuth2AccessToken.getAdditionalInformation().get(EnhancedJwtTokenConverter.ADDINFO_KEY_ACCESS_TOKEN_USER_DETAILS);
        }
        return result;
    }

    /**
     * This method will get authenticated user from RequestContextHolder.<br/>
     * If there is no authenticated user, throw exception.
     *
     * @return Never return null.
     */
    public AccessTokenUserDetails validateExistAuthenticatedUser() {
        AccessTokenUserDetails authenticatedUser = getUser();
        if (null == authenticatedUser) {
            LOGGER.error("Not found AuthenticatedUser information in request. "
                    + "The request was not authorized."
                    + "Please check to register requestURL to AuthenticationInterceptor class.");
            throw new RuntimeException("Unauthenticated!");
        }
        return authenticatedUser;
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    private static class AccessTokenUserDetailsWrapper{
        @JsonProperty(EnhancedJwtTokenConverter.ADDINFO_KEY_ACCESS_TOKEN_USER_DETAILS)
        private AccessTokenUserDetails accessTokenUserDetails;

        public AccessTokenUserDetails getAccessTokenUserDetails() {
            return accessTokenUserDetails;
        }

        public void setAccessTokenUserDetails(AccessTokenUserDetails accessTokenUserDetails) {
            this.accessTokenUserDetails = accessTokenUserDetails;
        }
    }
}
