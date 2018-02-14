package org.tnmk.ln.client.security.resource.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.tnmk.ln.client.common.constant.UriPrefixConstants;
import org.tnmk.ln.client.security.AuthenticationServerProxy;
import org.tnmk.ln.client.security.model.OAuth2AccessTokenResponse;

/**
 * @author khoi.tran on 11/6/16.
 */
@RestController
public class RefreshTokenResource {

    @Autowired
    private AuthenticationServerProxy authenticationServerProxy;

    @RequestMapping(value = {UriPrefixConstants.API_PREFIX + "/refresh-token"}, method = RequestMethod.GET)
    public OAuth2AccessTokenResponse refreshToken(@RequestBody RefreshTokenRequest refreshTokenRequest) {
        return authenticationServerProxy.refreshToken(refreshTokenRequest.getRefreshToken());
    }

    public static class RefreshTokenRequest {
        private String refreshToken;

        public String getRefreshToken() {
            return refreshToken;
        }

        public void setRefreshToken(String refreshToken) {
            this.refreshToken = refreshToken;
        }
    }
}
