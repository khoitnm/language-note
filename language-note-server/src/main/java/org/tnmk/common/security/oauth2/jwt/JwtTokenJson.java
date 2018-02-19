package org.tnmk.common.security.oauth2.jwt;

public class JwtTokenJson {
    /**
     * The encoded json of jwt header token.
     */
    private String jwtHeader;
    /**
     * The encoded json of jwt body token
     */
    private String jwtBody;

    public String getJwtHeader() {
        return jwtHeader;
    }

    public void setJwtHeader(String jwtHeader) {
        this.jwtHeader = jwtHeader;
    }

    public String getJwtBody() {
        return jwtBody;
    }

    public void setJwtBody(String jwtBody) {
        this.jwtBody = jwtBody;
    }
}
