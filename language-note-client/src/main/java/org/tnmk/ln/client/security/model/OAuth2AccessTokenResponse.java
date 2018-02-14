package org.tnmk.ln.client.security.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class OAuth2AccessTokenResponse {
    /**
     * The access token issued by the authorization server. This value is REQUIRED.
     */
    @JsonProperty("access_token")
    private String accessToken;

    /**
     * The type of the token issued as described in <a
     * href="http://tools.ietf.org/html/draft-ietf-oauth-v2-22#section-7.1">Section 7.1</a>. Value is case insensitive.
     * This value is REQUIRED.
     */
    public static String TOKEN_TYPE_BEARER = "Bearer";
    public static String TOKEN_TYPE_OAUTH2 = "OAuth2";

    @JsonProperty("token_type")
    private String tokenType;


    /**
     * The lifetime in seconds of the access token. For example, the value "3600" denotes that the access token will
     * expire in one hour from the time the response was generated. This value is OPTIONAL.
     */
    @JsonProperty("expires_in")
    private long expiresIn;


    /**
     * The refresh token which can be used to obtain new access tokens using the same authorization grant as described
     * in <a href="http://tools.ietf.org/html/draft-ietf-oauth-v2-22#section-6">Section 6</a>. This value is OPTIONAL.
     */
    @JsonProperty("refresh_token")
    private String refreshToken;

    /**
     * The scope of the access token as described by <a
     * href="http://tools.ietf.org/html/draft-ietf-oauth-v2-22#section-3.3">Section 3.3</a>
     */
    @JsonProperty("scope")
    private String scope;

    private String jti;

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public String getTokenType() {
        return tokenType;
    }

    public void setTokenType(String tokenType) {
        this.tokenType = tokenType;
    }

    public long getExpiresIn() {
        return expiresIn;
    }

    public void setExpiresIn(long expiresIn) {
        this.expiresIn = expiresIn;
    }

    public String getRefreshToken() {
        return refreshToken;
    }

    public void setRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
    }

    public String getScope() {
        return scope;
    }

    public void setScope(String scope) {
        this.scope = scope;
    }

    public String getJti() {
        return jti;
    }

    public void setJti(String jti) {
        this.jti = jti;
    }
}
